package Entidades;

public class Custo_reproducao {
    private int id_gestacao;
    private int id_contas_pagar;
    private double valor;
    private int qtd;

    public int getId_gestacao() {
        return id_gestacao;
    }

    public void setId_gestacao(int id_gestacao) {
        this.id_gestacao = id_gestacao;
    }

    public int getId_contas_pagar() {
        return id_contas_pagar;
    }

    public void setId_contas_pagar(int id_contas_pagar) {
        this.id_contas_pagar = id_contas_pagar;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }
    
}
