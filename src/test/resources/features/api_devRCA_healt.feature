@api_devRCA @all

Feature: Api controller for rca health

  Background:
    Given I set base uri for rca healt gateway

  Scenario: get live
    When I GET /live
    Then response code should be 200
    And response body should be valid json
    And response body should contain "status":"UP"
    And response body should contain "name":"rca-gateway"


  Scenario: get ready
    When I GET /ready
    Then response code should be 200
    And response body should be valid json
    And response body should contain "status":"UP"
    And response body should contain "name":"rca-gateway"
