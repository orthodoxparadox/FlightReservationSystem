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
        Passenger p = Database.getPassenger(id);
        f.addPassenger(p);
    }
}
