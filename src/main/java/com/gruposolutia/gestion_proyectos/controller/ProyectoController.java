package com.gruposolutia.gestion_proyectos.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gruposolutia.gestion_proyectos.model.Proyecto;
import com.gruposolutia.gestion_proyectos.service.ProyectoService;

@RestController
public class ProyectoController {

	private final ProyectoService proyectoService;

	public ProyectoController(ProyectoService proyectoService) {
		super();
		this.proyectoService = proyectoService;
	}
	
	@GetMapping("/api/public/proyectos")
	public List<Proyecto> obtenerProyectos() {
		return proyectoService.obtenerProyectos();
		
		
	}
	
	
}
