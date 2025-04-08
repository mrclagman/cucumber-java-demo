Feature: Add a course in course list


  Scenario: User adds a course
    Given an admin creates a random course
    When admin adds the course
    Then response should be status 201
    And response body should contain correct schema for addition of course
    And response body should contain message for successful course addition
