package Entidades;

public class Animal {
    private int id_animal;
    private String nome;
    private String nascimento;
    private double peso;
    private String descricao;
    private int id_mae;
    private int id_pai;
    private int id_tipo;
    private int id_raca;
    private boolean lactacao;
    private int id_lote;
    //private int id_exame;
    
    public Animal(){
        this.id_animal = 0;
        this.nome = "";
        this.nascimento = "1000-01-01";
        this.peso = 0.0;
        this.descricao = "";
        this.id_mae = 0;
        this.id_pai = 0;
        this.id_tipo = 0;
        this.id_raca = 0;
    }

    public int getId_animal() {
        return id_animal;
    }

    public void setId_animal(int idBrinco) {
        this.id_animal = idBrinco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getId_mae() {
        return id_mae;
    }

    public void setId_mae(int id_mae) {
        this.id_mae = id_mae;
    }

    public int getId_pai() {
        return id_pai;
    }

    public void setId_pai(int id_pai) {
        this.id_pai = id_pai;
    }

    public int getId_tipo() {
        return id_tipo;
    }

    public void setId_tipo(int id_tipo) {
        this.id_tipo = id_tipo;
    }

    public int getId_raca() {
        return id_raca;
    }

    public void setId_raca(int id_raca) {
        this.id_raca = id_raca;
    }

    public boolean isLactacao() {
        return lactacao;
    }

    public void setLactacao(boolean lactacao) {
        this.lactacao = lactacao;
    }

    public int getId_lote() {
        return id_lote;
    }

    public void setId_lote(int id_lote) {
        this.id_lote = id_lote;
    }

    /*public int getId_exame() {
        return id_exame;
    }

    public void setId_exame(int id_exame) {
        this.id_exame = id_exame;
    }*/
}