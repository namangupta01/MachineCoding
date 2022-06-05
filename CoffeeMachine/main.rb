## Running all the test cases

require './coffee_machine'
require './test_cases'

# Method to execute and check if coffee machine is behaving as expected or not
def execute_test_case test_case
  outlets_data = test_case['input']['machine']['outlets']

  # Total ingredients present
  total_items_quantity_data = test_case['input']['machine']['total_items_quantity']

  # Beverages To prepare Information
  beverages = test_case['input']['machine']['beverages']

  # Test case expected output
  expected_outputs = test_case['expected_outputs']

  # Creating coffee machine object. Passing number of outlets data and total ingredients quantity
  cm = CoffeeMachine.new outlets_data, total_items_quantity_data.clone

  threads = []
  coffee_machine_actual_output = []

  mutex = Mutex.new
  beverages.each do |beverage_name, beverage_quantity_data|
    thread = Thread.new do
      cm_output = cm.get_outlet.prepare_beverage(beverage_name, beverage_quantity_data)

      # Using mutex so that we add the output to the coffee_machine_actual_output array with Thread Safety
      mutex.synchronize do
        coffee_machine_actual_output << cm_output
      end

    end
    threads << thread
  end

  threads.each(&:join) # Waiting for all the threads to complete the work

  # Checking here, if actual output is matching the expected output or not
  output_matched = false
  expected_outputs.each do |output|
    if (output - coffee_machine_actual_output).count == 0
      output_matched = true
      break
    end
  end

  puts coffee_machine_actual_output
  if output_matched
    puts "Test Cases Passed! \n \n"
  else
    puts "Test Cases Failed! \n \n"
  end
  output_matched
end

# Execute all Test Cases
execute_test_case TEST_CASE_ONE
execute_test_case TEST_CASE_TWO
