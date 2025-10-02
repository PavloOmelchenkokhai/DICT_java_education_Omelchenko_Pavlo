package Hangman;
import java.util.Scanner;
import java.util.Random;

public class hangman {
    public static void main(String[] args) {
        String[] wordList = {"python", "java", "javascript","kotlin"};
        Random rand = new Random();

        int randomIndex = rand.nextInt(wordList.length);

        final String SECRET_WORD = wordList[randomIndex];

        Scanner scanner = new Scanner(System.in);
        System.out.println("HANGMAN");
        System.out.print("Guess the word: ");

        String guess = scanner.nextLine().toLowerCase();

        if (guess.equals(SECRET_WORD)) {
            System.out.println("You survived!");
        } else  {
            System.out.println("You lost!");
        }

        scanner.close();
    }
}
