package Entidades;

public class Custo_equipamento {
    private int id_equipamento;
    private int id_contas_pagar;
    private int qtd;
    private double valor;

    public int getId_equipamento() {
        return id_equipamento;
    }

    public void setId_equipamento(int id_equipamento) {
        this.id_equipamento = id_equipamento;
    }

    public int getId_contas_pagar() {
        return id_contas_pagar;
    }

    public void setId_contas_pagar(int id_contas_pagar) {
        this.id_contas_pagar = id_contas_pagar;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
    
    
    
}
