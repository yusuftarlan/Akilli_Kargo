package util;

public class Node<T> {
    private T data;
    public Node<T> next;
    private Node<T> prev;
    private boolean priority = false;
    
    public Node(T data) {
        this.data = data;
    }
    
    public Node(T data, boolean priority) {
        this.data = data;
        this.priority = priority;
    }
    
    // Getters and setters
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
    
    public Node<T> getNext() { return next; }
    public void setNext(Node<T> next) { this.next = next; }
    
    public Node<T> getPrev() { return prev; }
    public void setPrev(Node<T> prev) { this.prev = prev; }
    
    public boolean isPriority() { return priority; }
    public void setPriority(boolean priority) { this.priority = priority; }
    
    @Override
    public String toString() {
        return "Node{data=" + data + ", priority=" + priority + "}";
    }
}
