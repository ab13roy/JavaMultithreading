/*
A barbershop consists of a waiting room with n chairs, and a barber chair for giving haircuts.
If there are no customers to be served, the barber goes to sleep.
If a customer enters the barbershop and all chairs are occupied, then the customer leaves the shop.
If the barber is busy, but chairs are available, then the customer sits in one of the free chairs.
If the barber is asleep, the customer wakes up the barber.
Write a program to coordinate the interaction between the barber and the customers.
 */

import java.util.HashSet;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

class Solution {
    public static void main( String args[] ) throws InterruptedException {
        BarberProblem.runTest();
    }
}

class BarberProblem {

    final int CHAIRS = 3;
    Semaphore waitForCustomerToEnter = new Semaphore(0);
    Semaphore waitForBarberToGetReady = new Semaphore(0);
    Semaphore waitForCustomerToLeave = new Semaphore(0);
    Semaphore waitForBarberToCutHair = new Semaphore(0);
    int waitingCustomers = 0;
    ReentrantLock lock = new ReentrantLock();
    int hairCutsGiven = 0;

    void customerWalksIn() throws InterruptedException {

        lock.lock();
        if (waitingCustomers == CHAIRS) {
            System.out.println("Customer walks out, all chairs occupied");
            lock.unlock();
            return;
        }
        waitingCustomers++;
        lock.unlock();

        waitForCustomerToEnter.release();
        waitForBarberToGetReady.acquire();

        lock.lock();
        waitingCustomers--;
        lock.unlock();


        waitForBarberToCutHair.acquire();
        waitForCustomerToLeave.release();
    }

    void barber() throws InterruptedException {

        while (true) {
            waitForCustomerToEnter.acquire();
            waitForBarberToGetReady.release();
            hairCutsGiven++;
            System.out.println("Barber cutting hair..." + hairCutsGiven);
            Thread.sleep(50);
            waitForBarberToCutHair.release();
            waitForCustomerToLeave.acquire();
        }
    }

    public static void runTest() throws InterruptedException {

        HashSet<Thread> set = new HashSet<Thread>();
        final BarberProblem barberShopProblem = new BarberProblem();

        Thread barberThread = new Thread(() -> {
            try {
                barberShopProblem.barber();
            } catch (InterruptedException ignored) {

            }
        });
        barberThread.start();

        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                try {
                    barberShopProblem.customerWalksIn();
                } catch (InterruptedException ignored) {

                }
            });
            set.add(t);
        }

        for (Thread t : set) {
            t.start();
        }

        for (Thread t : set) {
            t.join();
        }

        set.clear();
        Thread.sleep(800);

        barberThread.join();
    }
}
