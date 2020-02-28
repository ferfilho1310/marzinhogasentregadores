package br.com.marzinhogas.entregadores.Models;

public class Entregador {

    private String nome;
    private String endereco;
    private String email;
    private String senha;
    private String confirmarsenha;
    private String sexo;
    private String token;
    private boolean online;
    private String identificador;

    public Entregador() {
    }

    public Entregador(String nome, String endereco, String email, String senha,
                      String confirmarsenha, String sexo, String token,String identificador, Boolean online) {
        this.nome = nome;
        this.endereco = endereco;
        this.email = email;
        this.senha = senha;
        this.confirmarsenha = confirmarsenha;
        this.sexo = sexo;
        this.token = token;
        this.online = online;
        this.identificador = identificador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirmarsenha() {
        return confirmarsenha;
    }

    public void setConfirmarsenha(String confirmarsenha) {
        this.confirmarsenha = confirmarsenha;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }
}
