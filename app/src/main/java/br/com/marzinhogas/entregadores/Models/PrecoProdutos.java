package br.com.marzinhogas.entregadores.Models;

import androidx.annotation.NonNull;

public class PrecoProdutos {

    private String precoagua;
    private String precogas;

    public PrecoProdutos() {

    }

    public PrecoProdutos(String precoagua, String precogas) {
        this.precoagua = precoagua;
        this.precogas = precogas;
    }

    public String getPreco_agua() {
        return precoagua;
    }

    public void setPreco_agua(String precoagua) {
        this.precoagua = precoagua;
    }

    public String getPreco_gas() {
        return precogas;
    }

    public void setPreco_gas(String precogas) {
        this.precogas = precogas;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String tabela = "Preço do gás: " + getPreco_gas() + "\nPreço da Água: " + getPreco_agua();
        stringBuilder.append(tabela);
        return tabela;
    }
}
