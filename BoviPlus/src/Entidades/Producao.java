package Entidades;

public class Producao {
    private int id_vaca;
    private String data_producao;
    private double litragem;
    private int id_entrega;

    public int getId_vaca() {
        return id_vaca;
    }

    public void setId_vaca(int id_vaca) {
        this.id_vaca = id_vaca;
    }

    public String getData_producao() {
        return data_producao;
    }

    public void setData_producao(String data_producao) {
        this.data_producao = data_producao;
    }

    public double getLitragem() {
        return litragem;
    }

    public void setLitragem(double litragem) {
        this.litragem = litragem;
    }

    public int getId_entrega() {
        return id_entrega;
    }

    public void setId_entrega(int id_entrega) {
        this.id_entrega = id_entrega;
    }

}
