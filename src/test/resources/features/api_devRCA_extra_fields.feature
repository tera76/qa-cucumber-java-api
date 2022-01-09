@api_devRCA @all

Feature: Api controller bdd for rca license plate

  Background:
    Given baseUri is https://ws-test.tera76.com/tera76KTB_REST/ktb
    And I set standard header


  Scenario: get extra fields
    Given I have a valid token for username italia607
    And I set base uri for rca gateway
    And I set standard header
    When I GET /extra-fields
    Then response code should be 200
    And response body should be valid json
    And response body should contain "code": "occupation"
    And response body should contain "label": "Agente di commercio / Rappresentante"
    And response body should contain "value": "agente_commercio_rappresentante"
    And response body should contain "label": "Notaio"
    And response body should contain "code": "kms_per_year"
    And response body should contain "label": "Meno di 10000 km"
    And response body should contain "value": "meno_di_10000"
    And response body should contain "code": "license_year"
    And response body should contain "label":"2020"
    And response body should contain "value":"2020"


