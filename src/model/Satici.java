package model;

import util.Node;
import util.Queue;

public class Satici {

    public Queue<Siparis> siparisQueue;

    public Satici(int capacity) {
        siparisQueue = new Queue<>(capacity);
    }


    public Kargo  kargoHazirla(){

        return new Kargo(siparisQueue.kargoHazirla());

    }
}
