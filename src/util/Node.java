package util;

public class Node <T> {
    private T data;
    public Node<T> next;
    public Node<T> prev;
    private boolean priority = false;

    public Node(T data) {
        this.data = data;
    }
    public T getData() {
        return data;
    }
    public boolean isPriority() {
        return priority;
    }
    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    @Override
    public String toString(){
        return data.toString();
    }
}
