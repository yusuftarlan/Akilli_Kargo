package test;

import model.Satici;
import model.Siparis;
import util.Node;
import util.Queue;

import java.util.Arrays;

public class Test {



    public static void main(String[] args) {

        Siparis s1 = new Siparis();
        Siparis s2 = new Siparis();
        Siparis s3 = new Siparis();
        Siparis s4 = new Siparis();
        Siparis s5 = new Siparis();
        Siparis s6 = new Siparis();
        Siparis s7 = new Siparis();
        s1.setSehir("istanbul");
        s2.setSehir("istanbul");
        s3.setSehir("bursa");
        s4.setSehir("mersin");
        s5.setSehir("istanbul");
        s6.setSehir("x");
        s7.setSehir("manisa");

        Queue<Siparis> sira = new Queue<>(10);

        sira.enqueue(new Node<>(s1),true);

        sira.enqueue(new Node<>(s2),true);

        sira.enqueue(new Node<>(s3),false);

        sira.enqueue(new Node<>(s4),true);

        sira.enqueue(new Node<>(s5),false);

        sira.enqueue(new Node<>(s6),false);

        sira.enqueue(new Node<>(s7),true);


        Satici s = new Satici(10);
        sira.printQueue();
        Siparis [] x = sira.kargoHazirla();

        System.out.println(Arrays.toString(x));

        sira.printQueue();




    }
}
