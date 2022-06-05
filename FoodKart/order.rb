class Order
  attr_reader :item_names, :restaurant, :id, :status, :price

  def initialize item_names, restaurant, id, price
    @items = item_names
    @restaurant = restaurant
    @id = id
    @status = 'placed'
    @price = price
  end

  def dispatch
    @status = 'dispatched'
    puts "Order with id: #{@id} dispatched from #{@restaurant.name}\n\n"
  end

  def print
    puts "Order of #{@price} placed successfully at #{@restaurant.name}\n\n"
  end

end
