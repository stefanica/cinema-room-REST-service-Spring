package cinema;

public class Seat {
    private int row;
    private int column;
    private int price;
    //private boolean availability = true;

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        this.price = row <= 4 ? 10: 8;
    }
/*
    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public boolean isAvailability() {
        return this.availability;
    }
*/
    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getPrice() {
        return price;
    }
}
