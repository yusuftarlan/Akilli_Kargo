package util;

import java.util.EmptyStackException;

public class Stack<T> {
    private Node<T> top;
    private int size;

    public Stack() {
        top = null;
        size = 0;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public void push(T data, boolean isPremium) {
        Node<T> newNode = new Node<>(data);
        newNode.setPriority(isPremium);

        if (isEmpty()) {
            top = newNode;
            size++;
            return;
        }

        if (isPremium) {
            // Insert below existing premium nodes but above normals
            if (!top.isPriority()) {
                // No premium at top, put new premium on top
                newNode.next = top;
                top = newNode;
            } else {
                // Traverse premium block
                Node<T> current = top;
                while (current.next != null && current.next.isPriority()) {
                    current = current.next;
                }
                newNode.next = current.next;
                current.next = newNode;
            }
        } else {
            // Normal order -> insert below last premium (or at top if none premium)
            if (!top.isPriority()) {
                // No premium present, push on top
                newNode.next = top;
                top = newNode;
            } else {
                Node<T> current = top;
                while (current.next != null && current.next.isPriority()) {
                    current = current.next;
                }
                newNode.next = current.next;
                current.next = newNode;
            }
        }
        size++;
    }

    public Node<T> pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        Node<T> popped = top;
        top = top.next;
        popped.next = null;
        size--;
        return popped;
    }

    public Node<T> peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return top;
    }

    public int size() {
        return size;
    }

    // Method to convert stack to queue
    public Queue<T> toQueue(int capacity) {
        Queue<T> queue = new Queue<>(capacity);
        Stack<T> tempStack = new Stack<>();

        // First reverse the stack to get FIFO order
        while (!isEmpty()) {
            Node<T> node = pop();
            tempStack.push(node.getData(), node.isPriority());
        }

        // Now add all items to the queue with their priority
        while (!tempStack.isEmpty()) {
            Node<T> node = tempStack.pop();
            queue.enqueue(node, node.isPriority());
        }

        return queue;
    }
}