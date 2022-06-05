require './item'

class ItemList
  attr_reader :items_map

  def initialize
    @items_map = {}
  end

  def find_or_create_item item_name
    return @items_map[item_name] if @items_map[item_name]
    item = Item.new item_name
    @items_map[item_name] = item
    item
  end

end
