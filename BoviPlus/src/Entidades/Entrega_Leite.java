package Entidades;



public class Entrega_Leite {
    private int id_entrega;
    private double qtd;
    private String inicio;
    private String fim;
    private double valor_litro;
    private int id_conta;

    public int getId_conta() {
        return id_conta;
    }

    public void setId_conta(int id_conta) {
        this.id_conta = id_conta;
    }

    public int getId_entrega() {
        return id_entrega;
    }

    public void setId_entrega(int id_entrega) {
        this.id_entrega = id_entrega;
    }

    public double getQtd() {
        return qtd;
    }

    public void setQtd(double qtd) {
        this.qtd = qtd;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFim() {
        return fim;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }

    public double getValor_litro() {
        return valor_litro;
    }

    public void setValor_litro(double valor_litro) {
        this.valor_litro = valor_litro;
    }
}
