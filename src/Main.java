import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static int run_type;
    public static void main(String[] args) throws InterruptedException {
        int n = Constants.num_flights;
        int p = Constants.num_passengers;
        int q = Constants.num_queries;
        Scanner sc = new Scanner(System.in);
        System.out.println("ENTER RUN MODE (1 FOR SERIAL, 2 FOR TWO PHASE LOCKING");
//        run_type = sc.nextInt();
        run_type = 2;
        Database.initDatabase(n, p);
        ExecutorService exec = Executors.newFixedThreadPool(5);
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < q; i++) {
            int t_type = r.nextInt(5);
//            t_type = sc.nextInt();
//            t_type = 0;
            int fl, fl2, pid;
            switch (t_type) {
                case 0:
                    System.err.println("Reserve");
                    fl = r.nextInt(n);
                    pid = r.nextInt(p);
//                    fl = sc.nextInt();
//                    pid = sc.nextInt();
                    System.err.println(fl + " " + pid);
                    Reserve reserve = new Reserve(Database.getAllFlights().get(fl), pid);
//                    System.err.println(fl + " " + pid);
                    exec.execute(reserve);
                    break;
                case 1:
                    System.err.println("Cancel");
                    fl = r.nextInt(n);
                    pid = r.nextInt(p);
//                    fl = sc.nextInt();
//                    pid = sc.nextInt();
                    System.err.println(fl + " " + pid);
                    Cancel cancel = new Cancel(Database.getAllFlights().get(fl), pid);
                    exec.execute(cancel);
                    break;
                case 2:
                    System.err.println("My Flights");
                    pid = r.nextInt(p);
//                    pid = sc.nextInt();
                    System.err.println(pid);
                    My_Flights my_flights = new My_Flights(pid);
                    exec.execute(my_flights);
                    break;
                case 3:
                    System.err.println("Total Reservations");
                    Total_Reservations total_reservations = new Total_Reservations();
                    exec.execute(total_reservations);
                    break;
                case 4:
                    System.err.println("Transfer");
                    fl = r.nextInt(n);
                    fl2 = r.nextInt(n);
                    pid = r.nextInt(p);
//                    fl = sc.nextInt();
//                    fl2 = sc.nextInt();
//                    pid = sc.nextInt();
                    System.err.println(fl + " " + fl2 + " " + pid);
                    Transfer transfer = new Transfer(Database.getAllFlights().get(fl), Database.getAllFlights().get(fl2), pid);
                    exec.execute(transfer);
                    break;
            }
        }
        if(!exec.isTerminated())
        {
            exec.shutdown();
            exec.awaitTermination(5L, TimeUnit.SECONDS);
        }
        System.out.println("Passengers list w/ flights : ");
        for(int i = 0; i < p; i++)
        {
            System.out.println("List for passenger " + i);
            Database.getAllPassengers().get(i).show_flights();
        }
        System.out.println("Flight info");
        for(int i = 0; i < n; i++)
        {
            System.out.println("Details for Flight " + i);
            Database.getAllFlights().get(i).show_info();
        }
//        System.err.println("Transactions done");
//        for(int i = 0; i < n; i++)
//        {
//            Flight f = Database.getAllFlights().get(i);
//            ArrayList<Seat> s = f.getSeats();
//            for(int j = 0; j < s.size(); j++)
//            {
//                if(s.get(j).isOccupied())
//                {
//                    System.err.println(i + " " + s.get(j).getP().id);
//                }
//            }
//        }


    }
}

