package org.academy;

public class Main {
    public static void main(String[] args) {
        var queue = new BlockingQueue(5);

        var producer1 = new Thread(new Producer(queue));
        var producer2 = new Thread(new Producer(queue));
        var consumer1 = new Thread(new Consumer(queue));
        var consumer2 = new Thread(new Consumer(queue));

        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();

    }
}