package Entidades;

public class Raca {
   private int id_raca;
   private String nome;
   private int tempo_gestacao;
   private String descricao;

    public int getId_raca() {
        return id_raca;
    }

    public void setId_raca(int id_raca) {
        this.id_raca = id_raca;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTempo_gestacao() {
        return tempo_gestacao;
    }

    public void setTempo_gestacao(int tempo_gestacao) {
        this.tempo_gestacao = tempo_gestacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
   @Override
    public String toString(){
        return nome;
    }
    
}
