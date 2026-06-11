package org.example;

import java.util.LinkedList;
import java.util.Queue;

import static java.lang.System.out;

public class MyTreadPull {
    final Queue<Runnable> tasks;
    private final Thread[] threads;
    public int capacity;
    private volatile boolean isShutdown;
    private volatile int activeTaskCount;

    public MyTreadPull(int capacity) {
        this.tasks = new LinkedList<>();
        this.capacity = capacity;
        this.threads = new Thread[capacity];
        this.isShutdown = false;
        this.activeTaskCount = 0;

        for (int i = 0; i < capacity; i++) {
            threads[i] = new MyThread("Поток " + i);
            threads[i].start();
        }
    }

    public synchronized void execute(Runnable task) {
        if (!isShutdown) {
            tasks.offer(task);
            activeTaskCount++;
            notifyAll();
        }
    }

    public void awaitTermination() throws InterruptedException {
        for (Thread thread : threads) {
            thread.join();
        }

        out.println("!!!Все потоки завершены");
    }

    public synchronized void shutdown() {
        isShutdown = true;
        notifyAll();
    }

    class MyThread extends Thread {
        public MyThread(String name) {
            super(name);
            setDaemon(false);
        }

        @Override
        public void run() {
            while (true) {
                Runnable task;

                synchronized (MyTreadPull.this) {
                    while (tasks.isEmpty() && !isShutdown) {
                        try {
                            MyTreadPull.this.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    if (tasks.isEmpty() && isShutdown) return;

                    task = tasks.poll();
                }

                if (task != null) {
                    try {
                        task.run();
                    } finally {
                        synchronized (MyTreadPull.this) {
                            activeTaskCount--;
                        }
                    }
                }
            }
        }
    }
}
