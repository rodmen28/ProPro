import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Task {
    private String name;
    private String description;
    private LocalDate dueDate;
    private LocalTime dueTime;
    private String priority;
    private List<String> categories;

    public Task(String name, LocalDate dueDate, LocalTime dueTime, String description, List<String> categories) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.priority = "low"; // Default priority
        this.categories = new ArrayList<>(categories);
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalTime getDueTime() {
        return dueTime;
    }

    public void setDueTime(LocalTime dueTime) {
        this.dueTime = dueTime;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Task{" +
               "name='" + name + '\'' +
               ", description='" + description + '\'' +
               ", dueDate=" + dueDate +
               ", dueTime=" + dueTime +
               ", priority='" + priority + '\'' +
               ", categories=" + categories +
               '}';
    }
}
