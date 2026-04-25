package ticketmanagement.services;

/**
 * Генерация уникальных положительных id для билетов.
 */
public class IdGenerator {
    private long nextId = 1;

    public synchronized long nextId() {
        return nextId++;
    }

    /**
     * После загрузки из файла поднимаем счётчик, чтобы новые id не пересекались.
     */
    public synchronized void notifyMaxExistingId(long id) {
        if (id >= nextId) {
            nextId = id + 1;
        }
    }
}
