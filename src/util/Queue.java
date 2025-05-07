package util;

public class Queue <Siparis>{
    private Node<Siparis> front;
    private Node<Siparis> rear;
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

    public void enqueue(Node<Siparis> node,boolean priority){

        if(isFull()) {
            System.out.println("Queue is full");
            return;
        }
        node.setPriority(priority);
        if(isEmpty()){
            node.next = null;
            front = node;
            rear = node;
            size++;
            if(node.isPriority())
                numOfPriority++;
        }
        else {
            // Listeye ilk defa öncelikli eleman eklenecekse
            if(node.isPriority() && numOfPriority == 0){
                node.next = front;
                front = node;
                numOfPriority++;

            } else if (node.isPriority() && numOfPriority > 0){
                Node<Siparis> temp = front;
                for (int i = 0; i < numOfPriority - 1; i++){
                    temp = temp.next;
                }
                node.next = temp.next;
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

    public Node<Siparis> dequeue(){
        //Sıra boşsa
        if(isEmpty()){
            System.out.println("Queue is empty");
            return null;
        }
        Node<Siparis> temp = front;
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
        Node<Siparis> temp = front;
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

    public boolean isEmpty(){
        return size == 0;
    }
    public boolean isFull(){
        return size == capacity;
    }
}
