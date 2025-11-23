import java.util.Scanner;

public class CalculatorApp {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("------ Simple Calculator -------");
        boolean running = true;

        while (running) {
            printMenu();
            try {
                int choice = Integer.parseInt(sc.nextLine().trim());

                switch (choice) {
                    case 1 -> handleBinaryOperation("Addition", (a, b) -> a + b);
                    case 2 -> handleBinaryOperation("Subtraction", (a, b) -> a - b);
                    case 3 -> handleBinaryOperation("Multiplication", (a, b) -> a * b);
                    case 4 -> handleBinaryOperation("Division", (a, b) -> {
                        if (b == 0) throw new ArithmeticException("Division by zero is not allowed.");
                        return a / b;
                    });
                    case 5 -> handleUnaryOperation("Square Root", Math::sqrt);
                    case 6 -> handleBinaryOperation("Power (a ^ b)", Math::pow);
                    case 0 -> {
                        running = false;
                        System.out.println("Exiting... Goodbye!");
                    }
                    default -> System.out.println("Invalid choice. Please select a valid option.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid numeric choice.");
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }

            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("Choose Operation:");
        System.out.println("1) Addition (a + b)");
        System.out.println("2) Subtraction (a - b)");
        System.out.println("3) Multiplication (a * b)");
        System.out.println("4) Division (a / b)");
        System.out.println("5) Square Root (sqrt(a))");
        System.out.println("6) Power (a ^ b)");
        System.out.println("0) Exit");
        System.out.print("Enter choice: ");
    }

    private static void handleBinaryOperation(String name, BinaryOp op) {
        try {
            System.out.print("Enter first number: ");
            double a = Double.parseDouble(sc.nextLine().trim());

            System.out.print("Enter second number: ");
            double b = Double.parseDouble(sc.nextLine().trim());

            double result = op.apply(a, b);
            System.out.printf("%s result: %s%n", name, formatDouble(result));

        } catch (NumberFormatException e) {
            System.out.println("Invalid number input. Please enter valid numeric values.");
        } catch (ArithmeticException e) {
            System.out.println("Math error: " + e.getMessage());
        }
    }

    private static void handleUnaryOperation(String name, UnaryOp op) {
        try {
            System.out.print("Enter number: ");
            double a = Double.parseDouble(sc.nextLine().trim());

            double result = op.apply(a);
            System.out.printf("%s result: %s%n", name, formatDouble(result));

        } catch (NumberFormatException e) {
            System.out.println("Invalid number input.");
        }
    }

    private static String formatDouble(double v) {
        if (v == (long) v) return String.format("%d", (long) v);
        return String.format("%.6f", v).replaceAll("0+$", "").replaceAll("\\.$", "");
    }

    @FunctionalInterface
    interface BinaryOp { double apply(double a, double b); }

    @FunctionalInterface
    interface UnaryOp { double apply(double a); }
}
