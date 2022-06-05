require './ingridient_compartment'
require './outlet'

class CoffeeMachine
  attr_reader :ingredient_compartment, :outlets, :next_outlet_number

  # Initializing CoffeeMachine with number of outlets data and total ingredient quantities
  # Example: outlets_data => { 'count_n': 4}
  # total_items_quantity_data => { "hot_water": 300, "hot_milk": 500, "ginger_syrup": 100, "sugar_syrup": 100, "tea_leaves_syrup": 100 }
  def initialize outlets_data, total_items_quantity_data
    outlets_count = outlets_data['count_n']
    @ingredient_compartment = IngredientCompartment.new(total_items_quantity_data)
    @outlets = []
    # Creating all the outlets object for this coffee machine
    outlets_count.times.each do |count|
      @outlets << Outlet.new(self, @ingredient_compartment)
    end

    # Using @next_outlet_number to find the next outlet for any beverages which is to be used
    @next_outlet_number = 0
  end

  # This method can be used to find the outlet which should be used to prepare the beverage
  # In a case when we have to prepare more beverages than the number of outlets present and then in that case we are
  # starting form the first outlet and assigning that outlet to the beverage which will wait untill the threads which
  # are using that outlets have finised their work
  def get_outlet
    current_outlet_number = @next_outlet_number
    @next_outlet_number = @next_outlet_number + 1
    if @next_outlet_number == @outlets.size
      @next_outlet_number = @next_outlet_number % @outlets.size
    end
    @outlets[current_outlet_number]
  end

end
