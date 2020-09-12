# Multithreading and Concurrency
#### This repository contains examples to help you get started with multithreading in Java - though the concepts should be applicable across any modern programming language.

*[This repository is still under development]*

I made this repository to act as a personal reference guide to building scalable and high performance multithreaded applications, and I hope this proves useful to others as well.

## Basics (Creating Threads)
- [Using the Thread class and an anonymous instance of Runnable.](./src/com/jyotindersingh/Basics1.java)
- [Setting UncaughtExceptionHandlers on your threads](./src/com/jyotindersingh/Basics2.java)
- [Defining your own class that extends Thread.](./src/com/jyotindersingh/Basics3.java)
- Case Study: [Bank Robbery](./src/com/jyotindersingh/BankRobbery.java)
    - Two threads try to brute force through a password to a vault, while another thread simultaneously tries to catch them.

## Thread Coordination (Termination, Interrupts, Daemon Threads, Joins)
- [Handling interrupts, when your methods already throw/handle external interrupts](./src/com/jyotindersingh/ThreadCoordination.java)
- [Handling external interrupts, by checking for them on a periodic basis](./src/com/jyotindersingh/ThreadCoordinationIsInterrupted.java) - ideal for methods that might not already handle them.
- [Daemon threads](./src/com/jyotindersingh/ThreadCoordinationDaemon.java) - To allow your application to exit without being blocked due to some thread running in the background.
- [Joins](./src/com/jyotindersingh/ThreadCoordinationJoins.java) - How to guarantee that a thread upon which we depend, completes its work by the time we expect it.
 
 ## Performance Optimization
 ### Performance measures in Multithreading:
 - Latency - The time to completion of a task. Measured in time units.
 - Throughput - The amount of tasks completed in a given period. Measured in *tasks/time unit* 
 
 **Optimizing for Latency**
 - [Image Processing](./src/com/jyotindersingh/ImageProcessing.java) - We run an image recolouring algorithm over an image in both a sequential and a multithreaded scenario and demostrate the performace gains achieved with the help of more threads.
 
 **Optimizing for Throughput**
 
 Thread Pooling: Creating the threads once and reusing them for future tasks, instead of recreating the threads each and every time from scratch.
 - [HTTP Server](./src/com/jyotindersingh/ThroughputHttpServer.java) - We run a CPU heavy task on a thread pool inside a server and look at the throughput gains as we increase the number of worker threads in the thread pool.
 
 
 ## Data Sharing between Threads (Resource Sharing and Critical Sections)
 **Monitors:** You can use the *synchronized* API. It provides a locking mechanism designed to prevent access to a block of code or an entire method by multiple threads.
 There are 2 ways to use the synchronized keyword:
 1. [You can declare one or more methods in a class using the *synchronized* keyword](./src/com/jyotindersingh/RaceCondition.java). When one or more threads try and call these methods on the same object of this class, **only one thread will be able to execute either of these methods**.
 1. [Define the block of code that we consider as Critical Section and use the *synchronized* keyword](./src/com/jyotindersingh/RaceCondition2.java) to restrict access only to that section without making the entire methods synchronized. This provides us with a lot more flexibility ,to have separate critical sections synchronize on different objects.
 
 Note: The *synchronized* block is **reentrant** - which means for instance if a Thread A is accessing a synchronized method, while already being in a different synchronized method or block, it will be able to access that synchronized method with no problem. Basically, a thread cannot prevent itself from entering a critical section.
 
 **Atomic Operations:** Sadly most operations we perform are often not atomic.
 - All reference assignments are atomic (so we can change the object reference in a single operation safely - hence, all our *getters* and *setters* are **atomic**).
 - All assignments to primitive types are atomic and safe, *except long and double*.
 - That means we can read from, and write to the following types atomically:
    - int
    - short
    - byte
    - float
    - char
    - boolean
 - **long** and **double** are exceptions, as they are 64 bit long - for them Java doesn't provide any guarantees (even on 64 bit machines). It's entirely possible that a write to a long or a double takes two internal operations by the CPU - one write to the *lower 32 bits*, and one to the *upper 32 bits*.
 - Fortunately, we are provided with the **volatile** keyword, which we can use when declaring long or double variables - and reads/writes to those variables are atomic, thread-safe - in other words, they are guaranteed to be performed with a single hardware operation. ```volatile double x = 1.0;```
 
[**Metrics Aggregation:**](./src/com/jyotindersingh/MetricAggregation.java) Frequently, when running production applications we need to make sure how long certain important operations or pieces of code take. The length of those operations can depend on client's input data, environment, etc. It is important for us to identify the duration of those operations and performance issues and optimize the customer's experience.