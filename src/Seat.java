public class Seat {
    private boolean isOccupied;
    private Flight f;
    private int seatNum;
    private Passenger p;

    public Seat(boolean isOccupied, Flight f, int seatNum, Passenger p) {
        this.isOccupied = isOccupied;
        this.f = f;
        this.seatNum = seatNum;
        this.p = p;
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
