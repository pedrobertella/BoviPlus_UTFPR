package Entidades;

public class Estoque {
    public int id_movimento, id_alimento;
    public double quant, valor_uni;
    public String data_compra;

    public Estoque(int id_movimento, int id_alimento, double quant, double valor_uni, String data_compra) {
        this.id_movimento = id_movimento;
        this.id_alimento = id_alimento;
        this.quant = quant;
        this.valor_uni = valor_uni;
        this.data_compra = data_compra;
    }

    public int getId_movimento() {
        return id_movimento;
    }

    public int getId_alimento() {
        return id_alimento;
    }

    public double getQuant() {
        return quant;
    }

    public double getValor_uni() {
        return valor_uni;
    }

    public String getData_compra() {
        return data_compra;
    }

    public void setId_movimento(int id_movimento) {
        this.id_movimento = id_movimento;
    }

    public void setId_alimento(int id_alimento) {
        this.id_alimento = id_alimento;
    }

    public void setQuant(double quant) {
        this.quant = quant;
    }

    public void setValor_uni(double valor_uni) {
        this.valor_uni = valor_uni;
    }

    public void setData_compra(String data_compra) {
        this.data_compra = data_compra;
    }
    
    
    
}
