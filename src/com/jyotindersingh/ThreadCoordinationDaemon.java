package com.jyotindersingh;

import java.math.BigInteger;

public class ThreadCoordinationDaemon {
    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(new LongComputationTask(new BigInteger("20000"), new BigInteger("1000000000")));

        // In case the thread uses external code that does not handle
        // thread interrupts, we can set the thread to be a daemon thread.
        // Daemon threads don't block our application from exiting, when the main thread ends
        // They can be used in usecases such as an autosave thread.
        //
        // So even if the entire calculation might not have finsihed, just the fact that the
        // main thread ended, makes the entire app terminate.
        thread.setDaemon(true);
        thread.start();
        Thread.sleep(100);
        thread.interrupt();

    }

    private static class LongComputationTask implements Runnable {
        private BigInteger base;
        private BigInteger power;

        public LongComputationTask(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println(base + "^" + power + " = " + pow(base, power));
        }

        private BigInteger pow(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;
            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
                result = result.multiply(base);
            }
            return result;
        }
    }
}
