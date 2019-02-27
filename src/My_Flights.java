public class My_Flights implements Runnable{
    private int id;

    public My_Flights(int id) {
        this.id = id;
    }
    public void run()
    {
        //take lock
        LockTable.acquireExclusiveLock(Database.allFlights, this);
        LockTable.acquireExclusiveLock(Database.allPassengers, this);

        Passenger p = Database.getPassenger(id);
        p.show_flights();

        //release lock
        LockTable.releaseExclusiveLock(Database.allFlights, this);
        LockTable.releaseExclusiveLock(Database.allPassengers, this);
    }
}
