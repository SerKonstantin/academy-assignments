package org.academy;

public class Consumer implements Runnable {
    private final BlockingQueue blockingQueue;

    public Consumer(BlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            var message = blockingQueue.dequeue();
            System.out.println("Consumer received a message: " + message);
        }
    }
}
