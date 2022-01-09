@api_devRCA @all

Feature: Api controller bdd for rca content

  Background:
    Given baseUri is https://ws-test.tera76.com/tera76KTB_REST/ktb
    And I set standard header

  Scenario: get content for fixed customer code
    Given I have a valid token for username italia607
    And I set base uri for rca gateway
    And I set standard header
    When I GET /content?customerCode=26002101
    Then response code should be 200
    And response body should be valid json
    And response body should contain "title": "ASSICURAZIONE AUTO"

  Scenario Outline: get content for customerCode by username
    Given I have a valid token for username <username>
    And I set base uri for rca gateway
    And I set standard header
    When I get /content for customerCode
    Then response code should be 200
    And response body should be valid json
    And response body should contain "title": "ASSICURAZIONE AUTO"
    And response body should contain "subtitle": "Scopri l'Assicurazione Auto con tera76: Ã¨ semplice, veloce e su misura per te."
    And response body should contain "nextButton": "Scopri di piÃ¹"
    And response body should contain "subtitle": "L'assicurazione per questo veicolo Ã¨ in scadenza."
    And response body should contain "paragraph": "Con tera76 hai un modo per rinnovarla: semplice, veloce e su misura per te."


    Examples:
      | username   |
      | italia607  |
      | italia780  |
      | italia781  |
      | italia782  |
      | italia784  |
      | TestRca160 |
      | TestRca163 |
      | italia787  |