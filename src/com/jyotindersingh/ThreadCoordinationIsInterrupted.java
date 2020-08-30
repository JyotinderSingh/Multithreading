package com.jyotindersingh;

import java.math.BigInteger;

public class ThreadCoordinationIsInterrupted {
    public static void main(String[] args) {

        Thread thread = new Thread(new LongComputationTask(new BigInteger("20000"), new BigInteger("1000000000")));

        thread.start();

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
                if (Thread.currentThread().isInterrupted()) {
                    // Note: We do not use the Try/Catch method of handling interrupts here,
                    // as we are not throwing an InterruptedException anywhere while
                    // computing the result, hence we will never enter the catch block
                    // (sleep method in itself implements that exception hence can
                    // be wrapped with the try catch block to handle the external interrupts)
                    System.out.println("Prematurely interrupted computation");
                    return BigInteger.ZERO;
                }
                result = result.multiply(base);
            }
            return result;
        }
    }
}
