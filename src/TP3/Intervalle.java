package TP3;

public class Intervalle {

    float borneInf;
    float borneSup;

    public Intervalle(float borneInf, float borneSup) {
        this.borneInf = borneInf;
        this.borneSup = borneSup;
    }

    public float moyenne() {
        return (borneSup + borneInf) / 2;
    }

    @Override
    public String toString() {
        return "[" +
                borneInf +
                " - " + borneSup +
                ']';
    }
}
