package org.academy;

import java.util.ArrayDeque;

public class BlockingQueue {
    private final ArrayDeque<String> queue = new ArrayDeque<>();
    private final int capacity;

    public BlockingQueue() {
        this.capacity = 10;
    }

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void enqueue(String message) {
        while (queue.size() == capacity) {
            try {
                System.out.println("Queue is full. " + Thread.currentThread().getName() + " is waiting.");
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        queue.addFirst(message);
        System.out.println(Thread.currentThread().getName() + " enqueued a message: " + message);

        this.notify();
        System.out.println(Thread.currentThread().getName() + " notified a waiting thread.");
    }

    public synchronized String dequeue() {
        while (queue.isEmpty()) {
            try {
                System.out.println("Queue is empty. " + Thread.currentThread().getName() + " is waiting.");
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        String message = queue.removeLast();
        System.out.println(Thread.currentThread().getName() + " dequeued a message: " + message);

        this.notify();
        System.out.println(Thread.currentThread().getName() + " notified a waiting thread.");

        return message;
    }

    public synchronized int size() {
        return queue.size();
    }
}
