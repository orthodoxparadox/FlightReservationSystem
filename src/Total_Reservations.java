public class Total_Reservations implements Runnable {
    public Total_Reservations() {
    }

    @Override
    public void run() {
        if(Main.run_type == 1) {
            //take lock
            LockTable.acquireExclusiveLock(Database.getAllFlights(), this);
            LockTable.acquireExclusiveLock(Database.getAllPassengers(), this);
            int sum_reservations = 0;
            for (int i = 0; i < Database.getAllFlights().size(); i++) {

                Flight f = Database.getAllFlights().get(i);
                sum_reservations += f.occupied;
            }
//            System.out.println("Total sum of reservations is : ");
//            System.out.println(sum_reservations);
            //release lock
            LockTable.releaseExclusiveLock(Database.getAllFlights(), this);
            LockTable.releaseExclusiveLock(Database.getAllPassengers(), this);
        }
        else
        {
            int sum_reservations = 0;
            for (int i = 0; i < Database.getAllFlights().size(); i++) {
                Flight f = Database.getAllFlights().get(i);
                LockTable.acquireSharedLock(f, this);
                sum_reservations += f.occupied;
            }
            for(int i = Database.getAllFlights().size() - 1; i >= 0; i--)
            {
                Flight f = Database.getAllFlights().get(i);
                LockTable.releaseSharedLock(f, this);
            }
//            System.out.println("Total sum of reservations is : ");
//            System.out.println(sum_reservations);
        }
    }
}
