@api_devTelepass @all

Feature: Api controller bdd for contratto/salva-nickname

  Background:
    Given baseUri is https://ws-test.telepass.com/TelepassKTB_REST/ktb
    And I set standard header
    And I have a valid token for username ciaombcs203

  Scenario: post salva nickname
    Given I set body to
    """
{"codiceContratto":750004028,"nicknameContratto":"test nick"}
    """
    When I POST /contratto/salva-nickname
    Then response code should be 200
    And response body should be valid json
    And response body should be equal to
    """
    {"errorCode":null,"errorDesc":null,"causalCode":null,"exHashCode":null,"status":"00"}
    """
