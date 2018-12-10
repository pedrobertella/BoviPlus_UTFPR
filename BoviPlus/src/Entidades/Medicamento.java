package Entidades;

public class Medicamento {
    private int id_med;
    private String nome;
    private String indicacao;
    private String contra_indc;
    

    public int getId_med() {
        return id_med;
    }

    public void setId_med(int id_med) {
        this.id_med = id_med;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return indicacao;
    }

    public void setIndicacao(String indicacao) {
        this.indicacao = indicacao;
    }

    public String getContra_indc() {
        return contra_indc;
    }

    public void setContra_indc(String contra_indc) {
        this.contra_indc = contra_indc;
    }

}
