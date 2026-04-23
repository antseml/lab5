package ticketmanagement;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Класс для управления коллекцией билетов.
 * Хранит коллекцию в java.util.Vector, обеспечивает основные операции.
 * 
 * @author AS
 * @version 1.1
 */

public class CollectionManager {
    private Vector<Ticket> collection;
    private LocalDateTime initDate;
    private String fileName;

    public CollectionManager(String fileName) {
        this.collection = new Vector<>();
        this.initDate = LocalDateTime.now();
        this.fileName = fileName;
    }

    public Vector<Ticket> getCollection() { return collection; }
    public LocalDateTime getInitDate() { return initDate; }

    public String getInfo() {
        return String.format("Тип коллекции: %s\nДата инициализации: %s\nКоличество элементов: %d",
                collection.getClass().getName(), initDate, collection.size());
    }

    public String show() {
    if (collection.isEmpty()) return "Коллекция пуста";
    StringBuilder sb = new StringBuilder();
    sb.append("\n");
    for (Ticket t : collection) {
        sb.append(t.toString());
    }
    return sb.toString();
}

    public void add(Ticket ticket) {
        collection.add(ticket);
    }

        public boolean updateFieldById(Long id, String fieldName, Object value) {
        for (int i = 0; i < collection.size(); i++) {
            if (collection.get(i).getId().equals(id)) {
                Ticket ticket = collection.get(i);
                switch (fieldName) {
                    case "name":
                        ticket.setName((String) value);
                        break;
                    case "coordinates":
                        ticket.setCoordinates((Coordinates) value);
                        break;
                    case "price":
                        ticket.setPrice((int) value);
                        break;
                    case "comment":
                        ticket.setComment((String) value);
                        break;
                    case "type":
                        ticket.setType((TicketType) value);
                        break;
                    case "person":
                        ticket.setPerson((Person) value);
                        break;
                    default:
                        return false;
                }
                collection.set(i, ticket);
                return true;
            }
        }
        return false;
    }

    public boolean removeById(Long id) {
        return collection.removeIf(t -> t.getId().equals(id));
    }

    public void clear() {
        collection.clear();
    }

    public boolean removeAt(int index) {
        if (index >= 0 && index < collection.size()) {
            collection.remove(index);
            return true;
        }
        return false;
    }

    public boolean addIfMax(Ticket ticket) {
        if (collection.isEmpty()) {
            collection.add(ticket);
            return true;
        }
        
        Ticket maxTicket = Collections.max(collection);
        if (ticket.compareTo(maxTicket) > 0) {
            collection.add(ticket);
            return true;
        }
        return false;
    }

    public void reorder() {
        Collections.reverse(collection);
    }

    public Ticket maxByCoordinates() {
        if (collection.isEmpty()) return null;
        return Collections.max(collection, (t1, t2) -> t1.compareCoordinates(t2));
    }

    public Set<Integer> getUniquePrices() {
        Set<Integer> prices = new HashSet<>();
        for (Ticket t : collection) {
            prices.add(t.getPrice());
        }
        return prices;
    }

    public List<Integer> getPricesDescending() {
        List<Integer> prices = new ArrayList<>();
        for (Ticket t : collection) {
            prices.add(t.getPrice());
        }
        prices.sort(Collections.reverseOrder());
        return prices;
    }

    public void loadFromFile() throws Exception {
        FileManager fileManager = new FileManager();
        List<Ticket> loaded = fileManager.loadFromFile(fileName);
        collection.clear();
        collection.addAll(loaded);
    }

    public void saveToFile() throws Exception {
        FileManager fileManager = new FileManager();
        fileManager.saveToFile(fileName, collection);
    }
}