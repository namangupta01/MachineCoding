import java.util.*;

public class StockExchange {
    public static StockExchange stockExchange;

    private final HashMap<String, Stock> stockSymbolToStockMap = new HashMap<>();

    /*
    stockToSellOrderMap is a map where key is a stock Object and Value is a Tree Map of Price and Orders Set.
    HashMap has a lookup time of O(n) and TreeMap has a lookup time of O(log n).
    So, This will help in finding all the sell Orders for a particular stock at a particular price with the lookup time of O(log n) where n is the different selling prices count of sell orders
*/
    private final HashMap<Stock, TreeMap<Double, TreeSet<Order>>> stockToSellOrdersMap = new HashMap<>();
    private final HashMap<Stock, TreeMap<Double, TreeSet<Order>>> stockToBuyOrdersMap = new HashMap<>();
    private final OrderBook orderBook;
    private final ArrayList<Transaction> transactions;

    private StockExchange() {
        this.transactions = new ArrayList<>();
        this.orderBook = new OrderBook();
    };

    public static StockExchange getStockExchange() {
        if(stockExchange == null) stockExchange = new StockExchange();
        return stockExchange;
    }

    public Order createSellOrder(String orderId, Long time, String stockSymbol, Double price, Integer quantity) {
        Order newOrder = new Order(orderId, time, false, quantity, price, getSymbolStock(stockSymbol));
        orderBook.addOrder(newOrder);
        executeSellOrder(newOrder);
        return newOrder;
    }

    public Order createBuyOrder(String orderId, Long time, String stockSymbol, Double price, Integer quantity) {
        Order newOrder = new Order(orderId, time, true, quantity, price, getSymbolStock(stockSymbol));
        orderBook.addOrder(newOrder);
        executeBuyOrder(newOrder);
        return newOrder;
    }

    // Private Methods

    private Stock getSymbolStock(String stockSymbol) {
        Stock stock = stockSymbolToStockMap.get(stockSymbol);

        if(stock == null) {
            stock = new Stock(stockSymbol);
            stockSymbolToStockMap.put(stockSymbol, stock);
        }
        return stock;
    }

    private void addOrderToStockToOrdersMap(Order order) {
        TreeMap<Double, TreeSet<Order>> priceToOrdersMap;

        if(order.getBuyOrder()) {
            priceToOrdersMap = stockToBuyOrdersMap.get(order.getStock());
        } else {
            priceToOrdersMap = stockToSellOrdersMap.get(order.getStock());
        }

        if(priceToOrdersMap == null) priceToOrdersMap = new TreeMap<>();

        TreeSet<Order> orderTreeSet = priceToOrdersMap.getOrDefault(order.getPrice(), new TreeSet<>(Comparator.comparingLong(Order::getTime)));

        orderTreeSet.add(order);
        priceToOrdersMap.put(order.getPrice(), orderTreeSet);

        if(order.getBuyOrder()) {
            stockToBuyOrdersMap.put(order.getStock(), priceToOrdersMap);
        } else {
            stockToSellOrdersMap.put(order.getStock(), priceToOrdersMap);
        }
    }

