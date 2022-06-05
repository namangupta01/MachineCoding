import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class StockExchangeClient {
    public static void startExchange() throws FileNotFoundException, ParseException {
        Scanner scanner = new Scanner(new File("./input/input1.txt"));
        StockExchange stockExchange = StockExchange.getStockExchange();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            input = input.replace("  ", " ");
            String[] inputArray = input.trim().split(" ");
            String orderId = inputArray[0];
            // Storing time string as Day Epoch Time Long
            Long time = sdf.parse(inputArray[1]).getTime();
            String symbol = inputArray[2];
            String orderType = inputArray[3];
            Double price = Double.parseDouble(inputArray[4]);
            Integer quantity = Integer.parseInt(inputArray[5]);
            if(orderType.equals("buy")) {
                stockExchange.createBuyOrder(orderId, time, symbol, price, quantity);
            } else {
                stockExchange.createSellOrder(orderId, time, symbol, price, quantity);
            }
        }

    }
}
