package CurrencyExchange;
import java.util.*;

public class CurrencyExchange {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double ARS = 0.82;      // аргентинське песо
        double HNL = 0.17;      // гондураська лемпіра
        double AUD = 1.9622;    // австралійський долар
        double MAD = 0.208;     // марокканський дирхам

        double mycoins = scanner.nextDouble();

        double resultARS = mycoins * ARS;
        double resultHNL = mycoins * HNL;
        double resultAUD = mycoins * AUD;
        double resultMAD = mycoins * MAD;

        System.out.printf("I will get %.2f ARS from the sale of %.1f mycoins.\n", resultARS, mycoins);
        System.out.printf("I will get %.2f HNL from the sale of %.1f mycoins.\n", resultHNL, mycoins);
        System.out.printf("I will get %.2f AUD from the sale of %.1f mycoins.\n", resultAUD, mycoins);
        System.out.printf("I will get %.2f MAD from the sale of %.1f mycoins.\n", resultMAD, mycoins);
    }
}
