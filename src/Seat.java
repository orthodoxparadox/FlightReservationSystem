import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Seat {
    private boolean isOccupied;
    private Flight f;
    private int seatNum;
    private Passenger p;
    LockTable lock;
    int holders;

    public Seat(boolean isOccupied, Flight f, int seatNum, Passenger p) {
        this.isOccupied = isOccupied;
        this.f = f;
        this.seatNum = seatNum;
        this.p = p;
        this.holders = 0;
        lock = new LockTable();
    }

    public boolean addPassenger(Passenger p){
        Flight f = this.f;
        if(p.check(f)) return false;
        this.setOccupied(true);
        this.setP(p);
        p.add_flight(f, this);
        f.occupied++;
        return true;
    }
    public void removePassenger(Passenger p)
    {
        Flight f = this.f;
        this.setOccupied(false);
        this.setP(null);
        p.remove_flight(f, this);
        f.occupied--;
    }


    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public Flight getF() {
        return f;
    }

    public void setF(Flight f) {
        this.f = f;
    }

    public int getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(int seatNum) {
        this.seatNum = seatNum;
    }

    public Passenger getP() {
        return p;
    }

    public void setP(Passenger p) {
        this.p = p;
    }
}
