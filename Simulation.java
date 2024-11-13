import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

class Custom_Semaphore {
    private int permits; //number of our recourse
    private final Object lock = new Object(); // to make only one thread modify permits at a time

    public Custom_Semaphore(int permits) {
        if (permits < 0) throw new IllegalArgumentException("Permits cannot be negative");
        this.permits = permits;
    }

    public void acquire() throws InterruptedException {
        //one thread access at a time
        synchronized (lock){
            while (permits == 0) {
                lock.wait();
            }
            permits--;
        }
    }

    public void release() {
        synchronized (lock) {
            permits++;
            lock.notify();
        }
    }
}

class Simulation {
    private static final int Parking_Spots = 4;
    private static final int NUM_GATES = 3;

    private static int totalCarsServed = 0;
    private static int currentCarsParked = 0;
    private static final Object lock = new Object();  // to arrange thread access to totalCarsServed and currentCarsParked
    private static Custom_Semaphore parkingSpots;
    private static int[] gateCarCounts = new int[NUM_GATES];

    public static void main(String[] args) {
        parkingSpots = new Custom_Semaphore(Parking_Spots);
        Thread[] carThreads = new Thread[0]; // To store all car threads
        GateThread[] gates = new GateThread[NUM_GATES];
        try {
            carThreads = readAndProcessInput("input.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Wait for all car threads to finish before starting gate threads
        for (Thread t : carThreads) {
            try {
                t.join();  // Ensures that the main thread waits for all CarThreads to finish
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //////////////////////////////////////////////
        for (int i = 0; i < NUM_GATES; i++) {
            gates[i] = new GateThread(i + 1); // Gate numbers are 1, 2, 3
        }

        // Add cars to the respective gate queues
        for (Thread carThread : carThreads) {
            CarThread car = (CarThread) carThread;
            gates[car.gate - 1].addCar(car); // Add car to the corresponding gate
        }

        // Start all gate threads
        for (GateThread gate : gates) {
            gate.start();
        }

        // Wait for all gate threads to finish
        for (GateThread gate : gates) {
            try {
                gate.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //////////////////////////////////////////////
        // Print the summary after all threads finish processing
        printSummary();
    }
    private static Thread[] readAndProcessInput(String filename) throws IOException {
        List<Thread> carThreadsList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] Car_info = line.split(", ");
                int gateN = Integer.parseInt((Car_info[0].replace("Gate ","")).trim());
                int Carid = Integer.parseInt((Car_info[1].replace("Car ", "")).trim());
                int ArriveTime = Integer.parseInt((Car_info[2].replace("Arrive ","")).trim());
                int ParkDurat = Integer.parseInt((Car_info[3].replace("Parks ","")).trim());

                // Create and add a CarThread for each car
                carThreadsList.add(new CarThread(gateN, Carid, ArriveTime, ParkDurat));
            }
        }

        // Convert the list to an array for easier thread management
        Thread[] carThreads = new Thread[carThreadsList.size()];
        carThreads = carThreadsList.toArray(carThreads);

        // Start all car threads
        for (Thread t : carThreads) {
            t.start();
        }

        return carThreads;
    }
    private static class GateThread extends Thread {
        private int gateNumber;
        private Queue<CarThread> carQueue = new ConcurrentLinkedQueue<>();

        public GateThread(int gateNumber) {
            this.gateNumber = gateNumber;
        }

        // Method to add a car to the queue
        public void addCar(CarThread car) {
            carQueue.add(car);
        }

        @Override
        public void run() {
            while (!carQueue.isEmpty()) {
                CarThread car = carQueue.poll();
                if (car != null) {
                    try {
                        car.join();  // Wait for each car to finish before processing the next one
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    private static class CarThread extends Thread {
        private int gate;
        private int car;
        private int arrivalTime;
        private int parkingDuration;

        public CarThread(int gate, int car, int arrivalTime, int parkingDuration) {
            this.gate = gate;
            this.car = car;
            this.arrivalTime = arrivalTime;
            this.parkingDuration = parkingDuration;
        }

        @Override
        public void run() {
            try {
                // Simulate arrival time
                Thread.sleep(arrivalTime * 1000);  // Arrival time in seconds
                System.out.printf("Car %d from Gate %d arrived at time %d\n", car, gate, arrivalTime);

                // Check if a parking spot is available
                long waitStart = System.currentTimeMillis();
                synchronized (Custom_Semaphore.class) {
                    if (currentCarsParked >= Parking_Spots) {
                        System.out.printf("Car %d from Gate %d waiting for a spot.\n", car, gate);
                    }
                }

                // Attempt to acquire a parking spot
                parkingSpots.acquire();
                long waitEnd = System.currentTimeMillis();
                long waitingTime = (waitEnd - waitStart) / 1000;

                // Car is now parked
                synchronized (Custom_Semaphore.class) {
                    currentCarsParked++;
                }
                System.out.printf("Car %d from Gate %d parked after waiting for %d seconds. (Parking Status: %d spots occupied)\n",
                        car, gate, waitingTime, currentCarsParked);
                synchronized (lock) {
                    totalCarsServed++;
                    gateCarCounts[gate - 1]++;
                }

                // Simulate parking duration
                Thread.sleep(parkingDuration * 1000);  // Parking duration in seconds

                // Car leaves, release parking spot
                parkingSpots.release();
                synchronized (Custom_Semaphore.class) {
                    currentCarsParked--;
                }
                System.out.printf("Car %d from Gate %d left after %d seconds. (Parking Status: %d spots occupied)\n",
                        car, gate, parkingDuration, currentCarsParked);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private static void printSummary() {
        synchronized (lock) {
            // Print the summary after all threads finish executing
            System.out.println("\nTotal Cars Served: " + totalCarsServed);
            System.out.println("Current Cars in Parking: " + currentCarsParked);
            System.out.println("Details:");
            for (int i = 0; i < NUM_GATES; i++) {
                System.out.printf("- Gate %d served %d cars.\n", (i + 1), gateCarCounts[i]);
            }
        }
    }
}
