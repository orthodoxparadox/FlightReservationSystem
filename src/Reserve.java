public class Reserve implements Runnable{
    private Flight f;
    private int id;

    public Reserve(Flight f, int id) {
        this.f = f;
        this.id = id;
    }
    @Override
    public void run()
    {
        //take lock
        LockTable.acquireExclusiveLock(Database.allFlights, this);
        LockTable.acquireExclusiveLock(Database.allPassengers, this);

        Passenger p = Database.getPassenger(id);
        f.addPassenger(p);

        //release lock
        LockTable.releaseExclusiveLock(Database.allFlights, this);
        LockTable.releaseExclusiveLock(Database.allPassengers, this);
    }
}
