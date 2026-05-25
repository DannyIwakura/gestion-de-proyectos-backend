package com.gruposolutia.gestion_proyectos.dto;

public class TotpSetupResponse {

    private String secret;
    private String qrCodeUri;

    public TotpSetupResponse() {
    }

    public TotpSetupResponse(String secret, String qrCodeUri) {
        this.secret = secret;
        this.qrCodeUri = qrCodeUri;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getQrCodeUri() {
        return qrCodeUri;
    }

    public void setQrCodeUri(String qrCodeUri) {
        this.qrCodeUri = qrCodeUri;
    }
}
