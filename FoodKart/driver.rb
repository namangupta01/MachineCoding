require './item_list'
require './restaurant'
require './restaurants_input'


class Driver
  attr_reader :commands, :restaurants_map, :items_map, :item_list, :order_restaurant_map, :unprocessed_orders_restaurant_map

  def initialize onboard_restaurants_data
    @item_list = ItemList.new
    @restaurants_map = {}
    @items_map = {}
    @order_restaurant_map = {}
    @unprocessed_orders_restaurant_map = {}
    onboard_restaurants_data.each do |restaurant_data|
      restaurant_name = restaurant_data[0]
      restaurant_capacity = restaurant_data[1]
      restaurant = self.create_restaurant restaurant_name, restaurant_capacity
      @restaurants_map[restaurant_name] = restaurant
      restaurant_data[2].each do |item_prices_data| #[['item1', 30], ['item2', 5], ['item3', 20]]
        item_name = item_prices_data[0]
        item_price = item_prices_data[1]
        self.add_or_update_item(restaurant_name, item_name, item_price)
      end
    end
  end

  def create_restaurant restaurant_name, max_processing_capacity
    if @restaurants_map[restaurant_name]
      return @restaurants_map[restaurant_name]
    end
    restaurant = Restaurant.new restaurant_name, max_processing_capacity
    @restaurants_map[restaurant_name] = restaurant
    return restaurant
  end

  def add_or_update_item restaurant_name, item_name, price
    restaurant = @restaurants_map[restaurant_name]
    unless restaurant
      puts "No Restaurant with name #{restaurant_name} is present\n\n"
      return
    end
    item = @item_list.find_or_create_item item_name
    restaurant.add_or_update_item item_name, price
    restaurant.print_menu
  end

  def remove_item restaurant_name, item_name
    restaurant = @restaurants_map[restaurant_name]
    restaurant.remove_item_from_menu item_name
    #
    restaurant.print_menu

  end

  def place_order_to_restaurant order_id, item_names
    # puts order_id
    # require 'byebug'
    # byebug
    #
    puts item_names
    if @order_restaurant_map[order_id]
      puts "Order with this id already present\n\n"
      return nil
    end

    available_items_with_prices = []

    restaurants_map.each do |restaurant_name, restaurant|
      price = restaurant.items_available_with_prices item_names
      if price != -1
        available_items_with_prices << [restaurant_name, price]
      end
    end

    if available_items_with_prices.count.zero?
      puts "No Restaurant Available\n\n"
      return nil
    end

    # puts sorted_available_items_with_prices

    sorted_available_items_with_prices = available_items_with_prices.sort{ |a| -a[1] }
    selected_restaurant_name = sorted_available_items_with_prices[0][0]
    price = sorted_available_items_with_prices[0][1]
    selected_restaurant = restaurants_map[selected_restaurant_name]
    order = selected_restaurant.place_order order_id, item_names, price
    @unprocessed_orders_restaurant_map[order_id] = selected_restaurant
    @order_restaurant_map[order_id] = selected_restaurant
    return order
  end

  def dispatch_order order_id
    return "No Order with this Id\n\n" if @unprocessed_orders_restaurant_map[order_id].nil?
    restaurant = @unprocessed_orders_restaurant_map[order_id]
    restaurant.dispatch_order order_id
    @unprocessed_orders_restaurant_map.delete(order_id)
  end

  def execute_commands commands
    commands = commands.sort { |d| -d[0] }
    # puts commands
    commands.each do |command|
      if command[1] == 'place-order'
        self.place_order_to_restaurant command[2], command[3]
      elsif command[1] == 'update-price'
        self.add_or_update_item command[2], command[3], command[4]
      elsif command[1] == 'dispatch-order'
        self.dispatch_order command[2]
      elsif command[1] == 'add-items'
        self.add_or_update_item command[2], command[3], command[4]
      elsif command[1] == 'remove-items'
        self.remove_item command[2], command[3]
      end
    end
  end

end

# ['reviews', 'price', ]

restaurants_data = [
  ['Restaurant 3', 1, [['item1', 30], ['item2', 5], ['item3', 20]]],
  ['Restaurant 1', 1, [['item1', 10], ['item2', 20], ['item3', 30]]],
  ['Restaurant 2', 1, [['item2', 20], ['item3', 10], ['item4', 30]]],
  # ['Restaurant 3', 1, [['item1', 30], ['item2', 5], ['item3', 20]]],
  ['Restaurant 4', 3, [['item2', 40], ['item3', 2], ['item4', 40]]],
  ['Restaurant 5', 4, [['item3', 50], ['item4', 15], ['item5', 35]]],
]

commands = [
  [1, 'place-order', 'order1', ['item1', 'item2', 'item3']],
  # [2, 'place-order', 'order2', ['item1']],
  # [7, 'remove-items', 'Restaurant 3', 'item1'],
  # [7, 'remove-items', 'Restaurant 1', 'item1'],
  # # [3, 'place-order', 'order2', ['item1', 'item2', 'item3']],
  # # [4, 'place-order', 'order3', ['item1', 'item2', 'item3']],
  # [5, 'dispatch-order', 'order2'],
  # # [6, 'place-order', 'order4', ['item1', 'item2', 'item3']],
  # # [8, 'dispatch-order', 'order1'],
  # # [9, 'place-order', 'order5', ['item1', 'item2', 'item3']],
  # # [10, 'add-items', 'Restaurant 10', 'item1', 10],
  # # [11, 'add-items', 'Restaurant 3', 'item1', 10],
  # # [12, 'place-order', 'order6', ['item1', 'item2', 'item3']],
  # # [13, 'place-order', 'order7', ['item1', 'item2', 'item3', 'item4']],


]

onboard_restaurants_data = restaurants_data
driver = Driver.new onboard_restaurants_data
driver.execute_commands commands
