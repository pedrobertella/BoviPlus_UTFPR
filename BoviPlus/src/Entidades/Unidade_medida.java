package Entidades;

/**
 *
 * @author Marlos Augusto
 */
public class Unidade_medida {
    private int id_undmdd;
    private String nome;
    private String Descricao;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String Descricao) {
        this.Descricao = Descricao;
    }

    public int getId_undmdd() {
        return id_undmdd;
    }

    public void setId_undmdd(int id_undmdd) {
        this.id_undmdd = id_undmdd;
    }
}
