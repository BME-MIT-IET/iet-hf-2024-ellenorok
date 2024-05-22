Feature: Mechanic put down new pump
  Mechanic players can expand the pipe network by picking up a new pump and putting it down at a pipe

  Scenario: Expand pipe network
    Given A mechanic player stands on a cistern and picks up a pump
    When The player steps on the pipe
    And The player puts down the pump
    Then The pipe will become two new pipes
    And The pump will be between the two new pipes
    And The player will stand on the pump
    And The player wont has any action left
    