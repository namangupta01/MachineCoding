public class Transaction {
    private final Order buyOrder;
    private final Order sellOrder;
    private final Integer quantity;
    private final Double price;
    private final Stock stock;

    public Transaction(Order buyOrder, Order sellOrder, Integer quantity, Double price, Stock stock) {
        this.buyOrder = buyOrder;
        this.sellOrder = sellOrder;
        this.quantity = quantity;
        this.price = price;
        this.stock = stock;
    }

    public Double getPrice() {
        return price;
    }

    public Order getBuyOrder() {
        return buyOrder;
    }

    public Order getSellOrder() {
        return sellOrder;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Stock getStock() {
        return stock;
    }
}
