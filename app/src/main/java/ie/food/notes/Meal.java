package ie.food.notes;


public class Meal {
    private String title;
    private String description;
    private int priority;

    public Meal() {
        //empty constructor needed
    }

    public Meal(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }
}