class Menu
  attr_reader :items
  # { item_name: {
  #   review: [],
  #   comments: []
  # }

  def initialize
    @items = {}
  end

  def add_or_update_item item_name, price
    @items[item_name] = price
  end

  def remove_item_from_menu item_name
    @items.delete(item_name)
  end

  def items
    //
  end

  def print
    puts "Menu"
    puts "------------------"
    items.each do |item_name, price|
      puts "#{item_name}: #{price}"
    end
    puts "------------------"
    puts ""
  end

  def total_item_prices item_names
    sum = 0
    all_items_present = true
    item_names.each do |item_name|
      if @items[item_name]
        sum += @items[item_name]
      else
        all_items_present = false
      end
    end

    return all_items_present, sum
  end

end
