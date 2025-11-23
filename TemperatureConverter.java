import java.util.Scanner;

public class TemperatureConverter {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("----- Temperature Converter -----");
        boolean running = true;
        while (running) {
            System.out.println("1) Celsius -> Fahrenheit");
            System.out.println("2) Fahrenheit -> Celsius");
            System.out.println("3) Celsius -> Kelvin");
            System.out.println("4) Kelvin -> Celsius");
            System.out.println("0) Exit");
            System.out.print("Choice: ");
            String choice = sc.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> convertCtoF();
                    case "2" -> convertFtoC();
                    case "3" -> convertCtoK();
                    case "4" -> convertKtoC();
                    case "0" -> running = false;
                    default -> System.out.println("Invalid choice");
                }
            } catch (NumberFormatException e) {
                System.out.println("Enter Temperature: ");
            }
            System.out.println();
        }
        System.out.println("Goodbye!!!!");
        }

    private static void convertCtoF() {
        System.out.print("Enter Celcius: ");
        double c = Double.parseDouble(sc.nextLine().trim());
        double f = c * 9/5 +32;
        System.out.printf("%.2f C = %.2f F%n", c, f);
    }
    private static void convertFtoC() {
        System.out.print("Enter Fahrenheit: ");
        double f = Double.parseDouble(sc.nextLine().trim());
        double c = (f - 32) * 5/9;
        System.out.printf("%.2f F = %.2f C%n",f,c);
    }
    private static void convertCtoK() {
        System.out.print("Enter Celsius: ");
        double c = Double.parseDouble(sc.nextLine().trim());
        double k = c + 273.15;
        System.out.printf("%.2f °C = %.2f K%n", c, k);
    }
    private static void convertKtoC() {
        System.out.print("Enter Kelvin: ");
        double k = Double.parseDouble(sc.nextLine().trim());
        double c = k - 273.15;
        System.out.printf("%.2f K = %.2f °C%n", k, c);
    }
}
