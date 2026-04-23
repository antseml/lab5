package ticketmanagement;

import java.util.*;

/**
 * Класс для чтения пользовательского ввода.
 * Поддерживает интерактивный режим и чтение из файлов скриптов.
 * 
 * @author AS
 * @version 1.1
 */

public class InputReader {
    private Scanner scanner;
    private boolean isInteractive;

    public InputReader(Scanner scanner, boolean isInteractive) {
        
        this.scanner = scanner;
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
                input = input.replace(',', '.');
                double value = Double.parseDouble(input);
                long longValue = (long) value;
                if (value != longValue) {
                    if (isInteractive) System.out.println("Ошибка: введите целое число");
                    continue;
                }
                if (positiveOnly && longValue <= 0) {
                    if (isInteractive) System.out.println("Значение должно быть больше 0");
                    continue;
                }
                return longValue;
            } catch (NumberFormatException e) {
                if (isInteractive) System.out.println("Ошибка: введите число");
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
                input = input.replace(',', '.');
                double value = Double.parseDouble(input);
                int intValue = (int) value;
                if (value != intValue) {
                    if (isInteractive) System.out.println("Ошибка: введите целое число");
                    continue;
                }
                if (positiveOnly && intValue <= 0) {
                    if (isInteractive) System.out.println("Значение должно быть больше 0");
                    continue;
                }
                return intValue;
            } catch (NumberFormatException e) {
                if (isInteractive) System.out.println("Ошибка: введите число");
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
                input = input.replace(',', '.');
                
                if (input.contains(".")) {
                    String[] parts = input.split("\\.");
                    if (parts.length == 2 && parts[1].length() > 15) {
                        parts[1] = parts[1].substring(0, 15);
                        input = parts[0] + "." + parts[1];
                    }
                }
                
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                if (isInteractive) System.out.println("Ошибка: введите число с плавающей точкой");
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
                input = input.replace(',', '.');
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
                System.out.print(prompt + " (");
                for (int i = 0; i < constants.length; i++) {
                    System.out.print((i + 1) + "-" + constants[i]);
                    if (i < constants.length - 1) System.out.print(", ");
                }
                System.out.print("): ");
            }
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                if (isInteractive) System.out.println("Значение не может быть пустым");
                continue;
            }
            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= constants.length) {
                    return constants[choice - 1];
                }
                throw new IllegalArgumentException();
            } catch (Exception e) {
                try {
                    return Enum.valueOf(enumClass, input.toUpperCase());
                } catch (IllegalArgumentException ex) {
                    if (isInteractive) System.out.println("Ошибка: введите номер или значение из списка");
                }
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