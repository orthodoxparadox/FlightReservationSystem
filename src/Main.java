import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int n = 10;
        int p = 2;
        int q = 11;

        Database.initDatabase(n, p);
        ExecutorService exec = Executors.newFixedThreadPool(5);
        CCM ccm = new CCM();

        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < q; i++) {
            int t_type = r.nextInt(5);
            t_type = 0;
            int fl, fl2, pid;
//            ccm.acquireLock(Database.allFlights);
//            ccm.acquireLock(Database.allPassengers);
            switch (t_type) {
                case 0:
                    fl = r.nextInt(n);
                    pid = r.nextInt(p);
                    Reserve reserve = new Reserve(Database.allFlights.get(fl), pid);
                    System.err.println(fl + " " + pid);
                    exec.execute(reserve);
                    break;
                case 1:
                    fl = r.nextInt(n);
                    pid = r.nextInt(p);
                    Cancel cancel = new Cancel(Database.allFlights.get(fl), pid);
                    exec.execute(cancel);
                    break;
                case 2:
                    pid = r.nextInt(p);
                    My_Flights my_flights = new My_Flights(pid);
                    exec.execute(my_flights);
                    break;
                case 3:
                    Total_Reservations total_reservations = new Total_Reservations();
                    exec.execute(total_reservations);
                    break;
                case 4:
                    fl = r.nextInt(n);
                    fl2 = r.nextInt(n);
                    pid = r.nextInt(p);
                    Transfer transfer = new Transfer(Database.allFlights.get(fl), Database.allFlights.get(fl2), pid);
                    exec.execute(transfer);
                    break;
            }
//            ccm.releaseLock(Database.allFlights);
//            ccm.releaseLock(Database.allPassengers);
        }
        if(!exec.isTerminated())
        {
            exec.shutdown();
            exec.awaitTermination(5L, TimeUnit.SECONDS);
        }
        System.err.println("Transactions done");
        for(int i = 0; i < n; i++)
        {
            Flight f = Database.allFlights.get(i);
            ArrayList<Seat> s = f.getSeats();
            for(int j = 0; j < s.size(); j++)
            {
                if(s.get(j).isOccupied())
                {
                    System.err.println(i + " " + s.get(j).getP().id);
                }
            }
        }


    }
}

