package com.gruposolutia.gestion_proyectos.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gruposolutia.gestion_proyectos.dto.GoogleAuthRequest;
import com.gruposolutia.gestion_proyectos.dto.LoginRequest;
import com.gruposolutia.gestion_proyectos.dto.LoginResponse;
import com.gruposolutia.gestion_proyectos.dto.TotpSetupResponse;
import com.gruposolutia.gestion_proyectos.dto.TotpVerificationRequest;
import com.gruposolutia.gestion_proyectos.model.Usuario;
import com.gruposolutia.gestion_proyectos.repository.UsuarioRepository;
import com.gruposolutia.gestion_proyectos.security.JwtService;
import com.gruposolutia.gestion_proyectos.service.GoogleAuthService;
import com.gruposolutia.gestion_proyectos.service.TotpService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Map<String, String> EMAIL_ROLES = Map.of(
        "admin@tuempresa.com",   "ROLE_ADMIN",
        "tecnico@tuempresa.com", "ROLE_TECNICO"
    );

    @Autowired
    private GoogleAuthService googleAuthService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TotpService totpService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );

        String username = authentication.getName();
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElse(null);

        if (usuario != null && usuario.isTotpEnabled()) {
            String preToken = jwtService.generatePreAuthToken(username);
            return ResponseEntity.ok(
                    new LoginResponse(true, preToken));
        }

        String token = jwtService.generateToken(username);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/totp/verify")
    public ResponseEntity<?> verifyTotp(
            @RequestBody TotpVerificationRequest request) {

        String username = jwtService.extractUsername(request.getPreToken());

        if (username == null || jwtService.isPreAuthToken(request.getPreToken()) == false) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Pre-token inv\u00e1lido o expirado");
        }

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElse(null);

        if (usuario == null || usuario.getTotpSecret() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("TOTP no configurado");
        }

        boolean isValid = totpService.verifyCode(
                usuario.getTotpSecret(),
                request.getTotpCode());

        if (!isValid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("C\u00f3digo TOTP inv\u00e1lido");
        }

        String token = jwtService.generateToken(username);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/totp/setup")
    public ResponseEntity<?> setupTotp() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElse(null);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }

        if (usuario.isTotpEnabled()) {
            return ResponseEntity.badRequest()
                    .body("TOTP ya est\u00e1 habilitado");
        }

        TotpService.TotpSetup setup = totpService.generateSecret(username);

        usuario.setTotpSecret(setup.getSecret());
        usuarioRepository.save(usuario);

        return ResponseEntity.ok(
                new TotpSetupResponse(
                        setup.getSecret(),
                        setup.getQrCodeUri()));
    }

    @PostMapping("/totp/enable")
    public ResponseEntity<?> enableTotp(
            @RequestBody Map<String, Integer> body) {

        Integer code = body.get("totpCode");
        if (code == null) {
            return ResponseEntity.badRequest().body("totpCode requerido");
        }

        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElse(null);

        if (usuario == null || usuario.getTotpSecret() == null) {
            return ResponseEntity.badRequest()
                    .body("Primero genera el secreto TOTP");
        }

        boolean isValid = totpService.verifyCode(
                usuario.getTotpSecret(), code);

        if (!isValid) {
            return ResponseEntity.badRequest()
                    .body("C\u00f3digo TOTP inv\u00e1lido");
        }

        usuario.setTotpEnabled(true);
        usuarioRepository.save(usuario);

        return ResponseEntity.ok(Map.of("message", "TOTP habilitado exitosamente"));
    }

    @PostMapping("/totp/disable")
    public ResponseEntity<?> disableTotp(
            @RequestBody Map<String, Integer> body) {

        Integer code = body.get("totpCode");
        if (code == null) {
            return ResponseEntity.badRequest().body("totpCode requerido");
        }

        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElse(null);

        if (usuario == null || !usuario.isTotpEnabled()) {
            return ResponseEntity.badRequest()
                    .body("TOTP no est\u00e1 habilitado");
        }

        boolean isValid = totpService.verifyCode(
                usuario.getTotpSecret(), code);

        if (!isValid) {
            return ResponseEntity.badRequest()
                    .body("C\u00f3digo TOTP inv\u00e1lido");
        }

        usuario.setTotpSecret(null);
        usuario.setTotpEnabled(false);
        usuarioRepository.save(usuario);

        return ResponseEntity.ok(Map.of("message", "TOTP deshabilitado exitosamente"));
    }

    @PostMapping("/google")
    public ResponseEntity<?> loginGoogle(
            @RequestBody GoogleAuthRequest request) {

        System.out.println("TOKEN RECIBIDO: " + request.getToken());
        try {
            var payload = googleAuthService.verify(request.getToken());
            String email = payload.getEmail();
            String nombre = (String) payload.get("name");

            String role = EMAIL_ROLES.get(email);
            if (role == null) {
                return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Usuario no autorizado");
            }

            String jwt = jwtService.generateToken(email);

            return ResponseEntity.ok(Map.of(
                "token", jwt,
                "email", email,
                "nombre", nombre,
                "role", role
            ));

        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Token de Google inv\u00e1lido");
        }
    }
}
