import java.util.*;

public class CreditCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the loan principal:");
        long principal = scanner.nextLong();

        System.out.println("What do you want to calculate?");
        System.out.println("type \"m\" – for number of monthly payments,");
        System.out.println("type \"p\" – for the monthly payment:");
        String calculationType = scanner.next();

        System.out.println("---");

        if (calculationType.equals("p")) {

            System.out.println("Enter the number of months:");
            int months = scanner.nextInt();

            calculatePayment(principal, months);

        } else if (calculationType.equals("m")) {
            System.out.println("Enter the monthly payment:");
            long monthlyPayment = scanner.nextLong();

            calculateMonths(principal, monthlyPayment);

        } else {
            System.out.println("Invalid input for calculation type. Please use 'm' or 'p'.");
        }

        scanner.close();
    }

    private static void calculateMonths(long principal, long monthlyPayment) {
        double numMonthsDouble = Math.ceil((double) principal / monthlyPayment);

        long numMonths = (long) numMonthsDouble;

        System.out.println("It will take " + numMonths + (numMonths == 1 ? " month" : " months") + " to repay the loan");
    }

    private static void calculatePayment(long principal, int months) {
        double regularPaymentDouble = (double) principal / months;

        long regularPayment = (long) Math.ceil(regularPaymentDouble);

        long lastPayment = principal - (months - 1) * regularPayment;

        if (regularPayment == lastPayment) {
            System.out.println("Your monthly payment = " + regularPayment);
        } else {
            System.out.println("Your monthly payment = " + regularPayment + " and the last payment = " + lastPayment + ".");
        }
    }
}