package Hangman;

import java.util.*;

public class hangman {

    private static final String[] WORD_LIST = {"python", "java", "javascript", "kotlin"};
    private static final Random RAND = new Random();
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("====== HANGMAN ======");

        while (true) {
            System.out.print("Type \"play\" to play the game, \"exit\" to quit: > ");
            String command = SCANNER.nextLine().toLowerCase();

            if (command.equals("play")) {
                playGame();
            } else if (command.equals("exit")) {
                System.out.println("Thanks for playing! Goodbye.");
                break;
            } else {
                continue;
            }
        }

        SCANNER.close();
    }

    private static void playGame() {
        int randomIndex = RAND.nextInt(WORD_LIST.length);
        final String SECRET_WORD = WORD_LIST[randomIndex];
        final int WORD_LENGTH = SECRET_WORD.length();

        int attempts = 8;
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

        System.out.println("----------------------------------");
        System.out.println("New game started! (" + WORD_LENGTH + " letters)");
        System.out.println("----------------------------------");

        while (attempts > 0 && String.valueOf(currentMask).contains("-")) {

            System.out.println(String.valueOf(currentMask));
            System.out.println("Attempts left: " + attempts);

            System.out.print("Input a letter: > ");
            String input = SCANNER.nextLine();

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
                    System.out.println("No improvements.");
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
        System.out.println("----------------------------------");
    }
}