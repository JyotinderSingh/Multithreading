package com.jyotindersingh;

public class Basics1 {

    public static void main(String[] args) throws InterruptedException {
        // We create an object of the Thread class
        // By default the Thread object is empty, so we pass
        // a new object of a class that implements a Runnable interface, into its constructor
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Code that will be run in a new thread
                System.out.println("We are now in thread " + Thread.currentThread().getName());
                System.out.println("Current thread priority is " + Thread.currentThread().getPriority());
            }
        });

        thread.setName("New Worker Thread");

        thread.setPriority(Thread.MAX_PRIORITY);

        // Thread.currentThread() gives the Thread object of the current thread
        System.out.println("We are in thread: " + Thread.currentThread().getName() + " before starting a new thread");
        // Start the new thread
        thread.start();
        // Note: The new thread might not get scheduled immediately
        System.out.println("We are in thread: " + Thread.currentThread().getName() + " after starting a new thread");

        // the sleep method puts the current thread to sleep
        // it instructs the operating system to not schedule the current thread
        // for the given number of milliseconds
        Thread.sleep(10000);
    }
}
