@api_devRCA @all

Feature: Api controller bdd for rca enabled-source

  Background:
    Given baseUri is https://ws-test.tera76.com/tera76KTB_REST/ktb
    And I set standard header


  Scenario Outline: get enabled-source for customerCode by username
    Given I have a valid token and customerCode for username <username>
    And I set base uri for rca gateway
    And I set standard header
    When I get /policy/enabled-source for customerCode
    Then response code should be 200
    And response body should be valid json
    And response body should be equal to
    """
    <expected>
    """

    Examples:
      | username   | expected                                                |
      | italia607  | ["BROKER"]                                              |
      | italia780  | ["BROKER"]                                              |
      | italia781  | ["MGA","BROKER"]                                        |
      | italia782  | ["BROKER"]                                              |
      | italia784  | [{"licensePlate":"CS388KG"},{"licensePlate":"EP550VA"}] |
      | TestRca147 | ["BROKER"]                                              |
      | TestRca160 | ["BROKER"]                                              |
      | TestRca163 | ["MGA","BROKER"]                                        |
      | italia787  | ["BROKER"]                                              |

