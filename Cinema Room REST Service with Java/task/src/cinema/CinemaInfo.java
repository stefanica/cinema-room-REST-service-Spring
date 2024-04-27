package cinema;

import java.util.List;

public class CinemaInfo {
    private int rows;
    private int columns;
    private List<Seat> seats;

    public CinemaInfo(int rows, int columns, List<Seat> seats) {
        this.rows = rows;
        this.columns = columns;
        this.seats = seats;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public List<Seat> getSeats() {
        return seats;
    }
}
