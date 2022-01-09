@ttt

Feature: Api controller bdd for rca ruby quotations

  Background:
    Given baseUri is https://ws-test.tera76.com/tera76KTB_REST/ktb
    And I set standard header
    And I have a valid token and contractCode for username TestRca147
    And I have a valid ApparatusCode


  Scenario: aggiungi targa
    Given I set body for modifica targa with codiceContratto and apparatusCode for targa AZ76474
    When I POST /targa/modifica-targa
    Then response code should be 200
    And response body should be valid json
    And response body should contain "errorCode":null
    And response body should contain "status":"00"
    And response body should contain "codiceTarga":"AZ76474",
    And response body should contain "codiceContratto":30005029,

