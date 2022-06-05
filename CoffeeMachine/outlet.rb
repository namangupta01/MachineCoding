class Outlet
  attr_reader :coffee_machine, :ingredient_compartment

  # This initilizes the Outlet
  def initialize coffee_machine, ingredient_compartment
    @coffee_machine = coffee_machine
    @ingredient_compartment = ingredient_compartment

    # Using Mutex so that we can use thread synchronization whenever required
    @mutex = Mutex.new
  end

  def prepare_beverage beverage_name, beverage_quantity
    # Using Mutex Synchronization for thread safety so that only one thread execute the below block at a time and it happens
    # atomically so if any other thread wants to access this block they have to wait untill earlier thread has finished
    # executing this block and have released the lock
    self.synchronize do
      beverage_prepared, not_sufficient_item = @ingredient_compartment.check_and_consume_items beverage_quantity
      if beverage_prepared
        return "#{beverage_name} is prepared"
      else
        result = "#{beverage_name} cannot be prepared because #{not_sufficient_item} is not sufficient"
      end
      return result
    end
  end

  # For Thread safety synchronization on current Outlet Object because only person can access the Outlet at a Time
  def synchronize(&block)
    @mutex.synchronize(&block)
  end
end
