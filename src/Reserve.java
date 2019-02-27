import java.util.concurrent.locks.Lock;

public class Reserve implements Runnable{
    private Flight f;
    private int id;

    public Reserve(Flight f, int id) {
        this.f = f;
        this.id = id;
    }
    @Override
    public void run() {
        //take lock
        if (Main.run_type == 1) {
            LockTable.acquireExclusiveLock(Database.getAllFlights(), this);
            LockTable.acquireExclusiveLock(Database.getAllPassengers(), this);
            Passenger p = Database.getPassenger(id);
            Seat s = f.getUnoccupiedSeat();
            if (s != null) s.addPassenger(p);

            //release lock
            LockTable.releaseExclusiveLock(Database.getAllFlights(), this);
            LockTable.releaseExclusiveLock(Database.getAllPassengers(), this);
        } else {
            Passenger p = Database.getPassenger(id);
            LockTable.acquireExclusiveLock(f, this);
            Seat s = f.getUnoccupiedSeat();
            if (s != null)
            {
                LockTable.acquireExclusiveLock(s, this);
                LockTable.acquireExclusiveLock(p, this);
                s.addPassenger(p);
                LockTable.releaseExclusiveLock(p, this);
                LockTable.releaseExclusiveLock(s, this);
            }
            LockTable.releaseExclusiveLock(f, this);
        }
    }
}
