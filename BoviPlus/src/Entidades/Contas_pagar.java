package Entidades;

public class Contas_pagar {
    private int id_contas_pagar;
    private double valor;
    private String data_pagamento;
    private String data_vencimento;
    private int origem;

    public int getOrigem() {
        return origem;
    }

    public void setOrigem(int origem) {
        this.origem = origem;
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
    
    public String getData_pagamento() {
        return data_pagamento;
    }

    public void setData_pagamento(String data_pagamento) {
        this.data_pagamento = data_pagamento;
    }

    public String getData_vencimento() {
        return data_vencimento;
    }

    public void setData_vencimento(String data_vencimento) {
        this.data_vencimento = data_vencimento;
    }
}
