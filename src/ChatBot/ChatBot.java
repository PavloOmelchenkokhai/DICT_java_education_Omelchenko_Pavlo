package ChatBot;
import java.util.Scanner;

public class ChatBot {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello! My name is MyChatBot");
        System.out.println("I was created in 2025");
        System.out.println("Please, remind me your name.");
        String userName = scanner.nextLine();
        System.out.println("What a great name you have, " + userName +"!");
        System.out.println("Let me guess your age.");
        System.out.println("Enter remainders of dividing your age by 3, 5 and 7.");

        int remainder3 = scanner.nextInt();
        int remainder5 = scanner.nextInt();
        int remainder7 = scanner.nextInt();

        int yourAge = (remainder3 * 70 + remainder5 * 21 + remainder7 * 15) % 105;

        System.out.println("Your age is " + yourAge + "; that's a good time to start programming!");
        System.out.println("Now I will prove to you that I can count to any number you want!");

        int numberToCount = scanner.nextInt();

        for (int i = 0; i <= numberToCount; i++) {
            System.out.println(i + "!");
        }

        System.out.println("Let's test your programming knowledge.");

        final int CORRECT_ANSWER = 2; // Правильна відповідь - варіант 2
        int userAnswer;

        // Цикл do-while для повторення тесту до правильної відповіді
        do {
            System.out.println("Which method is used to read an integer from the console in Java?");
            System.out.println("1. nextLine()");
            System.out.println("2. nextInt()");
            System.out.println("3. next()");
            System.out.println("4. readInteger()");

            userAnswer = scanner.nextInt();

            if (userAnswer != CORRECT_ANSWER) {
                System.out.println("Please, try again.");
            }
        } while (userAnswer != CORRECT_ANSWER);

        System.out.println("Congratulations, that's correct!");

        scanner.close();
        System.out.println("Goodbye, have a nice day!");
    }
}
