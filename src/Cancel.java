public class Cancel implements Runnable {
    private Flight f;
    private int id;

    public Cancel(Flight f, int id) {
        this.f = f;
        this.id = id;
    }
    public void run()
    {
        Passenger p = Database.getPassenger(id);
        f.removePassenger(p);
    }
}
