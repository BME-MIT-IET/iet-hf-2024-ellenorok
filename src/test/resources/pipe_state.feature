Feature: Pipe state
  Player action can change the surface of a pipe, which can hinder other player
  
  Scenario Outline: Player do action to change the state of a pipe
    Given A player stands on a pipe and another player stands next to it
    When The player on the pipe makes it '<state>'
    And The other player steps on it
    Then The moving player '<happening>' on the pipe

    Examples: 
      | state   | happening   |
      | sticky  |   stucks    |
      | slippery| cannot stay |
