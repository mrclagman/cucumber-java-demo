Feature: Update product details

  Scenario: Update product title
    Given an admin wants to update the "title" of an existing product with id 1
    When an admin updates the product
    Then response should be status 200
    And response body should contain the updated "title"