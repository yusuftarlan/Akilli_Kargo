package util;

public class Stack<T> {
    private Node<T> top;
    private int size;

    public Stack() {
        this.top = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public void push(T data, boolean isPremium) {
        Node<T> newNode = new Node<>(data, isPremium);
        
        if (isPremium) {
            // Premium orders go to the top of premium section
            insertPremiumNode(newNode);
        } else {
            // Normal orders go to the bottom
            insertNormalNode(newNode);
        }
        size++;
    }
    
    private void insertPremiumNode(Node<T> newNode) {
        if (top == null || !top.isPriority()) {
            newNode.setNext(top);
            top = newNode;
        } else {
            // Find position among premium orders (LIFO for premium)
            newNode.setNext(top);
            top = newNode;
        }
    }
    
    private void insertNormalNode(Node<T> newNode) {
        if (top == null) {
            top = newNode;
            return;
        }
        
        // Find the end of premium orders
        Node<T> current = top;
        Node<T> prev = null;
        
        while (current != null && current.isPriority()) {
            prev = current;
            current = current.getNext();
        }
        
        if (prev == null) {
            // No premium orders, insert at top
            newNode.setNext(top);
            top = newNode;
        } else {
            // Insert after premium orders, at beginning of normal orders
            newNode.setNext(prev.getNext());
            prev.setNext(newNode);
        }
    }

    public Node<T> pop() {
        if (isEmpty()) return null;
        
        Node<T> result = top;
        top = top.getNext();
        size--;
        return result;
    }

    public Node<T> peek() {
        return top;
    }

    public int size() {
        return size;
    }

    // Method to convert stack to queue
    public Queue<T> toQueue(int capacity) {
        Queue<T> queue = new Queue<>(capacity);
        Stack<T> tempStack = new Stack<>();
        
        // Reverse order to maintain priority
        while (!isEmpty()) {
            Node<T> node = pop();
            tempStack.push(node.getData(), node.isPriority());
        }
        
        while (!tempStack.isEmpty()) {
            Node<T> node = tempStack.pop();
            queue.enqueue(node, node.isPriority());
            push(node.getData(), node.isPriority()); // Restore original stack
        }
        
        return queue;
    }
}