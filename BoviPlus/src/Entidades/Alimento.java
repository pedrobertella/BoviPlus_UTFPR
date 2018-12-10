package Entidades;

public class Alimento {
    private int id;
    private String nome;
    private int tipo;
    private int unid;

    public Alimento(int idAlimento, String nome, int tipo, int unid) {
        this.id = idAlimento;
        this.nome = nome;
        this.tipo = tipo;
        this.unid = unid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getUnid() {
        return unid;
    }

    public void setUnid(int unid) {
        this.unid = unid;
    }

    @Override
    public String toString(){
        return nome;
    }
    
}
