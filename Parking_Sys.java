import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parking_Sys {
    //method that return a list of cars readed and parsed from a file
    public static List<Car_Thread> read_cars_info(String filename){
        List<Car_Thread> Cars = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
            String line;
            while((line= reader.readLine()) !=null){
                //Gate 1, Car 0, Arrive 0, Parks 3
//                line = reader.readLine();
                String[] Car_info = line.split(", ");
                int gateN = Integer.parseInt((Car_info[0].replace("Gate ","")).trim());
                int Carid = Integer.parseInt((Car_info[1].replace("Car ", "")).trim());
                int ArriveTime = Integer.parseInt((Car_info[2].replace("Arrive ","")).trim());
                int ParkDurat = Integer.parseInt((Car_info[3].replace("Parks ","")).trim());

                Car_Thread car = new Car_Thread(gateN,Carid,ArriveTime,ParkDurat);
                Cars.add(car);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return Cars;
    }
    public static void main(String[] args ){
        List<Car_Thread> cars = read_cars_info("D:\\OS\\OS Ass#2\\MultiThreaded_parking_Sys\\src\\cars_data.txt");

        //starting a Thread for each Car
        for (Car_Thread car:cars){
            Thread Car = new Thread(car);
            Car.start();
        }
    }
}
