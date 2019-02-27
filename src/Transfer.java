public class Transfer implements Runnable {
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
        if (Main.run_type == 1) {
            //take lock
            Database.lock.acquireExclusiveLock(this);

            Passenger p = Database.getAllPassengers().get(id);
            boolean doesExist = f1.removePassenger(p);
            if (doesExist) {
                boolean canadd = f2.addPassenger(p);
                if (!canadd) {
                    f1.addPassenger(p);
                }
            }

            //release lock
            Database.lock.releaseExclusiveLock(this);
        } else {
            Passenger p = Database.getPassenger(id);
            f1.lock.acquireExclusiveLock(this);
            Seat s1 = f1.findPassengerSeat(p);
            if (s1 != null) {
                f2.lock.acquireExclusiveLock(this);
                Seat s2 = f2.getUnoccupiedSeat();
                if (s2 != null) {
                    s1.lock.acquireExclusiveLock(this);
                    s2.lock.acquireExclusiveLock(this);
                    p.lock.acquireExclusiveLock(this);
                    s1.removePassenger(p);
                    s2.addPassenger(p);
                    p.lock.releaseExclusiveLock(this);
                    s1.lock.releaseExclusiveLock(this);
                    s2.lock.releaseExclusiveLock(this);
                }
                f2.lock.releaseExclusiveLock(this);
            }
            f1.lock.releaseExclusiveLock(this);
        }
    }
}