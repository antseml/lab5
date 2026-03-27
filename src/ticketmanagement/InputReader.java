package ticketmanagement;

import java.util.*;
public class InputReader {
    private Scanner scanner;
    private boolean isInteractive;

    public InputReader(Scanner scanner, boolean isInteractive) {
        
        this.scanner = new Scanner(System.in, "UTF-8");
        
        this.isInteractive = isInteractive;
    }

    public String readNonEmptyString(String prompt) {
        while (true) {
            if (isInteractive) System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            if (isInteractive) System.out.println("Значение не может быть пустым");
        }
    }

    public String readString(String prompt) {
        if (isInteractive) System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public Long readLong(String prompt, boolean positiveOnly) {
        while (true) {
            try {
                if (isInteractive) System.out.print(prompt);
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    if (isInteractive) System.out.println("Значение не может быть пустым");
                    continue;
                }
                long value = Long.parseLong(input);
                if (positiveOnly && value <= 0) {
                    if (isInteractive) System.out.println("Значение должно быть больше 0");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                if (isInteractive) System.out.println("Ошибка: введите целое число");
            }
        }
    }

    public Double readDouble(String prompt, boolean canBeNull) {
        while (true) {
            try {
                if (isInteractive) System.out.print(prompt);
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    if (canBeNull) return null;
                    if (isInteractive) System.out.println("Значение не может быть пустым");
                    continue;
                }
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                if (isInteractive) System.out.println("Ошибка: введите число с плавающей точкой");
            }
        }
    }

    public int readInt(String prompt, boolean positiveOnly) {
        while (true) {
            try {
                if (isInteractive) System.out.print(prompt);
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    if (isInteractive) System.out.println("Значение не может быть пустым");
                    continue;
                }
                int value = Integer.parseInt(input);
                if (positiveOnly && value <= 0) {
                    if (isInteractive) System.out.println("Значение должно быть больше 0");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                if (isInteractive) System.out.println("Ошибка: введите целое число");
            }
        }
    }

    public float readFloat(String prompt, boolean positiveOnly) {
        while (true) {
            try {
                if (isInteractive) System.out.print(prompt);
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    if (isInteractive) System.out.println("Значение не может быть пустым");
                    continue;
                }
                float value = Float.parseFloat(input);
                if (positiveOnly && value <= 0) {
                    if (isInteractive) System.out.println("Значение должно быть больше 0");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                if (isInteractive) System.out.println("Ошибка: введите число");
            }
        }
    }

    public <T extends Enum<T>> T readEnum(String prompt, Class<T> enumClass) {
        T[] constants = enumClass.getEnumConstants();
        while (true) {
            if (isInteractive) {
                System.out.print(prompt + " (доступны: ");
                for (int i = 0; i < constants.length; i++) {
                    System.out.print(constants[i]);
                    if (i < constants.length - 1) System.out.print(", ");
                }
                System.out.print("): ");
            }
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.isEmpty()) {
                if (isInteractive) System.out.println("Значение не может быть пустым");
                continue;
            }
            try {
                return Enum.valueOf(enumClass, input);
            } catch (IllegalArgumentException e) {
                if (isInteractive) System.out.println("Ошибка: введите одно из допустимых значений");
            }
        }
    }

    public Coordinates readCoordinates() {
        Double x = readDouble("  Введите координату X (Double): ", false);
        long y = readLong("  Введите координату Y (long): ", false);
        return new Coordinates(x, y);
    }

    public Person readPerson() {
        if (isInteractive) System.out.println("  Введите информацию о человеке (или оставьте пустым для null):");
        String hasPerson = readString("  Есть человек? (y/n, по умолчанию n): ");
        if (!hasPerson.equalsIgnoreCase("y")) {
            return null;
        }

        float height = readFloat("  Введите рост (float > 0): ", true);
        Color eyeColor = readEnum("  Введите цвет глаз", Color.class);
        Color hairColor = readEnum("  Введите цвет волос", Color.class);
        Country nationality = readEnum("  Введите национальность", Country.class);

        return new Person(height, eyeColor, hairColor, nationality);
    }

    public Ticket readTicket() {
        String name = readNonEmptyString("  Введите название билета: ");
        
        if (isInteractive) System.out.println("  Введите координаты:");
        Coordinates coordinates = readCoordinates();
        
        int price = readInt("  Введите цену (int > 0): ", true);
        String comment = readNonEmptyString("  Введите комментарий: ");
        TicketType type = readEnum("  Введите тип билета", TicketType.class);
        Person person = readPerson();

        return new Ticket(name, coordinates, price, comment, type, person);
    }
}