public class My_Flights implements Runnable{
    private int id;

    public My_Flights(int id) {
        this.id = id;
    }
    public void run()
    {
        if(Main.run_type == 1) {
            //take lock
            Database.lock.acquireExclusiveLock(this);

            Passenger p = Database.getPassenger(id);
            p.show_flights();

            //release lock
            Database.lock.releaseExclusiveLock(this);
        }
        else
        {
            Passenger p = Database.getPassenger(id);
            p.lock.acquireSharedLock(this);
            p.show_flights();
            p.lock.releaseSharedLock(this);
        }
    }
}
