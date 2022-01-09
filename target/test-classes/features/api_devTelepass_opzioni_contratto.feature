@api_devTelepass @all

Feature: Api controller bdd for opzioni contratto

  Background:
    Given baseUri is https://ws-test.telepass.com/TelepassKTB_REST/ktb
    And I set standard header
    And I have a valid token for username MetLife42

  Scenario: opzioni contratto ok
    Given I set body to {"codiceContratto":30018044,"tipiOpzione":["PR","PB","PA"]}
    When I POST /opzione/opzioni-contratto
    Then response code should be 200
    And response body should be valid json
    And response body should contain "errorCode":null
    And response body should contain "codiceContratto":30018044
    And response body should contain codiciTipoOpzione