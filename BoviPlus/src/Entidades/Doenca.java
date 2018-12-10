package Entidades;

public class Doenca {
    private int id_doenca;
    private String nome;
    private String descricao;

    public int getId_doenca() {
        return id_doenca;
    }

    public void setId_doenca(int id_doenca) {
        this.id_doenca = id_doenca;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
