package br.com.marzinhogas.entregadores.Models;

public class Imei {

    private String imei;
    private String usuariodocelular;

    public Imei() {
    }

    public Imei(String imei, String usuariodocelular) {
        this.imei = imei;
        this.usuariodocelular = usuariodocelular;
    }

    public String getUsuario() {
        return usuariodocelular;
    }

    public void setUsuario(String usuariodocelular) {
        this.usuariodocelular = usuariodocelular;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

}
