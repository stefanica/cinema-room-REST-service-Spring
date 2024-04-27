package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
public class CinemaController {
    private boolean[][] isTaken = new boolean[9][9];
    private boolean first = true;
    private CinemaInfo cinemaInfo;
    private Map<String, Ticket> ticketList = new HashMap<>();

    public CinemaController() {
        this.cinemaInfo = getCinema();
    }

    //@GetMapping
    //@RequestMapping("/seats")
    @GetMapping("/seats")
    public CinemaInfo getSeatInfo() {
        /*
        int rows = 9;
        int columns = 9;
        List<Seat> seats = new ArrayList<>();

            for (int i = 1; i <= rows; i++) {
                for (int j = 1; j <= columns; j++) {
                    int price = 0;
                    if (i <= 4) {
                        price = 10;
                    } else {
                        price = 8;
                    }
                    seats.add(new Seat(i, j, price));
                    isTaken[i][j] = false;
                }
            }
            cinemaInfo = new CinemaInfo(rows, columns, seats);
            first = false;*/

        return cinemaInfo;
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseSeat(@RequestBody Seat seat) {
        int row = seat.getRow();
        int column = seat.getColumn();

        if (row > 9  || row < 0 || column < 0 || column > 9) {
            return new ResponseEntity<>(Map.of("error", "The number of a row or a column is out of bounds!"), HttpStatus.BAD_REQUEST);
        }

        if (isTaken[row][column] == false) {
            //stage 2 implementation
            /*List<Seat> seatsList = cinemaInfo.getSeats();
            for (Seat item : seatsList) {
                if (item.getRow() == row && item.getColumn() == column) {
                    isTaken[row][column] = true;
                    Ticket ticket = new Ticket(UUID.randomUUID(), item);
                    return ResponseEntity.ok(ticket);
                }
            }*/
            //stage 3 implementation
            isTaken[row][column] = true;
            String key = row + "" + column;
            Ticket ticket = ticketList.get(key);
            //return new ResponseEntity<>(Map.of("error", "The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);
            return ResponseEntity.ok(ticket);
        }

        return new ResponseEntity<>(Map.of("error", "The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnSeat(@RequestBody Token token) {
        boolean found = false;
        String keyItem = "";
        for (String key : ticketList.keySet()) {
            Ticket ticket = ticketList.get(key);
            if (token.getToken().equals(ticket.getToken())) {
                Seat seat = ticket.getTicket();
                int row = seat.getRow();
                int column = seat.getColumn();
                if (isTaken[row][column] == true) {
                    found = true;
                    keyItem = key;
                }
            }
        }

        if (found) {
            Ticket ticket = ticketList.get(keyItem);
            Seat seat = ticket.getTicket();
            isTaken[seat.getRow()][seat.getColumn()] = false;
            //ticketList.remove(keyItem);
            return new ResponseEntity<>(Map.of("ticket", ticket.getTicket()), HttpStatus.OK);
        }

        return new ResponseEntity<>(Map.of("error", "Wrong token!"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getStats(@RequestParam(required = false) String password) {

        if (password != null && password.equals("super_secret")) {
            int income = 0;
            int available = 81;
            int purchased = 0;
            for (int i = 0; i < isTaken.length; i++) {
                for (int j = 0; j < isTaken[i].length; j++) {
                    if (isTaken[i][j]) {
                        available--;
                        purchased++;
                        if (i <= 4) {
                            income += 10;
                        } else {
                            income += 8;
                        }
                    }
                }
            }
            Stats stats = new Stats(income, available, purchased);
            return new ResponseEntity<>(stats, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of("error", "The password is wrong!"), HttpStatus.valueOf(401));
        }
    }

    public CinemaInfo getCinema() {
        int rows = 9;
        int columns = 9;
        List<Seat> seats = new ArrayList<>();

        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= columns; j++) {
                int price = 0;
                if (i <= 4) {
                    price = 10;
                } else {
                    price = 8;
                }
                Seat newSeat = new Seat(i, j);
                String key = i + "" + j;
                ticketList.put(key, new Ticket(UUID.randomUUID(), newSeat));
                seats.add(newSeat);
            }
        }
        return new CinemaInfo(rows, columns, seats);
    }



}
