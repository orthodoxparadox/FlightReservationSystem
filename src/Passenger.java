import java.util.ArrayList;

public class Passenger {
    int id;
    ArrayList<Seat> mySeats;
    ArrayList<Flight> myFlights;

    public Passenger(int id) {
        this.id = id;
        mySeats = new ArrayList<Seat>();
        myFlights = new ArrayList<Flight>();
    }

    public void add_flight(Flight f, Seat s)
    {
        myFlights.add(f);
        mySeats.add(s);
    }
    public void remove_flight(Flight f, Seat s)
    {
        myFlights.remove(f);
        mySeats.remove(s);
    }

    public boolean check(Flight flight) {
        return myFlights.contains(flight);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Seat> getMySeats() {
        return mySeats;
    }

    public void setMySeats(ArrayList<Seat> mySeats) {
        this.mySeats = mySeats;
    }

    public ArrayList<Flight> getMyFlights() {
        return myFlights;
    }

    public void setMyFlights(ArrayList<Flight> myFlights) {
        this.myFlights = myFlights;
    }

    public void show_flights() {
        for(int i = 0; i < myFlights.size(); i++)
        {
            Flight f = myFlights.get(i);
            System.out.println(f.getFlightno());
        }
    }
}
