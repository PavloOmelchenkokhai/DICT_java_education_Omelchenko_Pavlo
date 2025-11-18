package CurrencyExchange;
import java.util.*;

public class CurrencyExchange {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please, enter the number of mycoins you have: ");
        double mycoins = scanner.nextDouble();

        System.out.print("Please, enter the exchange rate: ");
        double rate = scanner.nextDouble();

        double dollars = mycoins * rate;

        System.out.println("The total amount of dollars: " + dollars);
    }
}

