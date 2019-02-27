public class Cancel implements Runnable {
    private Flight f;
    private int id;

    public Cancel(Flight f, int id) {
        this.f = f;
        this.id = id;
    }
    public void run()
    {
        //take lock
        LockTable.acquireExclusiveLock(Database.allFlights, this);
        LockTable.acquireExclusiveLock(Database.allPassengers, this);

        Passenger p = Database.getPassenger(id);
        f.removePassenger(p);

        //release lock
        LockTable.releaseExclusiveLock(Database.allFlights, this);
        LockTable.releaseExclusiveLock(Database.allPassengers, this);
    }
}
