package com.jyotindersingh;

public class ThreadCoordination {
    public static void main(String[] args) {

        Thread thread = new Thread(new BlockingTask());

        thread.start();

        thread.interrupt();

    }

    private static class BlockingTask implements Runnable {
        @Override
        public void run() {
            // Performs some task that involves blocking the thread for a long time
            try {
                Thread.sleep(500000);
            } catch (InterruptedException e) {
                System.out.println("Exiting blocking thread");
            }
        }
    }
}
