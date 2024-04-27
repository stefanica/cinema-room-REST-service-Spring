package cinema;

import java.util.UUID;

public class Ticket {
    private UUID token;
    private Seat ticket;

    public Ticket(UUID uuid, Seat seat) {
        this.token = uuid;
        this.ticket = seat;
    }

    public UUID getToken() {
        return token;
    }

    public Seat getTicket() {
        return ticket;
    }
}
