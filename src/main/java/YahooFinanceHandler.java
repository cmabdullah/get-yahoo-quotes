import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class YahooFinanceHandler {

    public static void main(String[] args) {

        final long start = System.nanoTime();

        Map<String, Integer> stocks = new HashMap<>();
        stocks.put("GOOG",5);
        stocks.put("CSCO",8);
        stocks.put("FB",6);
        stocks.put("AAPL",12);
        stocks.put("AMZN",11);
        stocks.put("NFLX",2);
        stocks.put("TSLA",7);
        double netAssetValue = 0.0;
        for (String ticker : stocks.keySet()) {
            try {
                netAssetValue += stocks.get(ticker) * YahooFinance.getPrice(ticker);
            } catch (Exception e) {
                //e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }

        final long end = System.nanoTime();
        final String value = new DecimalFormat("$##,##0.00").format(netAssetValue);
        System.out.println("Your net asset value is " + value);
        System.out.println("Time (seconds) taken " + (end - start) / 1.0e9);
    }
}
