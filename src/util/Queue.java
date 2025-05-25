package util;


import model.Siparis;

public class Queue <T>{
    public Node<T> front;
    public Node<T> rear;
    private int size;
    private final int capacity;
    private int numOfPriority;

    public Queue(int capacity){

        this.capacity = capacity;
        front = null;
        rear = null;
        size = 0;
        numOfPriority = 0;
    }

    public void enqueue(Node<T> node,boolean priority){
        if(isFull()) {
            System.out.println("Queue is full");
            return;
        }
        node.setPriority(priority);

        if(isEmpty()){
            node.next = null;
            front = node;
            rear = node;
            size = 1;
            if(priority){
                numOfPriority = 1;
            }
            return;
        }
        else{
            // Listeye ilk defa öncelikli eleman eklenecekse
            if(node.isPriority() && numOfPriority == 0){
                node.next = front;
                front = node;
                numOfPriority++;
                return;
            }
            if (node.isPriority() && numOfPriority > 0){
                Node<T> temp = front;
                for (int i = 0; i < numOfPriority - 1; i++){
                    temp = temp.next;
                }
                node.next = temp.next;
                if (node.next == null){
                    rear = node;
                }
                temp.next = node;
                numOfPriority++;

            } else if (!node.isPriority()){
                rear.next = node;
                node.next = null;
                rear = node;
            }
            size++;
        }
    }

    public Node<T> dequeue(){
        //Sıra boşsa
        if(isEmpty()){
            System.out.println("Queue is empty");
            return null;
        }
        Node<T> temp = front;
        //Sırada tek eleman varsa
        if (front == rear) {
            front = null;
            rear = null;
        }
        //Diğer bütün durumlar
        else {
            front = front.next;
            temp.next = null;
        }
        size--;
        if (numOfPriority > 0){
            numOfPriority--;
        }
        return temp;
    }

    public void printQueue(){
        Node<T> temp = front;
        if(isEmpty()){
            System.out.println("Queue is empty");
        }else{
            System.out.println("Front");
            while(temp != null){
                System.out.print(temp+" öncelikli: "+temp.isPriority());
                System.out.println();
                temp = temp.next;
            }
            System.out.println("Rear");
        }
    }
    public Siparis[] kargoHazirla(){
        int i = 1;

        Siparis [] kargo = new Siparis[10];
        kargo[0] = (Siparis)dequeue().getData();
        String hedefSehir = kargo[0].getSehir();
        boolean frontCikti = true;

        while (frontCikti){
            if (((Siparis)front.getData()).getSehir().equals(hedefSehir)){
                System.out.println("front çikti");
                kargo[i++] = (Siparis) dequeue().getData();
            }else{
                frontCikti = false;
            }
        }

        Node<T> temp = front;
        while (temp.next != null){
            if (((Siparis)temp.next.getData()).getSehir().equals(hedefSehir)){
                System.out.println("ortadan çikti");
                kargo[i++] = (Siparis) temp.next.getData();
                temp.next = temp.next.next;
            }
            temp = temp.next;
        }
        return kargo;
    }

    public int getCapacity() {
        return capacity;
    }
    public int getSize() {
        return size;
    }

    public int getNumOfPriority() {
        return numOfPriority;
    }

    public boolean isEmpty(){
        return size == 0;
    }
    public boolean isFull(){
        return size == capacity;
    }
}
