package org.academy;

import java.util.concurrent.RecursiveTask;

public class FactorialTask extends RecursiveTask<Integer> {
    private final int start;
    private final int end;

    public FactorialTask(int number) {
        this.start = 1;
        this.end = Math.abs(number);
    }

    public FactorialTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    /*
    Самым сложным было понять что вместо обычной рекурсии в расчете факториала можно применять divide & conquer.
    Даже после того как я прочитал статью на хабре где черным по белому было написано что Recursive на названии
    интерфейса не значит что нужно применять хвостовую рекурсию, а в примере приводили деление массива пополам
    */

    @Override
    protected Integer compute() {
        if (end == start) {
            return end;
        } else if (end - start == 1) {
            return end * start;
        }

        int mid = (start + end) / 2;
        var left = new FactorialTask(start, mid);
        var right = new FactorialTask(mid + 1, end);

        left.fork();
        right.fork();

        return right.join() * left.join();
    }
}
