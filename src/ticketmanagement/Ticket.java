package ticketmanagement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Класс, представляющий билет.
 * Содержит информацию о билете: id, название, координаты, цену, комментарий, тип и информацию о человеке.
 * Реализует сортировку по умолчанию (по id).
 * 
 * @author AS
 * @version 1.0
 */

public class Ticket implements Comparable<Ticket> {
    private static long nextId = 1;
    
    private Long id; 
    private String name; 
    private Coordinates coordinates; 
    private LocalDate creationDate; 
    private int price;
    private String comment; 
    private TicketType type; 
    private Person person; 

    public Ticket(String name, Coordinates coordinates, int price, 
                  String comment, TicketType type, Person person) {
        this.id = nextId++;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.price = price;
        this.comment = comment;
        this.type = type;
        this.person = person;
    }

    public Ticket(Long id, String name, Coordinates coordinates, LocalDate creationDate,
                  int price, String comment, TicketType type, Person person) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.price = price;
        this.comment = comment;
        this.type = type;
        this.person = person;
        if (id >= nextId) nextId = id + 1;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public Coordinates getCoordinates() { return coordinates; }
    public LocalDate getCreationDate() { return creationDate; }
    public int getPrice() { return price; }
    public String getComment() { return comment; }
    public TicketType getType() { return type; }
    public Person getPerson() { return person; }

    public void setName(String name) { this.name = name; }
    public void setCoordinates(Coordinates coordinates) { this.coordinates = coordinates; }
    public void setPrice(int price) { this.price = price; }
    public void setComment(String comment) { this.comment = comment; }
    public void setType(TicketType type) { this.type = type; }
    public void setPerson(Person person) { this.person = person; }

    @Override
    public int compareTo(Ticket other) {
        return Long.compare(this.id, other.id);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("БИЛЕТ #%d\n", id));
        sb.append(String.format("Название:      %s\n", name));
        sb.append(String.format("Координаты:    %s\n", coordinates));
        sb.append(String.format("Дата создания: %s\n", creationDate.format(formatter)));
        sb.append(String.format("Цена:          %d\n", price));
        sb.append(String.format("Комментарий:   %s\n", comment));
        sb.append(String.format("Тип билета:    %s\n", type));
        
        if (person != null) {
            sb.append("Информация о человеке:\n");
            sb.append(String.format("Рост:         %.2f\n", person.getHeight()));
            sb.append(String.format("Цвет глаз:    %s\n", person.getEyeColor()));
            sb.append(String.format("Цвет волос:   %s\n", person.getHairColor()));
            sb.append(String.format("Национальность: %s\n", person.getNationality()));
        } else {
            sb.append("Человек:        не указан\n");
        }
        
        return sb.toString();
    }


    public int compareCoordinates(Ticket other) {
        double thisValue = this.coordinates.getX() + this.coordinates.getY();
        double otherValue = other.coordinates.getX() + other.coordinates.getY();
        return Double.compare(thisValue, otherValue);
    }
}