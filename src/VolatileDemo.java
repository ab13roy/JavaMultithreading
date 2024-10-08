class VolatileDemo {

    // volatile doesn't imply thread-safety!
    static volatile int count = 0;

    public static void main(String[] args) throws InterruptedException {

        int numThreads = 10;
        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(() -> {
                for(int k=0;k<1000;k++) {
                    count++;
                }
            });
        }

        for (int i = 0; i < numThreads; i++) {
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            threads[i].join();
        }

        System.out.println("count = " + count);
    }
}