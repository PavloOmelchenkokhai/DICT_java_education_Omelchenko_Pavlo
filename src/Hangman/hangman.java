package Hangman;

import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;

public class hangman {
    public static void main(String[] args) {
        String[] wordList = {"python", "java", "javascript", "kotlin"};
        Random rand = new Random();

        int randomIndex = rand.nextInt(wordList.length);
        final String SECRET_WORD = wordList[randomIndex];
        final int WORD_LENGTH = SECRET_WORD.length();

        int attempts = 8;
        Scanner scanner = new Scanner(System.in);

        char[] currentMask = new char[WORD_LENGTH];
        Arrays.fill(currentMask, '-');

        if (WORD_LENGTH >= 2) {
            currentMask[0] = SECRET_WORD.charAt(0);
            currentMask[1] = SECRET_WORD.charAt(1);
        }

        System.out.println("====== HANGMAN ======");
        System.out.println("The secret word has " + WORD_LENGTH + " letters.");

        while (attempts > 0 && String.valueOf(currentMask).contains("-")) {

            System.out.println(String.valueOf(currentMask));
            System.out.println("Attempts left: " + attempts);

            System.out.print("Input a letter: > ");
            String input = scanner.nextLine().toLowerCase();

            if (input.length() != 1) {
                System.out.println("Please enter only one letter!");
                continue;
            }

            char guessLetter = input.charAt(0);
            boolean isCorrectGuess = false;

            for (int i = 0; i < WORD_LENGTH; i++) {
                if (SECRET_WORD.charAt(i) == guessLetter) {
                    if (currentMask[i] != guessLetter) {
                        currentMask[i] = guessLetter;
                        isCorrectGuess = true;
                    } else {
                        isCorrectGuess = true;
                    }
                }
            }

            if (!isCorrectGuess) {
                attempts--;
                System.out.println("That letter doesn't appear in the word.");
            }
            System.out.println("----------------------------------");
        }

        System.out.println("The word was: " + SECRET_WORD);

        if (!String.valueOf(currentMask).contains("-")) {
            System.out.println("You survived!");
        } else {
            System.out.println("You lost!");
        }

        scanner.close();
    }
}