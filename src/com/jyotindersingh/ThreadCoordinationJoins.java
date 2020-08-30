package com.jyotindersingh;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreadCoordinationJoins {
    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNumbers = Arrays.asList(100000000L, 0L, 3435L, 35435L, 2324L, 4656L, 23L, 2435L, 5566L);
        // We want to calculate the factorial of all these numbers in the list
        // This is a CPU intensive task, so we would like to take advantage of multithreading

        List<FactorialThread> threads = new ArrayList<>();

        for (long inputNumber : inputNumbers) {
            threads.add(new FactorialThread(inputNumber));
        }

        // start all the threads
        for (Thread thread : threads) {
            // You could also set the threads to daemon to handle threads which take too long
            // We use a more elegant technique by using interrupts.
            //thread.setDaemon(true);
            thread.start();
        }

        // the threads have now started the calculations, and are calculating the factorial
        // We now want the main method to proceed only when all the threads
        // are done, hence we call the join method on each of the threads.
        // The join method only returns once the thread has finished execution.
        for (Thread thread : threads) {
            // We set a time limit for each computation as 2000 ms (2 seconds)
            // so that we don't keep waiting for ever in case of calculations
            // that are too large.
            thread.join(2000);
        }

        // Since we are out of the above for loop, it means that all the threads
        // have completed the calculations, and hence we can proceed to print the results

        for (int i = 0; i < inputNumbers.size(); ++i) {
            FactorialThread factorialThread = threads.get(i);
            if (factorialThread.isFinished()) {
                System.out.println("Factorial of " + inputNumbers.get(i) + " is " + factorialThread.getResult());
            } else {
                System.out.println("The calculation for " + inputNumbers.get(i) + " is still in progress");
                factorialThread.interrupt();
            }
        }

    }

    public static class FactorialThread extends Thread {
        private long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;

        public FactorialThread(long inputNumber) {
            this.inputNumber = inputNumber;
        }

        @Override
        public void run() {
            this.result = factorial(inputNumber);
            this.isFinished = true;
        }

        public BigInteger factorial(long n) {
            BigInteger tempResult = BigInteger.ONE;

            for (long i = n; i > 0; i--) {
                // Check for interrupts from main, in case the thread is taking too long
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("interrupted due to timeout, exiting...");
                    // return a 0 in case of timeout
                    return BigInteger.ZERO;
                }
                tempResult = tempResult.multiply(new BigInteger(Long.toString(i)));
            }
            return tempResult;
        }

        public boolean isFinished() {
            return isFinished;
        }

        public BigInteger getResult() {
            return result;
        }
    }
}
