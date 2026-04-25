package ticketmanagement.io;

import java.util.Scanner;

/**
 * Ввод с консоли или из скрипта: повтор при ошибке, для enum выводит список констант.
 */
public class ConsoleInput {
    private Scanner scanner;
    private boolean interactive;

    public ConsoleInput(Scanner scanner, boolean interactive) {
        this.scanner = scanner;
        this.interactive = interactive;
    }

    public void setScanner(Scanner scanner, boolean interactive) {
        this.scanner = scanner;
        this.interactive = interactive;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public boolean isInteractive() {
        return interactive;
    }

    public String readLine(String prompt) {
        if (interactive && !prompt.isEmpty()) {
            System.out.print(prompt);
        }
        return scanner.nextLine().trim();
    }

    public String readNonEmptyLine(String prompt) {
        while (true) {
            String line = readLine(prompt);
            if (!line.isEmpty()) {
                return line;
            }
            if (interactive) {
                System.out.println("Значение не может быть пустым. Повторите ввод.");
            }
        }
    }

    public Long readLong(String fieldName, boolean positiveOnly) {
        String prompt = "Введите " + fieldName + " (целое число" + (positiveOnly ? " > 0" : "") + ", пустая строка недопустима): ";
        while (true) {
            try {
                String input = readLine(interactive ? prompt : "");
                if (input.isEmpty()) {
                    if (interactive) {
                        System.out.println("Поле обязательно. Повторите ввод.");
                    }
                    continue;
                }
                input = input.replace(',', '.');
                double value = Double.parseDouble(input);
                long longValue = (long) value;
                if (value != longValue) {
                    if (interactive) {
                        System.out.println("Введите целое число.");
                    }
                    continue;
                }
                if (positiveOnly && longValue <= 0) {
                    if (interactive) {
                        System.out.println("Значение должно быть больше 0.");
                    }
                    continue;
                }
                return longValue;
            } catch (NumberFormatException e) {
                if (interactive) {
                    System.out.println("Некорректное число. Повторите ввод.");
                }
            }
        }
    }

    public int readInt(String fieldName, boolean positiveOnly) {
        String prompt = "Введите " + fieldName + " (целое число" + (positiveOnly ? " > 0" : "") + "): ";
        while (true) {
            try {
                String input = readLine(interactive ? prompt : "");
                if (input.isEmpty()) {
                    if (interactive) {
                        System.out.println("Поле обязательно. Повторите ввод.");
                    }
                    continue;
                }
                input = input.replace(',', '.');
                double value = Double.parseDouble(input);
                int intValue = (int) value;
                if (value != intValue) {
                    if (interactive) {
                        System.out.println("Введите целое число.");
                    }
                    continue;
                }
                if (positiveOnly && intValue <= 0) {
                    if (interactive) {
                        System.out.println("Значение должно быть больше 0.");
                    }
                    continue;
                }
                return intValue;
            } catch (NumberFormatException e) {
                if (interactive) {
                    System.out.println("Некорректное число. Повторите ввод.");
                }
            }
        }
    }

    public Double readDoubleNonNull(String fieldName) {
        String prompt = "Введите " + fieldName + " (число с плавающей точкой, пустая строка недопустима): ";
        while (true) {
            try {
                String input = readLine(interactive ? prompt : "");
                if (input.isEmpty()) {
                    if (interactive) {
                        System.out.println("Поле не может быть null. Повторите ввод.");
                    }
                    continue;
                }
                input = input.replace(',', '.');
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                if (interactive) {
                    System.out.println("Некорректное число. Повторите ввод.");
                }
            }
        }
    }

    public float readPositiveFloat(String fieldName) {
        String prompt = "Введите " + fieldName + " (число > 0): ";
        while (true) {
            try {
                String input = readLine(interactive ? prompt : "");
                if (input.isEmpty()) {
                    if (interactive) {
                        System.out.println("Поле обязательно. Повторите ввод.");
                    }
                    continue;
                }
                input = input.replace(',', '.');
                float value = Float.parseFloat(input);
                if (value <= 0) {
                    if (interactive) {
                        System.out.println("Значение должно быть больше 0.");
                    }
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                if (interactive) {
                    System.out.println("Некорректное число. Повторите ввод.");
                }
            }
        }
    }

    public <T extends Enum<T>> T readEnum(String fieldName, Class<T> enumClass) {
        T[] constants = enumClass.getEnumConstants();
        while (true) {
            if (interactive) {
                System.out.print("Введите " + fieldName + ". Доступные значения: ");
                for (int i = 0; i < constants.length; i++) {
                    System.out.print(constants[i]);
                    if (i < constants.length - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.print(" (можно имя или номер 1-" + constants.length + "): ");
            }
            String input = readLine("");
            if (input.isEmpty()) {
                if (interactive) {
                    System.out.println("Поле обязательно. Повторите ввод.");
                }
                continue;
            }
            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= constants.length) {
                    return constants[choice - 1];
                }
            } catch (NumberFormatException ignored) {
                // try name
            }
            try {
                return Enum.valueOf(enumClass, input.toUpperCase());
            } catch (IllegalArgumentException e) {
                if (interactive) {
                    System.out.println("Нет такой константы. Повторите ввод.");
                }
            }
        }
    }
}
