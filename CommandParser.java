import java.time.LocalDate;
import java.util.Scanner;

public class CommandParser {
    private TaskManager taskManager;

    public CommandParser(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public void parseAndExecute(String commandLine) {
        Scanner scanner = new Scanner(commandLine);
        if (!scanner.hasNext()) {
            System.out.println("No command entered.");
            return;
        }

        String command = scanner.next();

        try {
            switch (command.toLowerCase()) {
                case "create":
                    scanner.useDelimiter("\""); // Use quotes to capture multi-word strings
                    scanner.next(); // Skip to the first quote
                    String name = scanner.next();
                    scanner.skip("\" due on "); // Skip to date part
                    String date = scanner.next();
                    scanner.skip(" at ");
                    String time = scanner.next();
                    scanner.skip(" description \"");
                    String description = scanner.next();
                    scanner.skip("\" categories ");
                    String categoriesText = scanner.next();
                    List<String> categories = Arrays.asList(categoriesText.split(","));
                    taskManager.createTask(name, date, time, description, categories);
                    System.out.println("Task created successfully.");
                    break;
                case "edit":
                    int taskId = Integer.parseInt(scanner.next());
                    scanner.next(); // skip 'set priority'
                    String priority = scanner.next();
                    taskManager.editTask(taskId, priority);
                    System.out.println("Task edited successfully.");
                    break;
                case "complete":
                    taskId = Integer.parseInt(scanner.next());
                    taskManager.completeTask(taskId);
                    System.out.println("Task completed successfully.");
                    break;
                case "list":
                    taskManager.listTasks();
                    break;
                case "print":
                    if ("weekly".equals(scanner.next())) {
                        LocalDate startOfWeek = LocalDate.now().with(java.time.DayOfWeek.MONDAY); // Assume week starts on Monday
                        taskManager.printWeeklyTasks(startOfWeek);
                        System.out.println("Weekly task list printed to file.");
                    }
                    break;
                case "createimage":
                    LocalDate startOfWeek = LocalDate.now().with(java.time.DayOfWeek.MONDAY); // Assume week starts on Monday
                    taskManager.createWeeklyTaskImage(startOfWeek);
                    System.out.println("Weekly task image created.");
                    break;
                default:
                    System.out.println("Unknown command.");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error processing command: " + e.getMessage());
        }
        scanner.close();
    }

    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        CommandParser parser = new CommandParser(manager);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter commands (type 'exit' to quit):");

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if ("exit".equalsIgnoreCase(input)) {
                break;
            }
            parser.parseAndExecute(input);
        }

        scanner.close();
    }
}