# Multithreading and Concurrency
#### This repository contains examples to help you get started with multithreading in Java - though the concepts should be applicable across any modern programming language.

I made this repository to act as a personal reference guide to building scalable and high performance multithreaded applications, and I hope this proves useful to others as well.

## Basics (Creating Threads)
- [Using the Thread class and an anonymous instance of Runnable.](./src/com/jyotindersingh/Basics1.java)
- [Setting UncaughtExceptionHandlers on your threads](./src/com/jyotindersingh/Basics2.java)
- [Defining your own class that extends Thread.](./src/com/jyotindersingh/Basics3.java)
- Case Study: [Bank Robbery](./src/com/jyotindersingh/BankRobbery.java)
    - Two threads try to brute force through a password to a vault, while another thread simultaneously tries to catch them.

## Thread Coordination (Termination, Interrupts, Daemon Threads, Joins)
- [Handling interrupts, when your methods already throw/handle external interrupts](./src/com/jyotindersingh/ThreadCoordination.java)
- [Handling external interrupts, by checking for them on a periodic basis - ideal for methods that might not already handle them](./src/com/jyotindersingh/ThreadCoordinationIsInterrupted.java)
- [Daemon threads - To allow your application to exit without being blocked due to some thread running in the background](./src/com/jyotindersingh/ThreadCoordinationDaemon.java)
- [Joins - How to guarantee that a thread upon which we depend, completes its work by the time we expect it.](./src/com/jyotindersingh/ThreadCoordinationJoins.java)
 
 ## Performance Optimization
 ### Performance measures in Multithreading:
 - Latency - The time to completion of a task. Measured in time units.
 - Throughput - The amount of tasks completed in a given period. Measured in *tasks/time unit* 
 
 **Optimizing for Latency**
 - [Image Processing](./src/com/jyotindersingh/ImageProcessing.java) - We run an image recolouring algorithm over an image in both a sequential and a multithreaded scenario and demostrate the performace gains achieved with the help of more threads.