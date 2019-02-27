public class Total_Reservations implements Runnable {
    public Total_Reservations() {
    }

    @Override
    public void run() {
        //take lock
        LockTable.acquireExclusiveLock(Database.allFlights, this);
        LockTable.acquireExclusiveLock(Database.allPassengers, this);
        int sum_reservations = 0;
        for(int i = 0; i < Database.allFlights.size(); i++)
        {

            Flight f = Database.allFlights.get(i);
            sum_reservations += f.occupied;
        }
        System.out.println(sum_reservations);
        //release lock
        LockTable.releaseExclusiveLock(Database.allFlights, this);
        LockTable.releaseExclusiveLock(Database.allPassengers, this);
    }
}
