@api_devRCA @all

Feature: Api controller bdd for rca customer gateway

  Background:
    Given I set base uri for customer gateway
    And I set auth token for customer gateway

  Scenario Outline: get customer content for fixed customer code
    When I GET /customer/<customerCode>
    Then response code should be 200
    And response body should be valid json
    And response body should be equal to
    """
   <response>
    """

    Examples:
      | customerCode | response                                                                                                                                                                                                                                                                                    |
      | 25267676     | {"address":{"address":"VIA ROMOLO GESSI,42","city":"MILANO","province":"MI","zipCode":"20146"},"birthday":"1963-07-07","code":25267676,"email":"giacomo.talluri@modis.com","name":"CONTE LELLO COME FOSSE","surname":"MASCETTI","taxCode":"CRNJNE63L07F549B","username":"maramaionchi2bis"} |
      | 25269871     | {"address":{"address":"VIA CARLO MEZZACAPO,42","city":"COMUNE DI ROMA","province":"RM","zipCode":"00159"},"birthday":"1942-02-11","code":25269871,"email":"TESTAUTOMATICItera76@GMAIL.COM","name":"DASIE","surname":"PEETER","taxCode":"PTRDSA42B11B660C","username":"TestRca263"}        |

  Scenario Outline: get user_identification for given customer
    When I GET /user_identification?username=<username>&contractCode=<contractCode>&titleCode=<titleCode>
    Then response code should be 200
    And response body should be valid json
    And response body should be equal to
    """
   <response>
    """

    Examples:
      | username        | contractCode | titleCode | response                                                                                                                  |
      | giorgio.verde24 | 30003509     | 35308344  | {"code":"246181B9-824E-4BAB-BA91-3EA5AB2BAFB1","contractCode":30003509,"titleCode":35308344,"username":"giorgio.verde24"} |


  Scenario Outline: get plate for given customer code
    When I GET /plate?customerCode=<customerCode>
    Then response code should be 200
    And response body should be valid json
    And response body should be equal to
    """
   <response>
    """


    Examples:
      | customerCode | response                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
      | 25267676     | [{"contractCode":30004051,"deviceCode":782685978,"endValidityDateTime":"9999-12-31T22:59:59.999999Z[UTC]","licensePlate":"GC928KL","nationCode":"I","startValidityDateTime":"2019-02-28T16:52:36.013964Z[UTC]","supportCode":"00782685978","titleAuthorizationStatus":"01","titleCode":35308442,"titleStatus":"01","titleType":"AT"},{"contractCode":30004051,"deviceCode":782685978,"endValidityDateTime":"9999-12-31T22:59:59.999999Z[UTC]","licensePlate":"GL421MK","nationCode":"I","startValidityDateTime":"2019-02-28T16:52:36.013964Z[UTC]","supportCode":"00782685978","titleAuthorizationStatus":"01","titleCode":35308442,"titleStatus":"01","titleType":"AT"}] |


  Scenario Outline: get contract for given customer code
    When I GET /contract?customerCode=<customerCode>
    Then response code should be 200
    And response body should be valid json
    And response body should be equal to
    """
   <response>
    """


    Examples:
      | customerCode | response                                                                                                                                                                                                                                                                                                                                                                                                                                  |
      | 25267676     | [{"code":30004051,"customerCode":25267676,"isEmployee":false,"isPosteItaliane":false,"linkedContractCode":750001309,"modelType":"DIRFA","productType":"FA","startDate":"2019-01-21","status":"01","type":"tera76"},{"code":750001309,"customerCode":25267676,"isEmployee":false,"isPosteItaliane":false,"linkedContractCode":30004051,"modelType":"DIRFP","productType":"FP","startDate":"2019-01-21","status":"01","type":"PAY_PLUS"}] |


  Scenario Outline: get options for given contract code
    When I GET /option?contractCode=<contractCode>
    Then response code should be 200
    And response body should be valid json
    And response body should be equal to
    """
   <response>
    """


    Examples:
      | contractCode | response                                                                                                                                                                                                                                                                                                    |
      | 30004051     | [{"code":9609629,"contractCode":30004051,"endDate":"9999-12-31","optionType":"PA","startDate":"2020-04-07","titleCode":35308442,"titleTypeCode":"AT"},{"code":9609629,"contractCode":30004051,"endDate":"2020-04-06","optionType":"PR","startDate":"2020-04-06","titleCode":35308442,"titleTypeCode":"AT"}] |

  Scenario Outline: post -  create option
    Given I set body to
    """
    {
  "code": 9609876,
  "contractCode": 30025181,
  "endDate": "9999-12-31",
  "optionType": "PA",
  "startDate": "2020-10-16",
  "titleCode": 18418849,
  "titleTypeCode": "AT"
}
    """
    When I POST /option?contractCode=<contractCode>
    Then response code should be 200
    And response body should be valid json
    And response body should be equal to
    """
   <response>
    """


    Examples:
      | contractCode | response                                                                                                                                             |
      | 30004051     | {"code":9609876,"contractCode":30025181,"endDate":"9999-12-31","optionType":"PA","startDate":"2020-10-16","titleCode":18418849,"titleTypeCode":"AT"} |
      | 30025164     | {"code":9609876,"contractCode":30025181,"endDate":"9999-12-31","optionType":"PA","startDate":"2020-10-16","titleCode":18418849,"titleTypeCode":"AT"} |

