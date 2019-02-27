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
            Database.lock.acquireExclusiveLock(this);

            Passenger p = Database.getPassenger(id);
            Seat s = f.findPassengerSeat(p);
            if(s != null) s.removePassenger(p);

            //release lock
            Database.lock.releaseExclusiveLock(this);
        }
        else
        {
            f.lock.acquireExclusiveLock(this);
            Passenger p = Database.getPassenger(id);
            Seat s = f.findPassengerSeat(p);
            if(s != null) {
                s.lock.acquireExclusiveLock(this);
                p.lock.acquireExclusiveLock(this);
                s.removePassenger(p);
                p.lock.releaseExclusiveLock(this);
                s.lock.releaseExclusiveLock(this);
            }
            f.lock.releaseExclusiveLock(this);
        }
    }
}
