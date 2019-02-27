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
            LockTable.acquireExclusiveLock(Database.getAllFlights(), this);
            LockTable.acquireExclusiveLock(Database.getAllPassengers(), this);

            Passenger p = Database.getAllPassengers().get(id);
            boolean doesExist = f1.removePassenger(p);
            if (doesExist) {
                boolean canadd = f2.addPassenger(p);
                if (!canadd) {
                    f1.addPassenger(p);
                }
            }

            //release lock
            LockTable.releaseExclusiveLock(Database.getAllFlights(), this);
            LockTable.releaseExclusiveLock(Database.getAllPassengers(), this);
        } else {
            Passenger p = Database.getPassenger(id);
            LockTable.acquireExclusiveLock(f1, this);
            Seat s1 = f1.findPassengerSeat(p);
            if (s1 != null) {
                LockTable.acquireExclusiveLock(f2, this);
                Seat s2 = f2.getUnoccupiedSeat();
                if (s2 != null) {
                    LockTable.acquireExclusiveLock(s1, this);
                    LockTable.acquireExclusiveLock(s2, this);
                    LockTable.acquireExclusiveLock(p, this);
                    s1.removePassenger(p);
                    s2.addPassenger(p);
                    LockTable.releaseExclusiveLock(p, this);
                    LockTable.releaseExclusiveLock(s1, this);
                    LockTable.releaseExclusiveLock(s2, this);
                }
                LockTable.releaseExclusiveLock(f2, this);
            }
            LockTable.releaseExclusiveLock(f1, this);
        }
    }
}