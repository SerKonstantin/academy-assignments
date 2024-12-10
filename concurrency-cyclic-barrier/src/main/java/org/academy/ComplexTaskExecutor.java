package org.academy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;

public class ComplexTaskExecutor {
    private final int numberOfThreads;

    public ComplexTaskExecutor(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    public void executeTasks(int numberOfTasks) {
        if (numberOfTasks != numberOfThreads) {
            throw new RuntimeException();
        }

        var executorService = Executors.newFixedThreadPool(numberOfThreads);
        var resultsList = Collections.synchronizedList(new ArrayList<Integer>());
        var complexTask = new ComplexTask();
        var barrier = new CyclicBarrier(numberOfTasks);

        for (int i = 0; i < numberOfTasks; i++) {
            executorService.submit(() -> {
                // Ждем пока все потоки не будут готовы, и потом запускаем их одновременно
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                Thread.sleep(2000); // Наглядно видно что результаты появляются одновременно, а не по очереди
                var result = complexTask.execute();
                resultsList.add(result);

                return result;
            });
        }

        // Без ожидания лист с результатами будет пуст, тк потоки еще не закончили работу
        // Пауза такая большая чтобы визуально отделить от работы executorService
        try {
            Thread.sleep(4000);
            int total = resultsList.stream().mapToInt(Integer::intValue).sum();
            System.out.println("Combined result of multiple calls of execute() is: " + total);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        executorService.shutdown();
    }

}
