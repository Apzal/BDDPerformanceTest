@stresstest
Feature: Flask App Stress Test

  Scenario Outline: Task2-Perform Stress Testing on client_login end point
    Given I set the thread with "<noOfThreads>"-"<rampUpPeriod>"-"<loopCount>" for "clientLogin.jmx" file
    And I use the "client_login_stress" file for request body
    Then I run the script
    Examples:
      | noOfThreads | rampUpPeriod | loopCount |
      | 100          | 5            | 10         |
      | 200          | 5            | 10        |
      | 300          | 5            | 10        |