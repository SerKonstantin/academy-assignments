package org.academy;

public class ComplexTask {
    public int execute() {
        int result = (int) (10 * Math.random());
        System.out.println(Thread.currentThread().getName() + " executed task with result: " + result);
        return result;
    }
}
