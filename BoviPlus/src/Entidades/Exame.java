package Entidades;

public class Exame {
    private int id_exame;
    private String nome;
    private String descricao;
    public String data_exame;
    public  String categoria;
    public int intervalo;
    public int id_animal;

    public int getId_exame() {
        return id_exame;
    }

    public void setId_exame(int id_exame) {
        this.id_exame = id_exame;
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

    /**
     * @return the data_exame
     */
    public String getData_exame() {
        return data_exame;
    }

    /**
     * @param data_exame the data_exame to set
     */
    public void setData_exame(String data_exame) {
        this.data_exame = data_exame;
    }

    /**
     * @return the categoria
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the intervalo
     */
    public int getIntervalo() {
        return intervalo;
    }

    /**
     * @param intervalo the intervalo to set
     */
    public void setIntervalo(int intervalo) {
        this.intervalo = intervalo;
    }

    /**
     * @return the id_animal
     */
    public int getId_animal() {
        return id_animal;
    }

    /**
     * @param id_animal the id_animal to set
     */
    public void setId_animal(int id_animal) {
        this.id_animal = id_animal;
    }
}
