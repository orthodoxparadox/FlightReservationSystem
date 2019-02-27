import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Database {
    static ArrayList<Flight> allFlights = new ArrayList<Flight>();
    static ArrayList<Passenger> allPassengers = new ArrayList<Passenger>();
    public static void initDatabase(int n, int p) {
        Random r = new Random(System.currentTimeMillis());
        for(int i = 0; i < p; i++) {
            allPassengers.add(new Passenger(i));
        }
        for (int i = 0; i < n; i++) {
            allFlights.add(new Flight(r.nextInt(Constants.max_capacity - Constants.min_capacity) + Constants.min_capacity, i));
        }
    }
    public static Passenger getPassenger(int pid) {
        try {
            Thread.sleep(Constants.sleep_time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return allPassengers.get(pid);
    }
    public static ArrayList<Flight> getAllFlights() {
        try {
            Thread.sleep(Constants.sleep_time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return allFlights;
    }
    public static ArrayList<Passenger> getAllPassengers() {
        try {
            Thread.sleep(Constants.sleep_time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return allPassengers;
    }
    public static void setAllFlights(ArrayList<Flight> f) {
        try {
            Thread.sleep(Constants.sleep_time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        allFlights = f;
    }
    public static void setAllPassengers(ArrayList<Passenger> p){
        try {
            Thread.sleep(Constants.sleep_time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        allPassengers = p;
    }
}