package model;

public class Siparis {
    String Sehir;


    public Siparis() {

    }
    public String getSehir() {
        return Sehir;
    }
    public void setSehir(String sehir) {
        Sehir = sehir;
    }

    @Override
    public String toString(){
        return getSehir();
    }
}
