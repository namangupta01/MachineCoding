class IngredientCompartment
  attr_reader :ingredients

  def initialize ingredients
    @ingredients = ingredients

    # Using Mutex so that we can use thread synchronization whenever required
    @mutex = Mutex.new
  end

  # For Thread safety synchronization on current Ingredient Compartment Object
  def synchronize(&block)
    @mutex.synchronize(&block)
  end

  def check_and_consume_items(item_quantities)
    # Thread Synchronizing this block so that this block of code can only be executed by only one thread at a time
    self.synchronize do
      sufficient_quantity_present, not_sufficient_item = sufficient_quantity_present?(item_quantities)
      if sufficient_quantity_present
        consume_items(item_quantities)
      end
      return sufficient_quantity_present, not_sufficient_item
    end
  end

  # Below method should not be publicly exposed so keeping them private
  private

  # Function to check if the coffee machine have the sufficient ingredients quantities required to make a beverage.
  # This method return true or false and name of a not sufficient ingredient in case when ingredients are not sufficient
  def sufficient_quantity_present?(item_quantities)
    all_items_present = true
    not_sufficient_item = nil
    item_quantities.each do |ingredient_name, quantity|
      if !@ingredients[ingredient_name] || @ingredients[ingredient_name] < quantity
        all_items_present = false
        not_sufficient_item = ingredient_name
        break
      end
    end
    return all_items_present, not_sufficient_item
  end

  # This method deduct the ingredients quantities required to make a particular beverages.
  # This method independently is not thread safe. In order to perform this function in thread safe environment use
  # check_and_consume_items method
  def consume_items(item_quantities)
    item_quantities.each do |ingredient_name, quantity|
      @ingredients[ingredient_name] = @ingredients[ingredient_name] - quantity
    end
  end

end
