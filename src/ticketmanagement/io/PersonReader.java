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

    /**
     * @return {@code null}, если пользователь ввёл пустую строку на вопрос о наличии человека.
     */
    public Person readNullable() {
        if (input.isInteractive()) {
            System.out.print("Человек у билета? Введите y если да, иначе пустая строка для null: ");
        }
        String first = input.readLine("");
        if (first.isEmpty()) {
            return null;
        }
        if (!first.equalsIgnoreCase("y") && !first.equalsIgnoreCase("yes") && !first.equalsIgnoreCase("д")) {
            return null;
        }

        if (input.isInteractive()) {
            System.out.println("Ввод данных человека:");
        }
        float height = input.readPositiveFloat("рост (float, > 0)");
        EyeColor eye = input.readEnum("цвет глаз", EyeColor.class);
        HairColor hair = input.readEnum("цвет волос", HairColor.class);
        Country country = input.readEnum("национальность", Country.class);
        return new Person(height, eye, hair, country);
    }
}
