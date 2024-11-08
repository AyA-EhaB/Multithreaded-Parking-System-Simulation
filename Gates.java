import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

class Car implements Runnable {
    public Parking parking;
    private int carNum;
    private int parkDuration;
    private int gateNum;
    private int arrivalTime;
    public Car(int gateNum,int carNum, int arrivalTime, int parkDuration) {
        this.gateNum = gateNum;
        this.carNum = carNum;
        this.arrivalTime = arrivalTime;
        this.parkDuration = parkDuration;
    }

    @Override
    public void run() {
        //parkCar
        parking.park(parkDuration);
    }
}

class Parking{
    private static int nCarsTotal=0;
    private int nSpots;
//    private List<Semaphore> spots;
    private Semaphore spots;

    public Parking(int nSpots) {
        this.nSpots = nSpots;
        spots = new Semaphore(nSpots);
//        spots = new ArrayList<>(nSpots);
//        for (int i = 0; i < nSpots; i++) {
//            spots.add(new Semaphore(1)); // Each gate semaphore initialized with 1 permit
//        }
    }
//    public Boolean foundParkingSpot() {
//        if(spots.availablePermits()>0){
//
//        }
//    }
    public void park(int duration) {
        try{
            spots.acquire();
            nCarsTotal ++;
            Thread.sleep(duration*10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            spots.release();
        }
    }
}

public class Gates extends Thread{
    int nGates = 3;
//    List<Semaphore> gateSemaphores;
//    private static Semaphore parkingSemaphore = new Semaphore(ParkingSystem.num_parking_spots);
    Parking parking =  new Parking(4);
    public Gates() {
//        gateSemaphores = new ArrayList<>(nGates);
//        for (int i = 0; i < nGates; i++) {
//            gateSemaphores.add(new Semaphore(1)); // Each gate semaphore initialized with 1 permit
//        }
    }
    public void enterGate(int gateNum){

//        try{
////            gateSemaphores.get(gateNum-1).acquire();
////            if(parking)
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }
}
