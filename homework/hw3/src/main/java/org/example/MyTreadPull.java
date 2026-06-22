package org.example;

import java.util.LinkedList;
import java.util.Queue;

import static java.lang.System.out;

public class MyTreadPull {
    private final Queue<Runnable> tasks;
    private final Thread[] threads;
    public int capacity;
    private volatile boolean isShutdown;
    private final Object lockedObject = new Object();

    public MyTreadPull(int capacity) {
        this.tasks = new LinkedList<>();
        this.capacity = capacity;
        this.threads = new Thread[capacity];
        this.isShutdown = false;

        for (int i = 0; i < capacity; i++) {
            threads[i] = new MyThread("Поток " + i);
            threads[i].start();
        }
    }

    public void execute(Runnable task) {
        synchronized (lockedObject) {
            if (!isShutdown) {
                tasks.offer(task);
                lockedObject.notify();
            }
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

    public int getPoolSize() {
        return tasks.size();
    }

    class MyThread extends Thread {
        public MyThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            while (true) {
                Runnable task;

                synchronized (MyTreadPull.this) {
                    while (tasks.isEmpty() && !isShutdown) {
                        try {
                            MyTreadPull.this.wait();
                        } catch (InterruptedException ignored) {
                        }
                    }

                    if (tasks.isEmpty() && isShutdown) return;

                    task = tasks.poll();
                }

                if (task != null) {
                    task.run();
                }
            }
        }
    }
}
