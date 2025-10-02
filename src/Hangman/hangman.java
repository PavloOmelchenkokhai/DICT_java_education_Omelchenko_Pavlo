package Hangman;
import java.util.Scanner;
import java.util.Random;

public class hangman {
    public static void main(String[] args) {
        String[] wordList = {"python", "java", "javascript","kotlin"};
        Random rand = new Random();

        int randomIndex = rand.nextInt(wordList.length);

        final String SECRET_WORD = wordList[randomIndex];

        String hint = "";

        if (SECRET_WORD.length() >= 2) {
            hint = SECRET_WORD.substring(0, 2);

            for (int i = 2; i < SECRET_WORD.length(); i++) {
                hint += "-";
            }
        } else {
            hint = SECRET_WORD;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("HANGMAN");

        System.out.print("Guess the word " + hint + ": ");

        String guess = scanner.nextLine().toLowerCase();

        if (guess.equals(SECRET_WORD)) {
            System.out.println("You survived!");
        } else  {
            System.out.println("You lost!");
        }

        scanner.close();
    }
}