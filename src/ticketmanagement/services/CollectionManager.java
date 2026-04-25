package ticketmanagement.services;

import ticketmanagement.models.Coordinates;
import ticketmanagement.models.Person;
import ticketmanagement.models.Ticket;
import ticketmanagement.models.TicketType;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Центральная работа с коллекцией {@link Vector}.
 */
public class CollectionManager {
    private final Vector<Ticket> collection = new Vector<>();
    private final LocalDateTime initDate = LocalDateTime.now();
    private final String fileName;
    private final FileManager fileManager;
    private final IdGenerator idGenerator;

    public CollectionManager(String fileName, FileManager fileManager, IdGenerator idGenerator) {
        this.fileName = fileName;
        this.fileManager = fileManager;
        this.idGenerator = idGenerator;
    }

    public Vector<Ticket> getCollection() {
        return collection;
    }

    public LocalDateTime getInitDate() {
        return initDate;
    }

    public String getFileName() {
        return fileName;
    }

    public IdGenerator getIdGenerator() {
        return idGenerator;
    }

    public String getInfo() {
        return String.format("Тип коллекции: %s\nДата инициализации: %s\nКоличество элементов: %d",
                collection.getClass().getName(), initDate, collection.size());
    }

    public String show() {
        if (collection.isEmpty()) {
            return "Коллекция пуста";
        }
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
                        ticket.setPrice((Integer) value);
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
        if (collection.isEmpty()) {
            return null;
        }
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
        List<Ticket> loaded = fileManager.loadFromFile(fileName);
        collection.clear();
        collection.addAll(loaded);
        for (Ticket t : collection) {
            idGenerator.notifyMaxExistingId(t.getId());
        }
    }

    public void saveToFile() throws Exception {
        fileManager.saveToFile(fileName, collection);
    }
}
