import java.util.concurrent.Semaphore;

class BadSemaphore {

    public static void main(String args[]) throws InterruptedException {
        BadSemaphoreExample.example();
    }
}

class BadSemaphoreExample {

    public static void example() throws InterruptedException {

        final Semaphore semaphore = new Semaphore(1);

        Thread badThread = new Thread(new Runnable() {

            public void run() {

                while (true) {

                    try {
                        semaphore.acquire();
                    } catch (InterruptedException ie) {
                        // handle thread interruption
                    }

                    // Thread was meant to run forever but runs into an
                    // exception that causes the thread to crash.
                    // and semaphore is never released
                    throw new RuntimeException("exception happens at runtime.");
                }
            }
        });

        badThread.start();

        // Wait for the bad thread to start
        Thread.sleep(1000);

        final Thread goodThread = new Thread(()-> {

            System.out.println("Good thread patiently waiting to be signalled.");
            try {
                semaphore.acquire();
                //Below line is never printed
                //Since it is still waiting to acquire the semaphore that was never released
                System.out.println("Got hold of the semaphore");
            } catch (InterruptedException ie) {
                // handle thread interruption
            }
        });

        goodThread.start();
        badThread.join();
        goodThread.join();
        System.out.println("Exiting Program");
    }
}