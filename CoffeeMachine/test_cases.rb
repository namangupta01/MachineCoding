test_case_one = {
  'input': {
    "machine": {
      "outlets": {
        "count_n": 3
      },
      "total_items_quantity": {
        "hot_water": 300,
        "hot_milk": 500,
        "ginger_syrup": 100,
        "sugar_syrup": 100,
        "tea_leaves_syrup": 100
      },
      "beverages": {
        "hot_tea": {
          "hot_water": 200,
          "hot_milk": 100,
          "ginger_syrup": 10,
          "sugar_syrup": 10,
          "tea_leaves_syrup": 30
        },
        "hot_coffee": {
          "hot_water": 100,
          "ginger_syrup": 30,
          "hot_milk": 400,
          "sugar_syrup": 50,
          "tea_leaves_syrup": 30
        },
        "black_tea": {
          "hot_water": 300,
          "ginger_syrup": 30,
          "sugar_syrup": 50,
          "tea_leaves_syrup": 30
        }
      }
    }
  },
  'expected_outputs': [
    [
      'hot_tea is prepared',
      'hot_coffee is prepared',
      'black_tea cannot be prepared because hot_water is not sufficient'
    ],
    [
      'black_tea is prepared',
      'hot_coffee cannot be prepared because hot_water is not sufficient',
      'hot_tea cannot be prepared because hot_water is not sufficient'
    ]
  ]
}

test_case_two = {
  'input': {
    "machine": {
      "outlets": {
        "count_n": 4
      },
      "total_items_quantity": {
        "hot_water": 500,
        "hot_milk": 500,
        "ginger_syrup": 100,
        "sugar_syrup": 100,
        "tea_leaves_syrup": 100
      },
      "beverages": {
        "hot_tea": {
          "hot_water": 200,
          "hot_milk": 100,
          "ginger_syrup": 10,
          "sugar_syrup": 10,
          "tea_leaves_syrup": 30
        },
        "hot_coffee": {
          "hot_water": 100,
          "ginger_syrup": 30,
          "hot_milk": 400,
          "sugar_syrup": 50,
          "tea_leaves_syrup": 30
        },
        "black_tea": {
          "hot_water": 300,
          "ginger_syrup": 30,
          "sugar_syrup": 50,
          "tea_leaves_syrup": 30
        },
        "green_tea": {
          "hot_water": 100,
          "ginger_syrup": 30,
          "sugar_syrup": 50,
          "green_mixture": 30
        },
      }
    }
  },
  'expected_outputs': [
    [
      'hot_tea is prepared',
      'hot_coffee is prepared',
      'black_tea cannot be prepared because hot_water is not sufficient',
      'green_tea cannot be prepared because sugar_syrup is not sufficient'
    ],
    [
      'hot_tea is prepared',
      'black_tea is prepared',
      'hot_coffee cannot be prepared because hot_water is not sufficient',
      'green_tea cannot be prepared because hot_water is not sufficient'
    ],
    [
      'hot_tea is prepared',
      'green_tea is prepared',
      'black_tea cannot be prepared because hot_water is not sufficient',
      'hot_coffee cannot be prepared because sugar_syrup is not sufficient'
    ],
    [
      'hot_coffee is prepared',
      'black_tea is prepared',
      'hot_tea cannot be prepared because hot_water is not sufficient',
      'green_tea cannot be prepared because sugar_syrup is not sufficient'
    ],
    [
      'hot_coffee is prepared',
      'green_tea is prepared',
      'hot_tea cannot be prepared because sugar_syrup is not sufficient',
      'black_tea cannot be prepared because sugar_syrup is not sufficient'
    ],
    [
      'black_tea is prepared',
      'green_tea is prepared',
      'hot_tea cannot be prepared because hot_water is not sufficient',
      'hot_coffee cannot be prepared because sugar_syrup is not sufficient'
    ],
    [
      'green_tea cannot be prepared because green_mixture is not sufficient',
      'hot_tea is prepared',
      'black_tea is prepared',
      'hot_coffee cannot be prepared because hot_water is not sufficient'
    ],
    [
      'hot_tea is prepared',
      'green_tea cannot be prepared because green_mixture is not sufficient',
      'hot_coffee is prepared',
      'black_tea cannot be prepared because hot_water is not sufficient'
    ],
    [
      'hot_tea is prepared',
      'black_tea is prepared',
      'green_tea cannot be prepared because hot_water is not sufficient',
      'hot_coffee cannot be prepared because hot_water is not sufficient'
    ],
    [
      'hot_coffee is prepared',
      'green_tea cannot be prepared because green_mixture is not sufficient',
      'black_tea is prepared',
      'hot_tea cannot be prepared because hot_water is not sufficient'
    ]
  ]
}

require 'json'

TEST_CASE_ONE = JSON.parse(test_case_one.to_json) # to_turn_symbol_keys_to_string_keys
TEST_CASE_TWO = JSON.parse(test_case_two.to_json)

