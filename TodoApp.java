import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TodoApp {
    private static final Scanner sc = new Scanner(System.in);
    private static final String SAVE_FILE = "todo-data.txt";
    private final List<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        TodoApp app = new TodoApp();
        app.loadFromFile();
        app.run();
    }

    private void run(){
        boolean running = true;
        System.out.println("----- To-Do List Application -----");
        while (running){
            printMenu();
            String choice = sc.nextLine().trim();
            switch (choice){
                case "1" -> addTask();
                case "2" -> markComplete();
                case "3" -> removeTask();
                case "4" -> listTasks();
                case "5" -> saveToFile();
                case "0" -> { saveToFile(); running = false; }
                default -> System.out.println("Invalid choice.");
            }
            System.out.println();
        }
        System.out.println("Goodbye!!!!!!");
    }

    private void printMenu(){
        System.out.println("1) Add task");
        System.out.println("2) Mark task complete");
        System.out.println("3) Remove task");
        System.out.println("4) Show tasks");
        System.out.println("5) Save tasks to file");
        System.out.println("0) Exit (saves Automatically)");
        System.out.print("Choice: ");
    }

    private void addTask(){
        System.out.print("Task title: ");
        String title = sc.nextLine().trim();
        if(title.isEmpty()){
            System.out.println("Title cannot be empty.");
            return;
        }
        tasks.add(new Task(title));
        System.out.println("Added task.");
    }

    private void markComplete(){
        if(tasks.isEmpty()) {
            System.out.println("No tasks to mark.");
            return;
        }
        listTasks();
        System.out.println("Enter task number to mark complete: ");
        try {
            int idx = Integer.parseInt(sc.nextLine().trim())-1;
            if(idx >= 0 && idx < tasks.size()){
                tasks.get(idx).setCompleted(true);
                System.out.println("Task marked complete.");
            }else System.out.println("Invalid number.");
        }catch (NumberFormatException e){
            System.out.println("Please enter a valid number.");
        }
    }

    private void removeTask() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks to remove.");
            return;
        }
        listTasks();
        System.out.print("Enter task number to remove: ");
        try {
            int idx = Integer.parseInt(sc.nextLine().trim()) - 1;
            if (idx >= 0 && idx < tasks.size()) {
                tasks.remove(idx);
                System.out.println("Task removed.");
            } else System.out.println("Invalid number.");
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    private void listTasks(){
        if (tasks.isEmpty()) {
            System.out.println("No tasks.");
            return;
        }
        System.out.println("Tasks:");
        int i = 1;
        for (Task t : tasks) {
            System.out.printf("%d. [%s] %s%n", i++, t.isCompleted() ? "X" : " ", t.getTitle());
        }
    }

    private void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(SAVE_FILE))) {
            for (Task t : tasks) {
                // Format: completed|title (escape newlines if needed)
                pw.println((t.isCompleted() ? "1" : "0") + "|" + t.getTitle().replace("|", "¦"));
            }
            System.out.println("Tasks saved to " + SAVE_FILE);
        } catch (IOException e) {
            System.out.println("Could not save tasks: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        File f = new File(SAVE_FILE);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|", 2);
                if (parts.length == 2) {
                    boolean done = "1".equals(parts[0]);
                    String title = parts[1].replace("¦", "|");
                    Task t = new Task(title);
                    t.setCompleted(done);
                    tasks.add(t);
                }
            }
            System.out.println("Loaded " + tasks.size() + " tasks from " + SAVE_FILE);
        } catch (IOException e) {
            System.out.println("Could not load tasks: " + e.getMessage());
        }
    }

    static class Task{
        private final String title;
        private boolean completed;

        Task(String title){
            this.title = title;
            this.completed = false;
        }
        public String getTitle(){
            return title;
        }
        public boolean isCompleted(){
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }
    }
}
