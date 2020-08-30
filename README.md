# Multithreading and Concurrency
### This repository contains examples to help you get started with multithreading in Java - though the concepts should be applicable across any modern programming language.

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
 