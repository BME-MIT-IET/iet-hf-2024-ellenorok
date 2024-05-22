Feature: Water flows in pipes
  The player can change the pumps flow direction, which decides the flow direction of the water

  Scenario: Waters flow direction changes
    Given A player stands on a working pump
    When The player changes the pumps flow direction
    Then The old pipe output wont have water
    And The new pipe output will have water