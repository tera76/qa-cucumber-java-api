@api_skipass @all

Feature: Api controller bdd for skipass tos resource endpoints

  Background:
    Given baseUri is https://ws-test.telepass.com


  Scenario: GET tos: Retrieve and read Terms of Service for user - ok24122020
    Given I have a valid token by 3rd-party login for username giorgio.verde28
    And I set base uri for skipass gateway
    When I GET /v1/tos?contractCode=30004645&customerCode=25268288
    Then response code should be 200
    And response body should be valid json
    And response body should be equal to
    """
    {"data":[{"builder":{},"description":"I presenti termini e condizioni di utilizzo dei nuovi Singoli Servizi (di seguito âTermini e Condizioni di Utilizzoâ) sono fornite da TPAY al Cliente ai sensi dellâart. 4.4 del Contratto Telepass PAY pacchetto Plus (di seguito il âContratto TPAYâ) e hanno lo scopo di illustrare le caratteristiche generali e le relative modalitÃ  di utilizzo, che rimangono comunque soggette alle Norme e Condizioni previste dal Contratto dal Foglio Informativo, dal Documento di Sintesi e dalla restante...","link":"https://storage.googleapis.com/tlp-skidata-dev.appspot.com/norme_e_condizioni_telepass_pay_skipass.html","mandatory":true,"serviceId":33,"title":"Norme di utilizzo","tosId":1},{"builder":{},"description":"Nuove N&C!\n\nI presenti termini e condizioni di utilizzo dei nuovi Singoli Servizi (di seguito âTermini e Condizioni di Utilizzoâ) sono fornite da TPAY al Cliente ai sensi dellâart. 4.4 del Contratto Telepass PAY pacchetto Plus (di seguito il âContratto TPAYâ) e hanno lo scopo di illustrare le caratteristiche generali e le relative modalitÃ  di utilizzo, che rimangono comunque soggette alle Norme e Condizioni previste dal Contratto dal Foglio Informativo, dal Documento di Sintesi e dalla restante...","link":"https://storage.googleapis.com/tlp-skidata-dev.appspot.com/norme_e_condizioni_telepass_pay_skipass.html","mandatory":true,"serviceId":33,"title":"Nuove norme di utilizzo","tosId":1},{"builder":{},"description":"I presenti termini e condizioni di utilizzo dei nuovi Singoli Servizi (di seguito âTermini e Condizioni di Utilizzoâ) sono fornite da TPAY al Cliente ai sensi dellâart. 4.4 del Contratto Telepass PAY pacchetto Plus (di seguito il âContratto TPAYâ) e hanno lo scopo di illustrare le caratteristiche generali e le relative modalitÃ  di utilizzo, che rimangono comunque soggette alle Norme e Condizioni previste dal Contratto dal Foglio Informativo, dal Documento di Sintesi e dalla restante...","link":"https://storage.googleapis.com/tlp-skidata-dev.appspot.com/norme_e_condizioni_telepass_pay_skipass.html","mandatory":true,"serviceId":33,"title":"Norme di inutilizzo","tosId":2},{"builder":{},"description":"Nuove N&C!\n\nI presenti termini e condizioni di utilizzo dei nuovi Singoli Servizi (di seguito âTermini e Condizioni di Utilizzoâ) sono fornite da TPAY al Cliente ai sensi dellâart. 4.4 del Contratto Telepass PAY pacchetto Plus (di seguito il âContratto TPAYâ) e hanno lo scopo di illustrare le caratteristiche generali e le relative modalitÃ  di utilizzo, che rimangono comunque soggette alle Norme e Condizioni previste dal Contratto dal Foglio Informativo, dal Documento di Sintesi e dalla restante...","link":"https://storage.googleapis.com/tlp-skidata-dev.appspot.com/norme_e_condizioni_telepass_pay_skipass.html","mandatory":true,"serviceId":33,"title":"Nuove norme di inutilizzo","tosId":2},{"builder":{},"description":"I presenti termini e condizioni di utilizzo dei nuovi Singoli Servizi (di seguito âTermini e Condizioni di Utilizzoâ) sono fornite da TPAY al Cliente ai sensi dellâart. 4.4 del Contratto Telepass PAY pacchetto Plus (di seguito il âContratto TPAYâ) e hanno lo scopo di illustrare le caratteristiche generali e le relative modalitÃ  di utilizzo, che rimangono comunque soggette alle Norme e Condizioni previste dal Contratto dal Foglio Informativo, dal Documento di Sintesi e dalla restante...","link":"https://storage.googleapis.com/tlp-skidata-dev.appspot.com/norme_e_condizioni_telepass_pay_skipass.html","mandatory":true,"serviceId":33,"title":"Paste alle norme","tosId":3}],"result":"ok","status":200}

     """

  Scenario: POST tos: accept any tos - ok24122020
    Given I have a valid token by 3rd-party login for username giorgio.verde28
    And I set base uri for skipass gateway
    And I set body to
    """
  {
  "list": [
    {
      "description": "string",
      "id": 0,
      "link": "string",
      "mandatory": true,
      "serviceId": 0,
      "title": "string",
      "tosId": 0,
      "version": 0
    }
  ]
}
     """
    When I POST /v1/tos/accept/any?contractCode=30004645&customerCode=25268288
    Then response code should be 200
    And response body should be valid json
    And response body should be equal to
  """
  {"data":[{"builder":{},"description":"string","id":0,"link":"string","mandatory":true,"serviceId":0,"title":"string","tosId":0,"version":0}],"result":"ok","status":200}
   """

  Scenario: POST tos accept: Persists user’s acceptance of TOS (one or more) - ok24122020
    Given I have a valid token by 3rd-party login for username giorgio.verde28
    And I set base uri for skipass gateway
    And I set body to
    """
  {
  "list": [
    {
      "description": "string",
      "id": 0,
      "link": "string",
      "mandatory": true,
      "serviceId": 0,
      "title": "string",
      "tosId": 0,
      "version": 0
    }
  ]
}
     """
    When I POST /v1/tos/accept/123?contractCode=30004645&customerCode=25268288
    Then response code should be 200
    And response body should be valid json
    And response body should be equal to
    """
    {"result":"ok","status":200}
     """


  Scenario: GET tos accepted - ok24122020
    Given I have a valid token by 3rd-party login for username giorgio.verde28
    And I set base uri for skipass gateway
    When I GET /v1/tos/accepted?contractCode=30004645&customerCode=25268288
    Then response code should be 200
    And response body should be valid json
    And response body should be equal to
    """
   {"data":[],"result":"ok","status":200}
    """

  Scenario: GET tos not accepted - ok24122020
    Given I have a valid token by 3rd-party login for username giorgio.verde28
    And I set base uri for skipass gateway
    When I GET /v1/tos/not/accepted?contractCode=30004645&customerCode=25268288
    Then response code should be 200
    And response body should be valid json
    And response body should be equal to
    """
 {"data":[{"builder":{},"description":"I presenti termini e condizioni di utilizzo dei nuovi Singoli Servizi (di seguito âTermini e Condizioni di Utilizzoâ) sono fornite da TPAY al Cliente ai sensi dellâart. 4.4 del Contratto Telepass PAY pacchetto Plus (di seguito il âContratto TPAYâ) e hanno lo scopo di illustrare le caratteristiche generali e le relative modalitÃ  di utilizzo, che rimangono comunque soggette alle Norme e Condizioni previste dal Contratto dal Foglio Informativo, dal Documento di Sintesi e dalla restante...","link":"https://storage.googleapis.com/tlp-skidata-dev.appspot.com/norme_e_condizioni_telepass_pay_skipass.html","mandatory":true,"serviceId":33,"title":"Norme di utilizzo","tosId":1},{"builder":{},"description":"Nuove N&C!\n\nI presenti termini e condizioni di utilizzo dei nuovi Singoli Servizi (di seguito âTermini e Condizioni di Utilizzoâ) sono fornite da TPAY al Cliente ai sensi dellâart. 4.4 del Contratto Telepass PAY pacchetto Plus (di seguito il âContratto TPAYâ) e hanno lo scopo di illustrare le caratteristiche generali e le relative modalitÃ  di utilizzo, che rimangono comunque soggette alle Norme e Condizioni previste dal Contratto dal Foglio Informativo, dal Documento di Sintesi e dalla restante...","link":"https://storage.googleapis.com/tlp-skidata-dev.appspot.com/norme_e_condizioni_telepass_pay_skipass.html","mandatory":true,"serviceId":33,"title":"Nuove norme di utilizzo","tosId":1},{"builder":{},"description":"I presenti termini e condizioni di utilizzo dei nuovi Singoli Servizi (di seguito âTermini e Condizioni di Utilizzoâ) sono fornite da TPAY al Cliente ai sensi dellâart. 4.4 del Contratto Telepass PAY pacchetto Plus (di seguito il âContratto TPAYâ) e hanno lo scopo di illustrare le caratteristiche generali e le relative modalitÃ  di utilizzo, che rimangono comunque soggette alle Norme e Condizioni previste dal Contratto dal Foglio Informativo, dal Documento di Sintesi e dalla restante...","link":"https://storage.googleapis.com/tlp-skidata-dev.appspot.com/norme_e_condizioni_telepass_pay_skipass.html","mandatory":true,"serviceId":33,"title":"Norme di inutilizzo","tosId":2},{"builder":{},"description":"Nuove N&C!\n\nI presenti termini e condizioni di utilizzo dei nuovi Singoli Servizi (di seguito âTermini e Condizioni di Utilizzoâ) sono fornite da TPAY al Cliente ai sensi dellâart. 4.4 del Contratto Telepass PAY pacchetto Plus (di seguito il âContratto TPAYâ) e hanno lo scopo di illustrare le caratteristiche generali e le relative modalitÃ  di utilizzo, che rimangono comunque soggette alle Norme e Condizioni previste dal Contratto dal Foglio Informativo, dal Documento di Sintesi e dalla restante...","link":"https://storage.googleapis.com/tlp-skidata-dev.appspot.com/norme_e_condizioni_telepass_pay_skipass.html","mandatory":true,"serviceId":33,"title":"Nuove norme di inutilizzo","tosId":2},{"builder":{},"description":"I presenti termini e condizioni di utilizzo dei nuovi Singoli Servizi (di seguito âTermini e Condizioni di Utilizzoâ) sono fornite da TPAY al Cliente ai sensi dellâart. 4.4 del Contratto Telepass PAY pacchetto Plus (di seguito il âContratto TPAYâ) e hanno lo scopo di illustrare le caratteristiche generali e le relative modalitÃ  di utilizzo, che rimangono comunque soggette alle Norme e Condizioni previste dal Contratto dal Foglio Informativo, dal Documento di Sintesi e dalla restante...","link":"https://storage.googleapis.com/tlp-skidata-dev.appspot.com/norme_e_condizioni_telepass_pay_skipass.html","mandatory":true,"serviceId":33,"title":"Paste alle norme","tosId":3}],"result":"ok","status":200}
    """