Feature: Update a course in course list

  Scenario: User adds then updates a course
    Given an admin has created a course
    And admin wants to update the course details
    When admin updates the course
    Then response should be status 200
    And response body should contain correct schema for update of course
    And response body should contain message for successful course update