package Entidades;

public class Enfermidade {
    private int id_animal;
    private int id_doenca;
    private int tempo_carencia;
    private int tempo_tratamento;
    private String data_enfermidade;

    public int getId_animal() {
        return id_animal;
    }

    public void setId_animal(int id_animal) {
        this.id_animal = id_animal;
    }

    public int getId_doenca() {
        return id_doenca;
    }

    public void setId_doenca(int id_doenca) {
        this.id_doenca = id_doenca;
    }
    
    public int getTempo_carencia() {
        return tempo_carencia;
    }

    public void setTempo_carencia(int tempo_carencia) {
        this.tempo_carencia = tempo_carencia;
    }

    public int getTempo_tratamento() {
        return tempo_tratamento;
    }

    public void setTempo_tratamento(int tempo_tratamento) {
        this.tempo_tratamento = tempo_tratamento;
    }

    public String getData_enfermidade() {
        return data_enfermidade;
    }

    public void setData_enfermidade(String data_enfermidade) {
        this.data_enfermidade = data_enfermidade;
    }
    
}
