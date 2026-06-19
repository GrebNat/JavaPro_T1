package org.example;

import static java.lang.System.out;
import static java.lang.Thread.sleep;

// Попробуйте реализовать собственный пул потоков.
//
// В качестве аргументов конструктора пулу передается его емкость (количество рабочих потоков).
//
// Как только пул создан, он сразу инициализирует и запускает потоки.
//
// Внутри пула очередь задач на исполнение организуется через LinkedList.
//
// При выполнении у пула потоков метода execute(Runnabler),
// указанная задача должна попасть в очередь исполнения, и как только появится свободный поток – должна быть выполнена.
//
// Также необходимо реализовать метод shutdown(),
// после выполнения которого новые задачи больше не принимаются пулом
// (при попытке добавить задачу можно бросать IllegalStateException),
// и все потоки для которых больше нет задач завершают свою работу.
//
// Дополнительно можно добавить метод awaitTermination() без таймаута, работающий аналогично стандартным пулам потоков
public class Main {
    public static void main(String[] args) throws InterruptedException {
        MyTreadPull pool = new MyTreadPull(4);

        for (int i = 1; i <= 10; i++) {
            final int taskId = i;
            pool.execute(() -> {
                String threadName = Thread.currentThread().getName();

                out.println("Таска " + taskId + " выполняется в потоке " + threadName);

                delay(1);

                out.println("Таска " + taskId + " завершена\n Поток " + threadName + " свободен");
            });
        }

        out.println("Ожидающие таски: " + pool.tasks.size());

        pool.shutdown();
        delay(3);
        pool.execute(() -> out.println("!!!Эта таска не будет добавлена"));

        pool.awaitTermination();
    }

    private static void delay(int seconds) {
        try {
            sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}