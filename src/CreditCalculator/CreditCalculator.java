import static java.lang.Math.*;

public class CreditCalculator {
    public static void main(String[] args) {
        CalculatorInput input = new CalculatorInput();
        input.readParameters();

        String validationError = input.validate();
        if (validationError != null) {
            System.out.println("Incorrect parameters");
            System.out.println("Reason: " + validationError);
            System.out.println("Example valid runs:");
            System.out.println("  java -Dtype=diff -Dprincipal=1000000 -Dperiods=10 -Dinterest=10 CreditCalculator");
            System.out.println("  java -Dtype=annuity -Dprincipal=1000000 -Dperiods=60 -Dinterest=10 CreditCalculator");
            System.out.println("  java -Dtype=annuity -Dpayment=8722 -Dperiods=120 -Dinterest=5.6 CreditCalculator");
            return;
        }

        CalculatorLogic logic = new CalculatorLogic();
        logic.calculate(input);
    }
}

class CalculatorInput {
    String type;
    Double principal;
    Double payment;
    Integer periods;
    Double interest;

    void readParameters() {
        try {
            type = System.getProperty("type");
            String sPrincipal = System.getProperty("principal");
            String sPayment = System.getProperty("payment");
            String sPeriods = System.getProperty("periods");
            String sInterest = System.getProperty("interest");

            if (sPrincipal != null) principal = Double.parseDouble(sPrincipal);
            if (sPayment != null) payment = Double.parseDouble(sPayment);
            if (sPeriods != null) periods = Integer.parseInt(sPeriods);
            if (sInterest != null) interest = Double.parseDouble(sInterest);
        } catch (NumberFormatException ex) {
        }
    }

    String validate() {
        if (type == null) return "Parameter --type is missing (use -Dtype=annuity or -Dtype=diff).";
        if (!type.equals("diff") && !type.equals("annuity"))
            return "Unknown type: must be 'annuity' or 'diff'.";

        if (interest == null) return "Parameter --interest is missing.";
        if (interest <= 0) return "--interest must be a positive number.";

        if (principal != null && principal < 0) return "--principal must be non-negative.";
        if (payment != null && payment < 0) return "--payment must be non-negative.";
        if (periods != null && periods <= 0) return "--periods must be a positive integer.";

        if (type.equals("diff")) {
            if (payment != null) return "--payment should not be provided for diff payments.";
            if (principal == null) return "--principal is required for diff.";
            if (periods == null) return "--periods is required for diff.";
            return null;
        }

        int known = 0;
        if (principal != null) known++;
        if (payment != null) known++;
        if (periods != null) known++;
        if (known < 2) {
            return "For annuity you must provide at least two of the following: --principal, --payment, --periods (plus --interest).";
        }

        return null;
    }
}

class CalculatorLogic {

    void calculate(CalculatorInput input) {
        double i = input.interest / 1200.0;

        if (input.type.equals("diff")) {
            calculateDifferentiated(input.principal, input.periods, i);
        } else {
            if (input.payment == null) {
                calculateAnnuityPayment(input.principal, input.periods, i);
            } else if (input.principal == null) {
                calculatePrincipal(input.payment, input.periods, i);
            } else if (input.periods == null) {
                calculatePeriods(input.principal, input.payment, i);
            } else {
                System.out.println("Incorrect parameters");
            }
        }
    }

    private void calculateDifferentiated(double P, int n, double i) {
        double total = 0.0;
        for (int m = 1; m <= n; m++) {
            double Dm = (P / n) + i * (P - (P * (m - 1) / n));
            long pay = (long) ceil(Dm);
            System.out.println("Month " + m + ": payment is " + pay);
            total += pay;
        }
        long over = (long) round(total - P);
        System.out.println("Overpayment = " + over);
    }

    private void calculateAnnuityPayment(double P, int n, double i) {
        if (P <= 0 || n <= 0) {
            System.out.println("Incorrect parameters: principal and periods must be positive for annuity payment calculation.");
            return;
        }
        double pow = pow(1 + i, n);
        double A = P * (i * pow) / (pow - 1);
        long payment = (long) ceil(A);
        System.out.println("Your annuity payment = " + payment + "!");
        printOverpayment(P, payment, n);
    }

    private void calculatePrincipal(double A, int n, double i) {
        if (A <= 0 || n <= 0) {
            System.out.println("Incorrect parameters: payment and periods must be positive for principal calculation.");
            return;
        }
        double pow = pow(1 + i, n);
        double denom = (i * pow) / (pow - 1);
        if (denom == 0) {
            System.out.println("Calculation error (denominator zero). Check parameters.");
            return;
        }
        double P = A / denom;
        long principal = (long) round(P);
        System.out.println("Your loan principal = " + principal + "!");
        printOverpayment(principal, (long) round(A), n);
    }

    private void calculatePeriods(double P, double A, double i) {
        if (P <= 0 || A <= 0) {
            System.out.println("Incorrect parameters: principal and payment must be positive for periods calculation.");
            return;
        }
        double denom = A - i * P;
        if (denom <= 0) {
            System.out.println("Error: The monthly payment is too small to ever repay the loan.");
            return;
        }
        double nDouble = log(A / denom) / log(1 + i);
        int n = (int) ceil(nDouble);
        int years = n / 12;
        int months = n % 12;
        StringBuilder sb = new StringBuilder("It will take ");
        if (years > 0) sb.append(years).append(years == 1 ? " year" : " years");
        if (months > 0) {
            if (years > 0) sb.append(" and ");
            sb.append(months).append(months == 1 ? " month" : " months");
        }
        sb.append(" to repay this loan!");
        System.out.println(sb.toString());
        printOverpayment(P, (long) round(A), n);
    }

    private void printOverpayment(double principal, long monthlyPayment, int periods) {
        long overpayment = (long) round(monthlyPayment * (double) periods - principal);
        System.out.println("Overpayment = " + overpayment);
    }
}
