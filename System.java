import java.util.concurrent.Semaphore;

class System {
    private static int num_parking_spots = 4;

    private static int num_car_parked = 0;
    private static Semaphore parking_semiphore;

    public static void main(String[] args) {
        parking_semiphore = new Semaphore(num_parking_spots);
        simulateCars();
    }

    private static void simulateCars() {
        // this is dummy function to simulate car arrivals and departures to test the functionality only
        // will be removed
        simulateCar(0, 3);
        simulateCar(1, 4);
        simulateCar(2, 2);
        simulateCar(3, 5);
        simulateCar(4, 3);
        simulateCar(5, 4);
        simulateCar(6, 3);
        simulateCar(7, 2);
        simulateCar(8, 5);
        simulateCar(9, 3);
        simulateCar(10, 4);
        simulateCar(11, 3);
        simulateCar(12, 2);
        simulateCar(13, 5);
        simulateCar(14, 3);
    }

    private static void simulateCar(int car, int parkingDuration) {
        try {
            parking_semiphore.acquire();
            System.out.printf("Car %d parked. (Parking Status: %d spots occupied)\n", car, ++num_car_parked);

            Thread.sleep(parkingDuration * 25);

            parking_semiphore.release();
            System.out.printf("Car %d left after %d units of time. (Parking Status: %d spots occupied)\n",
                    car, parkingDuration, --num_car_parked);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