    private void executeBuyOrder(Order buyOrder) {
        TreeMap<Double, TreeSet<Order>> stockPriceToOrdersMap = stockToSellOrdersMap.get(buyOrder.getStock());

        // If no sell orders are there just add buyOrder to map and no need to check further in finding suitable sell orders.
        if(stockPriceToOrdersMap == null || stockPriceToOrdersMap.size() == 0) {
            addOrderToStockToOrdersMap(buyOrder);
            return;
        }

        Map.Entry<Double, TreeSet<Order>> sellOrdersEntry = stockPriceToOrdersMap.firstEntry();
        ArrayList<Transaction> transactionsList = new ArrayList<>();

        while(sellOrdersEntry != null && sellOrdersEntry.getKey() <= buyOrder.getPrice() && buyOrder.getPendingQuantity() > 0) {

            Double sellPrice = sellOrdersEntry.getKey();
            TreeSet<Order> sellOrders = sellOrdersEntry.getValue();

            while(buyOrder.getPendingQuantity() > 0 && sellOrders.size() > 0) {
                Order sellOrder = sellOrders.first();

                if(sellOrder == null) break;

                int quantityDelta = buyOrder.getPendingQuantity() - sellOrder.getPendingQuantity();

                if(quantityDelta == 0) {
                    transactionsList.add(new Transaction(buyOrder, sellOrder, buyOrder.getPendingQuantity(), sellOrder.getPrice(), buyOrder.getStock()));
                    sellOrder.setPendingQuantity(0);
                    buyOrder.setPendingQuantity(0);
                    sellOrders.remove(sellOrder);
                    break;
                } else if (quantityDelta > 0) {
                    transactionsList.add(new Transaction(buyOrder, sellOrder, sellOrder.getPendingQuantity(), sellOrder.getPrice(), buyOrder.getStock()));
                    sellOrder.setPendingQuantity(0);
                    buyOrder.setPendingQuantity(quantityDelta);
                    sellOrders.remove(sellOrder);
                } else {
                    transactionsList.add(new Transaction(buyOrder, sellOrder, buyOrder.getPendingQuantity(), sellOrder.getPrice(), buyOrder.getStock()));
                    buyOrder.setPendingQuantity(0);
                    sellOrder.setPendingQuantity(-(quantityDelta));
                    break;
                }
            }

            if(sellOrders.size() == 0) stockPriceToOrdersMap.remove(sellPrice);
            sellOrdersEntry = stockPriceToOrdersMap.firstEntry();
        }
        if(buyOrder.getPendingQuantity() > 0) addOrderToStockToOrdersMap(buyOrder);
        for (Transaction transaction : transactionsList) {
            System.out.println(transaction.getBuyOrder().getOrderId() + " " + String.format("%.2f", transaction.getPrice()) + " " + transaction.getQuantity() + " " + transaction.getSellOrder().getOrderId());
        }
        transactions.addAll(transactionsList);
    }

    private void executeSellOrder(Order sellOrder) {
        TreeMap<Double, TreeSet<Order>> stockPriceToOrdersMap = stockToBuyOrdersMap.get(sellOrder.getStock());
        if(stockPriceToOrdersMap == null || stockPriceToOrdersMap.size() == 0) {
            addOrderToStockToOrdersMap(sellOrder);
            return;
        }

        Map.Entry<Double, TreeSet<Order>> buyOrdersEntry = stockPriceToOrdersMap.lastEntry();
        ArrayList<Transaction> transactionsList = new ArrayList<>();
        while(buyOrdersEntry != null && buyOrdersEntry.getKey() >= sellOrder.getPrice() && sellOrder.getPendingQuantity() > 0) {
            TreeSet<Order> buyOrders = buyOrdersEntry.getValue();

            while(sellOrder.getPendingQuantity() > 0 && buyOrders.size() > 0) {
                Order buyOrder = buyOrders.first();

                int quantityDelta = buyOrder.getPendingQuantity() - sellOrder.getPendingQuantity();

                if(quantityDelta == 0) {
                    transactionsList.add(new Transaction(buyOrder, sellOrder, sellOrder.getPendingQuantity(), sellOrder.getPrice(), buyOrder.getStock()));
                    buyOrder.setPendingQuantity(0);
                    buyOrder.setPendingQuantity(0);
                    buyOrders.remove(buyOrder);
                    break;
                } else if (quantityDelta > 0) {
                    transactionsList.add(new Transaction(buyOrder, sellOrder, sellOrder.getPendingQuantity(), sellOrder.getPrice(), buyOrder.getStock()));
                    buyOrder.setPendingQuantity(quantityDelta);
                    sellOrder.setPendingQuantity(0);
                    break;
                } else {
                    transactionsList.add(new Transaction(buyOrder, sellOrder, buyOrder.getPendingQuantity(), sellOrder.getPrice(), buyOrder.getStock()));
                    buyOrder.setPendingQuantity(0);
                    sellOrder.setPendingQuantity(-(quantityDelta));
                    buyOrders.remove(buyOrder);
                }
            }

            if(buyOrders.size() == 0) stockPriceToOrdersMap.remove(buyOrdersEntry.getKey());
            buyOrdersEntry = stockPriceToOrdersMap.lastEntry();
        }
        if(sellOrder.getPendingQuantity() > 0) addOrderToStockToOrdersMap(sellOrder);
        for (Transaction transaction : transactionsList) {
            System.out.println(transaction.getBuyOrder().getOrderId() + " " + String.format("%.2f", transaction.getPrice()) + " " + transaction.getQuantity() + " " + transaction.getSellOrder().getOrderId());
        }
        transactions.addAll(transactionsList);
    }

}
