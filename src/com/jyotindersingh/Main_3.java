package com.jyotindersingh;

public class Main_3 {
    public static void main(String[] args) {
        Thread thread = new NewThread();

        thread.start();
    }

    private static class NewThread extends Thread {
        @Override
        public void run() {
            // Code that executes on the new thread
            System.out.println("Hello from " + this.getName());
        }
    }

}
