package model;

import util.Queue;

public class Satici {

    private Queue<Siparis> siparisQueue;

    public Satici(int capacity) {
        siparisQueue = new Queue<>(capacity);
    }


    public Siparis[] kargoHazirla(){
        Siparis [] kargo = new Siparis[10];
        kargo[0] = siparisQueue.dequeue().getData();
        String hedefSehir = kargo[0].getSehir();
        return kargo;

    }
}
