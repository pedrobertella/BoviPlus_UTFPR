package Entidades;
 
public class Reproducao {
    private int id_gestacao;
    private int id_mae;
    private int id_pai;
    private String previsao;
    private String data_inseminacao;

    public int getId_gestacao() {
        return id_gestacao;
    }

    public void setId_gestacao(int id_gestacao) {
        this.id_gestacao = id_gestacao;
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

    public String getPrevisao() {
        return previsao;
    }

    public void setPrevisao(String previsao) {
        this.previsao = previsao;
    }

    public String getData_inseminacao() {
        return data_inseminacao;
    }

    public void setData_inseminacao(String data_inseminacao) {
        this.data_inseminacao = data_inseminacao;
    }

    
}
