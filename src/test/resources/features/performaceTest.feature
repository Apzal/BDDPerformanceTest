@loadtest
Feature: Flask App Performance Test

  Scenario Outline: Task1-Perform Load Testing on client_registration end point
    Given I set the thread with "<noOfThreads>"-"<rampUpPeriod>"-"<loopCount>" for "clientRegister.jmx" file
    And I create a "client_details" and "client_login" file for request body with "<dataRowCount>" data in iteration "<Itr>"
    Then I run the script
    Examples:
      | Itr | noOfThreads | rampUpPeriod | loopCount | dataRowCount |
      | 1   | 3           | 5            | 5         | 15           |
      | 2   | 5           | 5            | 5         | 25           |
      | 3   | 10          | 5            | 10        | 100          |

  Scenario Outline: Task2-Perform Load Testing on client_login end point
    Given I set the thread with "<noOfThreads>"-"<rampUpPeriod>"-"<loopCount>" for "clientLogin.jmx" file
    And I use the "client_login" file for request body in iteration "<Itr>"
    Then I run the script
    Examples:
      | Itr | noOfThreads | rampUpPeriod | loopCount |
      | 1   | 3           | 5            | 5         |
      | 2   | 5           | 5            | 5         |
      | 3   | 10          | 5            | 10        |