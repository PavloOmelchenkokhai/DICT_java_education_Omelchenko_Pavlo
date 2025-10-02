package Hangman;

import java.util.*;

public class hangman {
    public static void main(String[] args) {
        String[] wordList = {"python", "java", "javascript", "kotlin"};
        Random rand = new Random();

        int randomIndex = rand.nextInt(wordList.length);
        final String SECRET_WORD = wordList[randomIndex];
        final int WORD_LENGTH = SECRET_WORD.length();

        int attempts = 8;
        Scanner scanner = new Scanner(System.in);
        Set<Character> guessedLetters = new HashSet<>();

        char[] currentMask = new char[WORD_LENGTH];
        Arrays.fill(currentMask, '-');

        if (WORD_LENGTH >= 2) {
            char firstLetter = SECRET_WORD.charAt(0);
            char secondLetter = SECRET_WORD.charAt(1);

            currentMask[0] = firstLetter;
            currentMask[1] = secondLetter;

            guessedLetters.add(firstLetter);
            guessedLetters.add(secondLetter);
        }

        System.out.println("====== HANGMAN ======");
        System.out.println("The secret word has " + WORD_LENGTH + " letters.");

        while (attempts > 0 && String.valueOf(currentMask).contains("-")) {

            System.out.println(String.valueOf(currentMask));
            System.out.println("Attempts left: " + attempts);

            System.out.print("Input a letter: > ");
            String input = scanner.nextLine();

            if (input.length() != 1) {
                System.out.println("You should input a single letter.");
                System.out.println("----------------------------------");
                continue;
            }

            char guessLetter = input.charAt(0);

            if (!Character.isLowerCase(guessLetter) || !Character.isLetter(guessLetter)) {
                System.out.println("Please enter a lowercase English letter.");
                System.out.println("----------------------------------");
                continue;
            }

            if (guessedLetters.contains(guessLetter)) {
                System.out.println("You've already guessed this letter.");
                System.out.println("----------------------------------");
                continue;
            }

            guessedLetters.add(guessLetter);

            boolean letterInWord = false;
            boolean improved = false;

            for (int i = 0; i < WORD_LENGTH; i++) {
                if (SECRET_WORD.charAt(i) == guessLetter) {
                    letterInWord = true;

                    if (currentMask[i] == '-') {
                        currentMask[i] = guessLetter;
                        improved = true;
                    }
                }
            }

            if (letterInWord) {
                if (!improved) {
                    System.out.println("No improvements (Should be caught by 'already guessed').");
                }
            } else {
                System.out.println("That letter doesn't appear in the word");
                attempts--;
            }

            System.out.println("----------------------------------");
        }

        System.out.println(String.valueOf(currentMask));
        System.out.println("The word was: " + SECRET_WORD);

        if (!String.valueOf(currentMask).contains("-")) {
            System.out.println("You guessed the word " + SECRET_WORD + "!");
            System.out.println("You survived!");
        } else {
            System.out.println("You lost!");
        }

        scanner.close();
    }
}