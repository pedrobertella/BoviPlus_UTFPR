package Entidades;

public class Contas_Receber {
    public int id_contas_receber;
    public double valor;
    public String data_recebimento;
    public String data_vencimento;
    public double qtd;
    public String descricao;
    public boolean producao;

    public int getId_contas_receber() {
        return id_contas_receber;
    }

    public void setId_contas_receber(int id_contas_receber) {
        this.id_contas_receber = id_contas_receber;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getData_recebimento() {
        return data_recebimento;
    }

    public void setData_recebimento(String data_recebimento) {
        this.data_recebimento = data_recebimento;
    }

    public String getData_vencimento() {
        return data_vencimento;
    }

    public void setData_vencimento(String data_vencimento) {
        this.data_vencimento = data_vencimento;
    }

    public double getQtd() {
        return qtd;
    }

    public void setQtd(double qtd) {
        this.qtd = qtd;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isProducao() {
        return producao;
    }

    public void setProducao(boolean producao) {
        this.producao = producao;
    }
   
}
