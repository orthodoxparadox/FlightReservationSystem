public class My_Flights implements Runnable{
    private int id;

    public My_Flights(int id) {
        this.id = id;
    }
    public void run()
    {
        if(Main.run_type == 1) {
            //take lock
            LockTable.acquireExclusiveLock(Database.getAllFlights(), this);
            LockTable.acquireExclusiveLock(Database.getAllPassengers(), this);

            Passenger p = Database.getPassenger(id);
            p.show_flights();

            //release lock
            LockTable.releaseExclusiveLock(Database.getAllFlights(), this);
            LockTable.releaseExclusiveLock(Database.getAllPassengers(), this);
        }
        else
        {
            Passenger p = Database.getPassenger(id);
            LockTable.acquireSharedLock(p, this);
            p.show_flights();
            LockTable.releaseSharedLock(p, this);
        }
    }
}
