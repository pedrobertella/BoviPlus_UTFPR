package Entidades;

public class Vacina {
    private int id_vacina;
    private String nome;
    private String indicacao;
    private String contra_indc;
    private boolean periodica;
    private String campanha;
    private String mes;
    private String cat_animais;
    

    public int getId_vacina() {
        return id_vacina;
    }

    public void setId_vacina(int idVacina) {
        this.id_vacina = idVacina;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIndicacao() {
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

    public boolean getPeriodica() {
        return periodica;
    }

    public void setPeriodica(boolean periodica) {
        this.periodica = periodica;
    }

    public String getCampanha() {
        return campanha;
    }

    public void setCampanha(String campanha) {
        this.campanha = campanha;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getCat_animais() {
        return cat_animais;
    }

    public void setCat_animais(String cat_animais) {
        this.cat_animais = cat_animais;
    }

}
