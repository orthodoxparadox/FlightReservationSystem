public class Transfer implements Runnable{
    private Flight f1;
    private Flight f2;
    private int id;

    public Transfer(Flight f1, Flight f2, int id) {
        this.f1 = f1;
        this.f2 = f2;
        this.id = id;
    }

    @Override
    public void run() {
        //take lock
        LockTable.acquireExclusiveLock(Database.allFlights, this);
        LockTable.acquireExclusiveLock(Database.allPassengers, this);

        Passenger p = Database.allPassengers.get(id);
        boolean doesExist = f1.removePassenger(p);
        if(doesExist)
        {
            boolean canadd = f2.addPassenger(p);
            if(!canadd)
            {
                f1.addPassenger(p);
            }
        }

        //release lock
        LockTable.releaseExclusiveLock(Database.allFlights, this);
        LockTable.releaseExclusiveLock(Database.allPassengers, this);
    }
}