import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Flight {
    private int capacity;
    ArrayList<Seat> seats;
    private int flightno;
    int occupied;
    public Flight(int capacity, int flightno) {
        this.capacity = capacity;
        this.seats = new ArrayList<Seat>();
        this.flightno = flightno;
        for(int i = 0; i < capacity; i++)
        {
            seats.add(new Seat(false, this, i + 1, null));
        }
        this.occupied = 0;
    }

    public boolean addPassenger(Passenger p) {
        if(p.check(this) || occupied == capacity)
        {
            return false;
        }
        for(int i = 0; i < capacity; i++) {
            if(!seats.get(i).isOccupied())
            {
                seats.get(i).setOccupied(true);
                seats.get(i).setP(p);
                p.add_flight(this, seats.get(i));
                occupied++;
                return true;
            }
        }
        return false;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public ArrayList<Seat> getSeats() {
        return seats;
    }

    public void setSeats(ArrayList<Seat> seats) {
        this.seats = seats;
    }

    public int getFlightno() {
        return flightno;
    }

    public void setFlightno(int flightno) {
        this.flightno = flightno;
    }

    public boolean removePassenger(Passenger p) {
        if(occupied == 0) return false;
        if(!p.check(this)) return false;
        for(int i = 0; i < capacity; i++)
        {
            if(seats.get(i).getP() == p)
            {
                seats.get(i).setOccupied(false);
                seats.get(i).setP(null);
                p.remove_flight(this, seats.get(i));
                occupied--;
                return true;
            }
        }
        return false;
    }
    public void show_info()
    {
        System.out.println(occupied + " seats occupied out of " + capacity);
        for(int i = 0; i < capacity; i++)
        {
            if(seats.get(i).isOccupied())
            {
                System.out.print(seats.get(i).getP().id + " ");
            }
        }
        System.out.println();
    }

    public Seat findPassengerSeat(Passenger p)
    {
        for(int i = 0; i < capacity; i++)
        {
            if(seats.get(i).getP() == p)
            {
                return seats.get(i);
            }
        }
        return null;
    }

    public Seat getUnoccupiedSeat() {
        for(int i = 0; i < capacity; i++) {
            if(!seats.get(i).isOccupied())
            {
                return seats.get(i);
            }
        }
        return null;
    }
}
