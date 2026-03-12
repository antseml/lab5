package ticketmanagement;

public class Ticket {
    private static long nextId = 1;

    private Long id;
    private String name;
    private int price;

    public Ticket(String name, int price) {
        this.id = nextId++;
        this.name = name;
        this.price = price;
    }

    public String toString() {
        return "Ticket{id=" + id + ", name='" + name + "', price=" + price + "}";
    }
}