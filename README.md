# Multithreading and Concurrency
### This repository contains examples to help you get started with multithreading in Java - though the concepts should be applicable across any modern programming language.

## Basics (Creating Threads)
- [Using the Thread class and an anonymous instance of Runnable.](./src/com/jyotindersingh/Main.java)
- [Setting UncaughtExceptionHandlers on your threads](./src/com/jyotindersingh/Main_2.java)
- [Defining your own class that extends Thread.](./src/com/jyotindersingh/Main_3.java)
- Case Study: [Bank Robbery](./src/com/jyotindersingh/BankRobbery.java)
    - Two threads try to brute force through a password to a vault, while another thread simultaneously tries to catch them.

## Thread Coordination (Termination, Interrupts, Daemon Threads, Joins)
- [Handling interrupts, when your methods already throw/handle external interrupts](./src/com/jyotindersingh/ThreadCoordination.java)
- [Handling external interrupts, by checking for them on a periodic basis - ideal for methods that might not already handle them](./src/com/jyotindersingh/ThreadCoordination_2.java)
- [Daemon threads - To allow your application to exit without being blocked due to some thread running in the background](./src/com/jyotindersingh/ThreadCoordination_3_Daemon.java)

 