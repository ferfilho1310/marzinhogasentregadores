package br.com.marzinhogas.entregadores.Models;


import android.os.Parcel;
import android.os.Parcelable;

public class Pedido implements Parcelable {

    private String nome;
    private String produto;
    private String endereco;
    private int quantidade_gas;
    private int quantidade_agua;
    private String data;
    private String user_id_pedido;
    private String horario;
    private Boolean entregue;

    public Pedido() {
    }

    public Pedido(String nome, String produto, String endereco, int quantidade_gas,
                  int quantidade_agua, String data, String user_id_pedido, String horario, Boolean entregue) {
        this.nome = nome;
        this.produto = produto;
        this.endereco = endereco;
        this.quantidade_gas = quantidade_gas;
        this.quantidade_agua = quantidade_agua;
        this.data = data;
        this.user_id_pedido = user_id_pedido;
        this.horario = horario;
        this.entregue = entregue;
    }

    protected Pedido(Parcel in) {
        nome = in.readString();
        produto = in.readString();
        endereco = in.readString();
        quantidade_gas = in.readInt();
        quantidade_agua = in.readInt();
        data = in.readString();
        user_id_pedido = in.readString();
        horario = in.readString();
        entregue = Boolean.valueOf(in.readString());
    }

    public static final Creator<Pedido> CREATOR = new Creator<Pedido>() {
        @Override
        public Pedido createFromParcel(Parcel in) {
            return new Pedido(in);
        }

        @Override
        public Pedido[] newArray(int size) {
            return new Pedido[size];
        }
    };

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getQuantidade_gas() {
        return quantidade_gas;
    }

    public void setQuantidade_gas(int quantidade_gas) {
        this.quantidade_gas = quantidade_gas;
    }

    public int getQuantidade_agua() {
        return quantidade_agua;
    }

    public void setQuantidade_agua(int quatidade_agua) {
        this.quantidade_agua = quatidade_agua;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUser_id_pedido() {
        return user_id_pedido;
    }

    public void setUser_id_pedido(String user_id_pedido) {
        this.user_id_pedido = user_id_pedido;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Boolean getEntregue() {
        return entregue;
    }

    public void setEntregue(Boolean entregue) {
        this.entregue = entregue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nome);
        parcel.writeString(produto);
        parcel.writeString(endereco);
        parcel.writeInt(quantidade_gas);
        parcel.writeInt(quantidade_agua);
        parcel.writeString(data);
        parcel.writeString(user_id_pedido);
        parcel.writeString(horario);
        parcel.writeString(String.valueOf(entregue));
    }
}
