Feature: Player gets point after action
  Players try to do actions to get points for their team

  Scenario Outline: Player do action then gets point
    Given On a pipe with water next to a cistern stands a "<name>"
    When The player does "<action>"
    Then The next turn the point increases for "<name>"

    Examples: 
      | name     |  action   |
      | mechanic |  repairs  |
      | saboteur |  breaks   |
