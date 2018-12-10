package Entidades;

public class Custo_alimento {
    private int id_alimento;
    private int id_contas_pagar;
    private double qtd;
    private double valor;

    public int getId_alimento() {
        return id_alimento;
    }

    public void setId_alimento(int id_alimento) {
        this.id_alimento = id_alimento;
    }

    public int getId_contas_pagar() {
        return id_contas_pagar;
    }

    public void setId_contas_pagar(int id_contas_pagar) {
        this.id_contas_pagar = id_contas_pagar;
    }

    public double getQtd() {
        return qtd;
    }

    public void setQtd(double qtd) {
        this.qtd = qtd;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

}
