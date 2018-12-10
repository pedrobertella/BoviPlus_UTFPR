package Entidades;

public class Alim_Replica {
    public int alim, animal;
    public double quant;

    public Alim_Replica(int alim, int animal, double quant) {
        this.alim = alim;
        this.animal = animal;
        this.quant = quant;
    }

    public int getAlim() {
        return alim;
    }

    public void setAlim(int alim) {
        this.alim = alim;
    }

    public int getAnimal() {
        return animal;
    }

    public void setAnimal(int animal) {
        this.animal = animal;
    }

    public double getQuant() {
        return quant;
    }

    public void setQuant(double quant) {
        this.quant = quant;
    }
    
}
