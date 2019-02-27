public class Cancel implements Runnable {
    private Flight f;
    private int id;

    public Cancel(Flight f, int id) {
        this.f = f;
        this.id = id;
    }
    public void run() {
        if (Main.run_type == 1) {
            //take lock
            LockTable.acquireExclusiveLock(Database.getAllFlights(), this);
            LockTable.acquireExclusiveLock(Database.getAllPassengers(), this);

            Passenger p = Database.getPassenger(id);
            Seat s = f.findPassengerSeat(p);
            if(s != null) s.removePassenger(p);

            //release lock
            LockTable.releaseExclusiveLock(Database.getAllFlights(), this);
            LockTable.releaseExclusiveLock(Database.getAllPassengers(), this);
        }
        else
        {
            LockTable.acquireExclusiveLock(f, this);
            Passenger p = Database.getPassenger(id);
            Seat s = f.findPassengerSeat(p);
            if(s != null) {
                LockTable.acquireExclusiveLock(s, this);
                LockTable.acquireExclusiveLock(p, this);
                s.removePassenger(p);
                LockTable.releaseExclusiveLock(p, this);
                LockTable.releaseExclusiveLock(s, this);
            }
            LockTable.releaseExclusiveLock(f, this);
        }
    }
}
