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
            Database.lock.acquireExclusiveLock(this);
            Passenger p = Database.getPassenger(id);
            Seat s = f.getUnoccupiedSeat();
            if (s != null) s.addPassenger(p);

            //release lock
            Database.lock.releaseExclusiveLock(this);
        } else {
            Passenger p = Database.getPassenger(id);
            f.lock.acquireExclusiveLock(this);
            Seat s = f.getUnoccupiedSeat();
            if (s != null)
            {
                s.lock.acquireExclusiveLock(this);
                p.lock.acquireExclusiveLock(this);
                s.addPassenger(p);
                p.lock.releaseExclusiveLock(this);
                s.lock.releaseExclusiveLock(this);
            }
            f.lock.releaseExclusiveLock(this);
        }
    }
}
