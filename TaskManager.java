import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class TaskManager {
    private Map<Integer, Task> tasks = new HashMap<>();
    private int nextId = 1;

    public void createTask(String name, String date, String time, String description, List<String> categories) {
        LocalDate dueDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalTime dueTime = LocalTime.parse(time, DateTimeFormatter.ISO_LOCAL_TIME);
        Task newTask = new Task(name, dueDate, dueTime, description, categories);
        tasks.put(nextId++, newTask);
    }

    public void editTask(int taskId, String priority) {
        Task task = tasks.get(taskId);
        if (task != null) {
            task.setPriority(priority);
        } else {
            System.out.println("Task ID not found.");
        }
    }

    public void completeTask(int taskId) {
        if (tasks.remove(taskId) != null) {
            System.out.println("Task completed and removed.");
        } else {
            System.out.println("Task ID not found.");
        }
    }

    public void listTasks() {
        tasks.values().forEach(System.out::println);
    }

    public void printWeeklyTasks(LocalDate startOfWeek) {
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        List<Task> weeklyTasks = tasks.values().stream()
            .filter(task -> !task.getDueDate().isBefore(startOfWeek) && !task.getDueDate().isAfter(endOfWeek))
            .collect(Collectors.toList());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("weekly_tasks.txt"))) {
            for (DayOfWeek day : DayOfWeek.values()) {
                LocalDate date = startOfWeek.plusDays(day.getValue() - 1);
                writer.write(date.toString() + "\n");
                for (Task task : weeklyTasks) {
                    if (task.getDueDate().equals(date)) {
                        writer.write(" - " + task.toString() + "\n");
                    }
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void createWeeklyTaskImage(LocalDate startOfWeek) {
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        List<Task> weeklyTasks = tasks.values().stream()
            .filter(task -> !task.getDueDate().isBefore(startOfWeek) && !task.getDueDate().isAfter(endOfWeek))
            .collect(Collectors.toList());

        int width = 800;
        int height = 600;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));

        int y = 30; // Starting Y position
        for (DayOfWeek day : DayOfWeek.values()) {
            LocalDate date = startOfWeek.plusDays(day.getValue() - 1);
            g2d.drawString(date.toString(), 10, y);
            y += 20;
            for (Task task : weeklyTasks) {
                if (task.getDueDate().equals(date)) {
                    g2d.drawString("- " + task.toString(), 10, y);
                    y += 20;
                }
            }
            y += 20; // Add extra space between days
        }

        g2d.dispose();

        try {
            ImageIO.write(image, "PNG", new File("weekly_tasks.png"));
        } catch (IOException e) {
            System.out.println("Error saving the image file: " + e.getMessage());
        }
    }
}