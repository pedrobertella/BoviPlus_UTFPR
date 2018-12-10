package Entidades;

public class Alimentacao {
    private int id_alimento;
    private int id_animal;
    private double qtd;
    private String data_alimentacao;
    

    public int getId_alimento() {
        return id_alimento;
    }

    public void setId_alimento(int id_alimento) {
        this.id_alimento = id_alimento;
    }

    public int getId_animal() {
        return id_animal;
    }

    public void setId_animal(int id_animal) {
        this.id_animal = id_animal;
    }

    public double getQtd() {
        return qtd;
    }

    public void setQtd(double qtd) {
        this.qtd = qtd;
    }

    public String getData_alimentacao() {
        return data_alimentacao;
    }

    public void setData_alimentacao(String data_alimentacao) {
        this.data_alimentacao = data_alimentacao;
    }
}
