package ticketmanagement.io;

import ticketmanagement.models.Coordinates;
import ticketmanagement.models.Person;
import ticketmanagement.models.Ticket;
import ticketmanagement.models.TicketType;
import ticketmanagement.services.IdGenerator;

import java.time.LocalDate;

/**
 * Сборка нового билета: id и дата создаются автоматически.
 */
public class TicketReader {
    private final ConsoleInput input;
    private final CoordinatesReader coordinatesReader;
    private final PersonReader personReader;
    private final IdGenerator idGenerator;

    public TicketReader(ConsoleInput input, CoordinatesReader coordinatesReader,
                        PersonReader personReader, IdGenerator idGenerator) {
        this.input = input;
        this.coordinatesReader = coordinatesReader;
        this.personReader = personReader;
        this.idGenerator = idGenerator;
    }

    public Ticket readNewTicket() {
        if (input.isInteractive()) {
            System.out.println("Ввод нового билета (id и дата создаются автоматически):");
        }
        String name = input.readNonEmptyLine("Введите название билета (name): ");
        Coordinates coordinates = coordinatesReader.read();
        int price = input.readInt("цену (price, целое > 0)", true);
        String comment = input.readNonEmptyLine("Введите комментарий (comment, не пустой): ");
        TicketType type = input.readEnum("тип билета (type)", TicketType.class);
        Person person = personReader.readNullable();

        long id = idGenerator.nextId();
        return new Ticket(id, name, coordinates, LocalDate.now(), price, comment, type, person);
    }
}
