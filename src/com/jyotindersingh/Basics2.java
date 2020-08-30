package com.jyotindersingh;

public class Basics2 {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Code that will be run in a new thread
                throw new RuntimeException("Intentional Exception");
            }
        });

        thread.setName("Misbehaving Thread");

        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                // Runs when an exception was thrown inside the thread, and
                // did not get caught anywhere
                System.out.println("A critical error happened in thread " + t.getName() + ". The error is " + e.getMessage());
            }
        });

        thread.start();
    }
}
