package br.com.marzinhogas.entregadores.Models;

public class Veiculo {

    private String placa;

    public Veiculo(String placa) {
        this.placa = placa;
    }

    public Veiculo(){
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
}

