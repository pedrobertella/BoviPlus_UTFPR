package Entidades;

import java.time.LocalDate;

public class Evento {

    public String texto;
    public String origem;
    public LocalDate data;

    public Evento(String t, String o, LocalDate data) {
        this.texto = t;
        this.origem = o;
        this.data = data;
    }

    public String getTexto() {
        return texto;
    }

    public String getOrigem() {
        return origem;
    }

    public LocalDate getData() {
        return data;
    }

}
