package util;

import java.util.*;
import model.Siparis;

public class Queue<T> {
    public Node<T> front;
    public Node<T> rear;
    private int size;
    private final int capacity;
    private int numOfPriority;

    // Şehir bazlı hızlı erişim için
    private Map<String, List<Node<T>>> cityMap;

    public Queue(int capacity){
        this.capacity = capacity;
        front = null;
        rear = null;
        size = 0;
        numOfPriority = 0;
        cityMap = new HashMap<>();
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

        // Şehir map'ine ekle (eğer Siparis ise)
        if (node.getData() instanceof Siparis) {
            Siparis siparis = (Siparis) node.getData();
            cityMap.computeIfAbsent(siparis.getSehir(), k -> new ArrayList<>()).add(node);
        }
    }

    public Node<T> dequeue(){
        if(isEmpty()){
            System.out.println("Queue is empty");
            return null;
        }
        Node<T> temp = front;
        
        // City map'ten kaldır
        if (temp.getData() instanceof Siparis) {
            Siparis siparis = (Siparis) temp.getData();
            List<Node<T>> cityNodes = cityMap.get(siparis.getSehir());
            if (cityNodes != null) {
                cityNodes.remove(temp);
                if (cityNodes.isEmpty()) {
                    cityMap.remove(siparis.getSehir());
                }
            }
        }
        
        if (front == rear) {
            front = null;
            rear = null;
        } else {
            front = front.next;
            temp.next = null;
        }
        size--;
        if (numOfPriority > 0 && temp.isPriority()){
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
    public Siparis[] kargoHazirla() {
        if (isEmpty()) {
            System.out.println("Queue is empty, cannot prepare cargo");
            return new Siparis[0];
        }
        
        int i = 0;
        Siparis[] kargo = new Siparis[Math.min(size, 10)];
        
        Node<T> firstNode = dequeue();
        if (firstNode == null) {
            return new Siparis[0];
        }
        
        kargo[i++] = castToSiparis(firstNode.getData());
        String hedefSehir = kargo[0].getSehir();
        
        // Front'tan aynı şehir siparişlerini çıkar
        while (!isEmpty() && front != null) {
            Siparis frontSiparis = castToSiparis(front.getData());
            if (frontSiparis.getSehir().equals(hedefSehir) && i < kargo.length) {
                kargo[i++] = castToSiparis(dequeue().getData());
            } else {
                break;
            }
        }
        
        // Ortadan aynı şehir siparişlerini çıkar
        Node<T> current = front;
        while (current != null && current.next != null && i < kargo.length) {
            Siparis nextSiparis = castToSiparis(current.next.getData());
            if (nextSiparis.getSehir().equals(hedefSehir)) {
                kargo[i++] = nextSiparis;
                current.next = current.next.next;
                size--;
                if (current.next == null) {
                    rear = current;
                }
            } else {
                current = current.next;
            }
        }
        
        // Sadece dolu elemanları döndür
        Siparis[] result = new Siparis[i];
        System.arraycopy(kargo, 0, result, 0, i);
        return result;
    }

    // Şehir bazlı kargo hazırlama (optimized)
    public List<Siparis> kargoHazirlaOptimized(String targetCity) {
        List<Siparis> kargo = new ArrayList<>();
        List<Node<T>> cityNodes = cityMap.get(targetCity);
        
        if (cityNodes == null || cityNodes.isEmpty()) {
            return kargo;
        }
        
        // İlk 10 sipariş al
        int maxCount = Math.min(10, cityNodes.size());
        for (int i = 0; i < maxCount; i++) {
            Node<T> node = cityNodes.get(i);
            removeNode(node);
            kargo.add(castToSiparis(node.getData()));
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

    // Generic güvenliği için helper method
    @SuppressWarnings("unchecked")
    private Siparis castToSiparis(T data) {
        if (data instanceof Siparis) {
            return (Siparis) data;
        }
        throw new ClassCastException("Data is not of type Siparis");
    }

    public class QueueIterator implements Iterator<T> {
        private Node<T> current;
        
        public QueueIterator() {
            this.current = front;
        }
        
        @Override
        public boolean hasNext() {
            return current != null;
        }
        
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T data = current.getData();
            current = current.next;
            return data;
        }
    }

    public Iterator<T> iterator() {
        return new QueueIterator();
    }

    // removeNode metodunu ekle
    private void removeNode(Node<T> nodeToRemove) {
        if (isEmpty() || nodeToRemove == null) {
            return;
        }
        
        // Eğer front node'unu siliyorsak
        if (front == nodeToRemove) {
            dequeue();
            return;
        }
        
        // Ortadaki bir node'u siliyorsak
        Node<T> current = front;
        while (current != null && current.getNext() != nodeToRemove) {
            current = current.getNext();
        }
        
        if (current != null) {
            current.setNext(nodeToRemove.getNext());
            if (nodeToRemove == rear) {
                rear = current;
            }
            size--;
            
            // Priority sayısını güncelle
            if (nodeToRemove.isPriority()) {
                numOfPriority--;
            }
            
            // City map'ten kaldır
            if (nodeToRemove.getData() instanceof Siparis) {
                Siparis siparis = (Siparis) nodeToRemove.getData();
                List<Node<T>> cityNodes = cityMap.get(siparis.getSehir());
                if (cityNodes != null) {
                    cityNodes.remove(nodeToRemove);
                    if (cityNodes.isEmpty()) {
                        cityMap.remove(siparis.getSehir());
                    }
                }
            }
        }
    }
}
