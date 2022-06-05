public class Order {
    private final String orderId;
    private final Long time; // Storing time in terms of seconds of a day
    private final Boolean isBuyOrder;
    private final Stock stock;
    private Integer pendingQuantity;
    private final Integer quantity;
    private final Double price;

    public Order(String orderId, Long time, Boolean isBuyOrder, Integer pendingQuantity, Double price, Stock stock) {
        this.orderId = orderId;
        this.time = time;
        this.isBuyOrder = isBuyOrder;
        this.pendingQuantity = pendingQuantity;
        this.quantity = pendingQuantity;
        this.price = price;
        this.stock = stock;
    }

    public String getOrderId() {
        return orderId;
    }

    public Boolean getBuyOrder() {
        return isBuyOrder;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getPendingQuantity() {
        return pendingQuantity;
    }

    public Long getTime() {
        return time;
    }

    public void setPendingQuantity(Integer pendingQuantity) {
        this.pendingQuantity = pendingQuantity;
    }

    public Stock getStock() {
        return stock;
    }
}
