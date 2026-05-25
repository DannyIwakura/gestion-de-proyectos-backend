package com.gruposolutia.gestion_proyectos.dto;

public class TotpVerificationRequest {

    private String preToken;
    private int totpCode;

    public String getPreToken() {
        return preToken;
    }

    public void setPreToken(String preToken) {
        this.preToken = preToken;
    }

    public int getTotpCode() {
        return totpCode;
    }

    public void setTotpCode(int totpCode) {
        this.totpCode = totpCode;
    }
}
