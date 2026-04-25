package ticketmanagement.commands;

import ticketmanagement.ApplicationContext;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Выполнение команд из файла; контроль глубины и рекурсии по каноническому пути.
 */
public class ExecuteScriptCommand implements Command {
    private final ApplicationContext ctx;
    private final CommandInvoker invoker;

    public ExecuteScriptCommand(ApplicationContext ctx, CommandInvoker invoker) {
        this.ctx = ctx;
        this.invoker = invoker;
    }

    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public String getDescription() {
        return "выполнить скрипт (аргумент: имя файла)";
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Использование: execute_script <file>");
            return;
        }
        String scriptFileName = args[0];
        File scriptFile = new File(scriptFileName);
        if (!scriptFile.exists()) {
            System.out.println("Файл не найден: " + scriptFileName);
            return;
        }
        final String canonicalPath;
        try {
            canonicalPath = scriptFile.getCanonicalPath();
        } catch (IOException e) {
            System.out.println("Ошибка пути: " + e.getMessage());
            return;
        }
        if (ctx.isMaxScriptDepth()) {
            System.out.println("Превышена глубина вложенности скриптов (макс. " + ctx.maxScriptDepth() + ")");
            return;
        }
        if (ctx.isScriptActive(canonicalPath)) {
            System.out.println("Рекурсивный вызов скрипта: " + scriptFileName);
            return;
        }
        boolean scriptEntered = false;
        try (Scanner fileScanner = new Scanner(scriptFile, StandardCharsets.UTF_8)) {
            ctx.pushScript(canonicalPath);
            scriptEntered = true;
            ctx.pushScriptInput(fileScanner);
            int lineNumber = 0;
            int commandCount = 0;
            while (fileScanner.hasNextLine()) {
                lineNumber++;
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                commandCount++;
                if (ctx.consoleInput().isInteractive()) {
                    System.out.printf("[%d] Выполнение: %s%n", lineNumber, line);
                }
                invoker.executeLine(line);
            }
            if (ctx.consoleInput().isInteractive()) {
                System.out.printf("Скрипт завершён. Команд: %d%n", commandCount);
            }
        } catch (Exception e) {
            System.out.println("Ошибка скрипта: " + e.getMessage());
        } finally {
            ctx.popScriptInput();
            if (scriptEntered) {
                ctx.popScript(canonicalPath);
            }
        }
    }
}
