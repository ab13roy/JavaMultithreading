# JavaMultithreading
> Some practice with Multithreading in Java <br/>
> Few questions asked in the interview are below. <br/>
> Additionally some of the files include understanding of code multithreading concepts. <br/>
> These include correct and incorrect usage of thread safe variables <br/>
> eg Mutex, Semaphore, Barriers etc.

# Below are the some of the problems solved using Multithreading

## <ins>Question 1
>__Imagine we have an Executor class that performs some useful task asynchronously via the method asynchronousExecution().
In addition the method accepts a callback object which implements the Callback interface.
The object’s done() gets invoked when the asynchronous execution is done.
Your task is to make the execution synchronous without changing the original classes,
(imagine, you are given the binaries and not the source code) so that main thread waits till asynchronous execution is complete.__

```
SAMPLE CODE:

class Demonstration {
    public static void main( String args[] ) throws Exception{
        Executor executor = new Executor();
        executor.asynchronousExecution(() -> {
            System.out.println("I am done");
        });

        System.out.println("main thread exiting...");
    }
}

interface Callback {

    public void done();
}


class Executor {

    public void asynchronousExecution(Callback callback) throws Exception {

        Thread t = new Thread(() -> {
            // Do some useful work
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ie) {
            }
            callback.done();
        });
        t.start();
    }
}
```

### _[Solution](https://github.com/ab13roy/JavaMultithreading/blob/master/src/AsyncToSync.java)_ 


## Question 2
>__A blocking queue is defined as a queue which blocks the caller of the enqueue method if there’s no more capacity to add the new item being enqueued. Similarly, the queue blocks the dequeue caller if there are no items in the queue. Also, the queue notifies a blocked enqueuing thread when space becomes available and a blocked dequeuing thread when an item becomes available in the queue.__ </br>
![Question2.png](assets%2FQuestion2.png)
### _[Solution1](https://github.com/ab13roy/JavaMultithreading/blob/master/src/BlockingQueueExample.java)_ _[Solution2](https://github.com/ab13roy/JavaMultithreading/blob/master/src/BlockingQueueExample2.java)_
> *Follow up* </br>
> *In both the enqueue() and dequeue() methods we use the notifyAll() method instead of the notify() method. The reason behind the choice is very crucial to understand. Consider a situation with two producer threads and one consumer thread all working with a queue of size one. It’s possible that when an item is added to the queue by one of the producer threads, the other two threads are blocked waiting on the condition variable. If the producer thread after adding an item invokes notify() it is possible that the other producer thread is chosen by the system to resume execution. The woken-up producer thread would find the queue full and go back to waiting on the condition variable, causing a deadlock. Invoking notifyAll() assures that the consumer thread also gets a chance to wake up and resume execution.*

## Question 3
>__This is an actual interview question asked at Uber and Oracle. Imagine you have a bucket that gets filled with tokens at the rate of 1 token per second. The bucket can hold a maximum of N tokens. Implement a thread-safe class that lets threads get a token when one is available. If no token is available, then the token-requesting threads should block. The class should expose an API called getToken that various threads can call to get a token. </br>__
![Question3.png](assets%2FQuestion3.png)
### _[Solution](https://github.com/ab13roy/JavaMultithreading/blob/master/src/RateLimiterExample1.java)_

## Question 4
>__Design and implement a thread-safe class that allows registeration of callback methods that are executed after a user specified time interval in seconds has elapsed.__
### _[Solution](https://github.com/ab13roy/JavaMultithreading/blob/master/src/DeferredCallback.java)_

### Question 5
>__Imagine you have an application where you have multiple readers and multiple writers. You are asked to design a lock which lets multiple readers read at the same time, but only one writer write at a time.__
### _[Solution](https://github.com/ab13roy/JavaMultithreading/blob/master/src/ReadWriteLock.java)_

### Question 6
>__Imagine at the end of a political conference, republicans and democrats are trying to leave the venue and ordering Uber rides at the same time. However, to make sure no fight breaks out in an Uber ride, the software developers at Uber come up with an algorithm whereby either an Uber ride can have all democrats or republicans or two Democrats and two Republicans. All other combinations can result in a fist-fight. </br>
> Your task as the Uber developer is to model the ride requestors as threads. Once an acceptable combination of riders is possible, threads are allowed to proceed to ride. Each thread invokes the method seated() when selected by the system for the next ride. When all the threads are seated, any one of the four threads can invoke the method drive() to inform the driver to start the ride. </br>__
![Question6.png](assets%2FQuestion6.png)
### _[Solution](https://github.com/ab13roy/JavaMultithreading/blob/master/src/CyclicBarrierExample.java)_

### Question 7
>__This is a classical synchronization problem proposed by Dijkstra. </br>
Imagine you have five philosopher’s sitting on a roundtable. The philosopher’s do only two kinds of activities. One they contemplate, and two they eat. However, they have only five forks between themselves to eat their food with. Each philosopher requires both the fork to his left and the fork to his right to eat his food. The arrangement of the philosophers and the forks are shown in the diagram. Design a solution where each philosopher gets a chance to eat his food without causing a deadlock. </br>__
![Question7.png](assets%2FQuestion7.png)
### _[Solution](https://github.com/ab13roy/JavaMultithreading/blob/master/src/DiningPhilosopher.java)_

### Question 8
>__A similar problem appears in Silberschatz and Galvin’s OS book, and variations of this problem exist in the wild. A barbershop consists of a waiting room with n chairs, and a barber chair for giving haircuts. If there are no customers to be served, the barber goes to sleep. If a customer enters the barbershop and all chairs are occupied, then the customer leaves the shop. If the barber is busy, but chairs are available, then the customer sits in one of the free chairs. If the barber is asleep, the customer wakes up the barber. Write a program to coordinate the interaction between the barber and the customers. </br>__
![Question8.png](assets%2FQuestion8.png)
### _[Solution](https://github.com/ab13roy/JavaMultithreading/blob/master/src/BarberProblem.java)_


### Question 9
>__Merge sort is a typical text-book example of a recursive algorithm and the poster-child of the divide and conquer strategy. The idea is very simple, we divide the array into two equal parts, sort them recursively and then combine the two sorted arrays. The base case for recursion occurs when the size of the array reaches a single element. An array consisting of a single element is already sorted. </br>__
![Question9.png](assets%2FQuestion9.png)
### _[Solution](https://github.com/ab13roy/JavaMultithreading/blob/master/src/MultithreadedMergeSort.java)_




