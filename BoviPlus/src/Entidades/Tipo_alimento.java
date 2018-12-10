package Entidades;

/**
 *
 * @author Marlos Augusto
 */
public class Tipo_alimento {
    private int id_tipo_alimento;
    private String nome;
    private String Descricao;

    public int getId_tipo_alimento() {
        return id_tipo_alimento;
    }

    public void setId_tipo_alimento(int id_tipo_alimento) {
        this.id_tipo_alimento = id_tipo_alimento;
    }

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
}
