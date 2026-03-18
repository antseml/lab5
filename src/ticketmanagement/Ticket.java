package ticketmanagement;

import java.time.LocalDate;

public class Ticket implements Comparable<Ticket> {
    private static long nextId = 1;

    private Long id;
    private String name;
    private Coordinates coordinates;
    private LocalDate creationDate;
    private int price;
    private String comment;
    private TicketType type;

    public Ticket(String name, Coordinates coordinates, int price, String comment, TicketType type) {
        this.id = nextId++;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.price = price;
        this.comment = comment;
        this.type = type;
    }

    public Long getId() { return id; }

    @Override
    public int compareTo(Ticket o) {
        return id.compareTo(o.id);
    }

    @Override
    public String toString() {
        return "Ticket{id=" + id + ", name='" + name + "', price=" + price + "}";
    }
}