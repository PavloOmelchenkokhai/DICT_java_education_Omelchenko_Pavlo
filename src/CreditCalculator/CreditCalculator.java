import java.util.*;
import static java.lang.Math.*;

public class CreditCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("What do you want to calculate?");
        System.out.println("type \"n\" for number of monthly payments,");
        System.out.println("type \"a\" for annuity monthly payment amount,");
        System.out.println("type \"p\" for loan principal:");
        String calculationType = scanner.next();

        double principal = -1.0;
        double payment = -1.0;
        int periods = -1;
        double interestPercent = -1.0;

        if (!calculationType.equals("p")) {
            System.out.println("Enter the loan principal:");
            principal = scanner.nextDouble();
        }

        if (!calculationType.equals("a")) {
            System.out.println("Enter the annuity payment:");
            payment = scanner.nextDouble();
        }

        if (!calculationType.equals("n")) {
            System.out.println("Enter the number of periods:");
            periods = scanner.nextInt();
        }

        System.out.println("Enter the loan interest:");
        interestPercent = scanner.nextDouble();

        if (interestPercent <= 0) {
            System.out.println("Incorrect interest rate. Interest must be a positive number.");
            scanner.close();
            return;
        }

        double i = interestPercent / 1200.0;

        if (calculationType.equals("a")) {
            calculateAnnuityPayment(principal, periods, i);
        } else if (calculationType.equals("p")) {
            calculatePrincipal(payment, periods, i);
        } else if (calculationType.equals("n")) {
            calculatePeriods(principal, payment, i);
        } else {
            System.out.println("Invalid calculation type. Please use 'n', 'a', or 'p'.");
        }

        scanner.close();
    }

    private static void calculateAnnuityPayment(double P, int n, double i) {
        // A = P * (i * (1 + i)^n) / ((1 + i)^n - 1)
        double powerTerm = pow(1 + i, n);

        double A = P * (i * powerTerm) / (powerTerm - 1);

        long monthlyPayment = (long) ceil(A);

        System.out.println("Your monthly payment = " + monthlyPayment + "!");

        calculateOverpayment(P, monthlyPayment, n);
    }

    private static void calculatePrincipal(double A, int n, double i) {
        // P = A / ((i * (1 + i)^n) / ((1 + i)^n - 1))
        double powerTerm = pow(1 + i, n);

        double annuityFactor = (i * powerTerm) / (powerTerm - 1);

        double P = A / annuityFactor;

        long principal = (long) round(P);

        System.out.println("Your loan principal = " + principal + "!");

        calculateOverpayment(principal, (long) round(A), n);
    }

    private static void calculatePeriods(double P, double A, double i) {

        double logArgument = A / (A - i * P);

        if (logArgument <= 1.0) {
            System.out.println("Error: The monthly payment is too small to ever repay the loan.");
            return;
        }

        double nDouble = log(logArgument) / log(1 + i);

        int n = (int) ceil(nDouble);

        int years = n / 12;
        int months = n % 12;

        String result = "It will take ";
        if (years > 0) {
            result += years + (years == 1 ? " year" : " years");
            if (months > 0) {
                result += " and ";
            }
        }
        if (months > 0) {
            result += months + (months == 1 ? " month" : " months");
        }

        if (result.equals("It will take ")) {
            result += "less than a month";
        }

        System.out.println(result + " to repay this loan!");

        calculateOverpayment((long) round(P), (long) round(A), n);
    }

    private static void calculateOverpayment(double principal, long regularPayment, int periods) {
        double totalPaid = (double) regularPayment * periods;
        long overpayment = (long) round(totalPaid - principal);

        System.out.println("Overpayment = " + overpayment);
    }
}