import java.util.ArrayList;

public class OrderBook {
    private final ArrayList<Order> allOrders;

    public OrderBook() {
        allOrders = new ArrayList<>();
    }

    public void addOrder(Order order) {
        allOrders.add(order);
    }
}
