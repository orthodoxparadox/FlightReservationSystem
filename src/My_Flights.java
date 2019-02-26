public class My_Flights implements Runnable{
    private int id;

    public My_Flights(int id) {
        this.id = id;
    }
    public void run()
    {
        Passenger p = Database.getPassenger(id);
        p.show_flights();
    }
}
