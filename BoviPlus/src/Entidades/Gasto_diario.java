package Entidades;

public class Gasto_diario {
    
    public int id_alimento;
    public double valor;
    public String dia;

    public Gasto_diario(int id_alimento, double valor, String dia) {
        this.id_alimento = id_alimento;
        this.valor = valor;
        this.dia = dia;
    }

    public int getId_alimento() {
        return id_alimento;
    }

    public void setId_alimento(int id_alimento) {
        this.id_alimento = id_alimento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }
    
    
    
}
