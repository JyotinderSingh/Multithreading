package com.jyotindersingh;

public class RaceCondition2 {
    @SuppressWarnings("DuplicatedCode")
    public static void main(String[] args) throws InterruptedException {
        InventoryCounter inventoryCounter = new InventoryCounter();
        IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter);
        DecrementingThread decrementingThread = new DecrementingThread(inventoryCounter);

        incrementingThread.start();
        decrementingThread.start();

        incrementingThread.join();
        decrementingThread.join();

        System.out.println("We currently have " + inventoryCounter.getItems() + " items.");
    }

    private static class DecrementingThread extends Thread {
        private InventoryCounter inventoryCounter;

        public DecrementingThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; ++i) {
                inventoryCounter.decrement();
            }
        }
    }

    private static class IncrementingThread extends Thread {
        private InventoryCounter inventoryCounter;

        public IncrementingThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; ++i) {
                inventoryCounter.increment();
            }
        }
    }

    private static class InventoryCounter {
        private int items = 0;
        Object lock = new Object();

        public void increment() {
            synchronized (lock) {
                items++;
            }
        }

        public void decrement() {
            synchronized (lock) {
                items--;
            }
        }

        public int getItems() {
            synchronized (lock) {
                return items;
            }
        }
    }
}
