package ticketmanagement;

import java.util.*;

public class CollectionManager {
    private Vector<Ticket> collection = new Vector<>();

    public String getInfo() {
        return "Количество элементов: " + collection.size();
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
}