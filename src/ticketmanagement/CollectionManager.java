package ticketmanagement;

import java.time.LocalDateTime;
import java.util.*;

public class CollectionManager {
    private Vector<Ticket> collection = new Vector<>();
    private LocalDateTime initDate = LocalDateTime.now();

    public String getInfo() {
        return "Тип: Vector\nДата: " + initDate + "\nРазмер: " + collection.size();
    }

    public String show() {
        if (collection.isEmpty()) return "Пусто";
        StringBuilder sb = new StringBuilder();
        for (Ticket t : collection) {
            sb.append(t).append("\n");
        }
        return sb.toString();
    }

    public void add(Ticket t) {
        collection.add(t);
    }

    public boolean updateById(Long id, Ticket newTicket) {
        for (int i = 0; i < collection.size(); i++) {
            if (collection.get(i).getId().equals(id)) {
                collection.set(i, newTicket);
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
}