require './menu'
require './order'

class Restaurant
  attr_reader :name, :menu, :orders, :max_processing_capacity, :under_processing_orders_map

  def initialize name, max_processing_capacity
    @name = name
    @menu = Menu.new
    @orders = []
    @max_processing_capacity = max_processing_capacity
    @under_processing_orders_map = {}
  end

  def add_or_update_item item_name, price
    @menu.add_or_update_item item_name, price
  end

  def remove_item_from_menu item_name
    @menu.remove_item_from_menu item_name
    puts "#{item_name} removed from #{@name}'s menu\n\n"
  end

  def place_order order_id, item_names, price
    order = Order.new item_names, self, order_id, price
    under_processing_orders_map[order_id] = order
    orders << order
    order.print
    return order
  end

  def dispatch_order order_id
    if under_processing_orders_map[order_id]
      order = under_processing_orders_map[order_id]
      order.dispatch
      under_processing_orders_map.delete(order_id)
    end
  end

  def print_menu
    @menu.print
  end

  def is_available_to_place_order
    return @max_processing_capacity > @under_processing_orders_map.keys.count
  end

  def check_and_get_items_prices item_names
    all_items_present, total_price = @menu.total_item_prices item_names
    if all_items_present
      return total_price
    else
      return -1
    end
  end

  def items_available_with_prices item_names
    return -1 unless self.is_available_to_place_order
    check_and_get_items_prices item_names
  end

end
