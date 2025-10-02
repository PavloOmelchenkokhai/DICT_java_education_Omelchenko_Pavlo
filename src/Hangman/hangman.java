package Hangman;
import java.util.Scanner;

public class hangman {
    public static void main(String[] args) {
        final String secret_word = "java";

        Scanner scanner = new Scanner(System.in);
        System.out.println("HANGMAN");
        System.out.println("Guess the word:");
        String guess = scanner.nextLine();

        if (guess.equals(secret_word)) {
            System.out.println("You survived!");
        } else  {
            System.out.println("You lost!");
        }
    }
}
