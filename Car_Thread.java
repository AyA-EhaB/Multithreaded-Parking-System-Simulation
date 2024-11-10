public class Car_Thread implements Runnable {
    //the object should have -> Gate:#N, Car:ID, Arrive:Time, Parking:Duration
    int gateNum;
    int id;
    int arriveTime;
    int parkingDuration;
    //constructor
    public Car_Thread (int gateNum,int id,int arriveTime,int parkingDuration){
        this.gateNum = gateNum;
        this.id=id;
        this.arriveTime=arriveTime;
        this.parkingDuration=parkingDuration;
    }
    // i will simulate car arrivall
    public void run(){
        // wait until the car arrives

        // Car parking for a duration

        try{
            // puase the thread for car arrival time
            // multiply the second*1000 to convert to millisec
            Thread.sleep(arriveTime*1000);
            System.out.println("Car "+id+" from Gate "+gateNum+" arrived at time "+ arriveTime );

            // the car will park for a spcific duration
            System.out.println("Car "+id+" from Gate "+gateNum+" is parking for "+parkingDuration);
            Thread.sleep(parkingDuration*1000);

            // then the car will leave
            System.out.println("Car "+id+" from Gate "+gateNum+" left after "+parkingDuration+" units.");
        }catch (InterruptedException e){
            //If, for some reason, the thread is interrupted
            // while sleeping (which could happen if
            // you manually interrupt it or stop the program early),
            // InterruptedException is thrown.
            System.out.println("stopped");
        }
    }


}
