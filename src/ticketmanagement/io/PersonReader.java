package ticketmanagement.io;

import ticketmanagement.models.Country;
import ticketmanagement.models.EyeColor;
import ticketmanagement.models.HairColor;
import ticketmanagement.models.Person;

/**
 * Ввод человека по полям; пустая строка на первом шаге — без человека (null).
 */
public class PersonReader {
    private final ConsoleInput input;

    public PersonReader(ConsoleInput input) {
        this.input = input;
    }
    public Person readNullable() {
        if (input.isInteractive()) {
            System.out.print("Есть информация о человеке?Введите 'y' если да, по дефолту - 'n': ");
        }
        String first = input.readLine("");
        if (first.isEmpty()) {
            return null;
        }
        if (!first.equalsIgnoreCase("y") && !first.equalsIgnoreCase("yes")) {
            return null;
        }

        if (input.isInteractive()) {
            System.out.println("Ввод данных человека:");
        }
        double height = input.readPositiveDouble("рост (double, > 0)");
        EyeColor eye = input.readEnum("цвет глаз", EyeColor.class);
        HairColor hair = input.readEnum("цвет волос", HairColor.class);
        Country country = input.readEnum("национальность", Country.class);
        return new Person(height, eye, hair, country);
    }
}
