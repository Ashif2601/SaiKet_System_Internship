import java.util.Random;
import java.util.Scanner;

public class NumberGuessGame {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args){
        System.out.println("----- Number Guessing Game -----");
        Random rnd = new Random();
        int lower = 1, upper = 100;
        int target = rnd.nextInt(upper - lower + 1) + lower;
        int attempts = 0;
        boolean won = false;

        System.out.println("I have chosen a number between " + lower + " and " + upper + ". Try to guess it!");

        while(!won){
            System.out.print("Enter your guess (or 'q' to quit): ");
            String line = sc.nextLine().trim();

            if(line.equalsIgnoreCase("q")){
                System.out.println("You quit. The number was " + target);
                break;
            }

            try{
                int guess = Integer.parseInt(line);
                attempts++;

                if(guess == target){
                    System.out.println("Correct! You guessed in " + attempts + " attempts.");
                    won = true;
                } else if(guess < target){
                    System.out.println("Too low.");
                } else {
                    System.out.println("Too high.");
                }
            } catch (NumberFormatException e){
                System.out.println("Please enter a valid integer or 'q'.");
            }
        }

        if(won){
            if(attempts <= 5)
                System.out.println("Excellent guessing!");
            else if(attempts <= 10)
                System.out.println("Good Job!");
            else
                System.out.println("You got it - well done!");
        }
    }
}
