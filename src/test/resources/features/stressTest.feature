@stresstest
Feature: Flask App Stress Test

  Scenario Outline: Task2-Perform Stress Testing on client_login end point
    Given I set the thread with "<noOfThreads>"-"<rampUpPeriod>"-"<loopCount>" for "clientLoginStress.jmx" file
    And I use the "client_login_stress" file for request body for Iteration "<Itr>"
    Then I run the script
    Examples:
      | Itr | noOfThreads | rampUpPeriod | loopCount |
      | 1   | 100         | 5            | 10        |
      | 2   | 200         | 5            | 10        |
      | 3   | 300         | 5            | 10        |