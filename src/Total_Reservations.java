public class Total_Reservations implements Runnable {
    public Total_Reservations() {
    }

    @Override
    public void run() {
        if(Main.run_type == 1) {
            //take lock
            Database.lock.acquireExclusiveLock(this);
            int sum_reservations = 0;
            for (int i = 0; i < Database.getAllFlights().size(); i++) {

                Flight f = Database.getAllFlights().get(i);
                sum_reservations += f.occupied;
            }
//            System.out.println("Total sum of reservations is : ");
//            System.out.println(sum_reservations);
            //release lock
            Database.lock.releaseExclusiveLock(this);
        }
        else
        {
            int sum_reservations = 0;
            for (int i = 0; i < Database.getAllFlights().size(); i++) {
                Flight f = Database.getAllFlights().get(i);
                f.lock.acquireSharedLock(this);
                sum_reservations += f.occupied;
            }
            for(int i = Database.getAllFlights().size() - 1; i >= 0; i--)
            {
                Flight f = Database.getAllFlights().get(i);
                f.lock.releaseSharedLock(this);
            }
//            System.out.println("Total sum of reservations is : ");
//            System.out.println(sum_reservations);
        }
    }
}
