package Entidades;

public class Pref {

    public int diasec, dianasc, cpagar, creceber, diavacina, diaexame, diasest;
    public String a1, a2, a3, a4, a5;
    public boolean mudo, autoalimentacao;

    public Pref(int i, int j, String a, String b, String c, String d, String e, int k, int l, int aa, int bb, boolean m, boolean n, int ddd) {
        diasec = i;
        dianasc = j;
        a1 = a;
        a2 = b;
        a3 = c;
        a4 = d;
        a5 = e;
        cpagar = k;
        creceber = l;
        diavacina = aa;
        diaexame = bb;
        mudo = m;
        autoalimentacao = n;
        diasest = ddd;
    }
}
