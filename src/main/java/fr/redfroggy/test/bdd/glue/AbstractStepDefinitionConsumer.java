package fr.redfroggy.test.bdd.glue;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import fr.redfroggy.test.bdd.scope.ScenarioScope;
import gherkin.JSONParser;
import gherkin.deps.com.google.gson.Gson;
import org.apache.coyote.http2.Http2Error;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

/**
 * Abstract step definition implementation
 * Http request are made using {@link RestTemplate} RestTemplate
 */
@SuppressWarnings("unchecked")
abstract class AbstractStepDefinitionConsumer {

    //Stored base uri
    String baseUri;

    //Parsed http request json body
    Map<String, Object> body;
    List<Map<String, Object>> body_list;


    //Rest template
    private RestTemplate template;

    //for new request patch
    private TestRestTemplate restTemplate;


    //Http headers
    private HttpHeaders headers;

    //List of query params
    private Map<String, String> queryParams;

    //Stored http response
    private ResponseEntity<String> responseEntity;
    private ObjectMapper objectMapper;

    //Scenario Scope
    private ScenarioScope scenarioScope;

    // token jwt
    public String globalToken = null;
    public int globalCustomerCode = 0;
    public int globalContractCode = 0;
    public int globalApparatusCode = 0;
    public String globalCardCode = null;
    public String    globalTransactionCode = null;

    AbstractStepDefinitionConsumer() {
        template = new RestTemplate();
        objectMapper = new ObjectMapper();
        scenarioScope = new ScenarioScope();
        headers = new HttpHeaders();
        queryParams = new HashMap<>();
    }

    /**
     * Add an http header
     * {@link #headers}
     *
     * @param name  header name
     * @param value header value
     */
    void setHeader(String name, String value) {
        Assert.notNull(name);
        Assert.notNull(value);
        headers.set(name, value);
    }

    /**
     * Add an HTTP query parameter
     * {@link #queryParams}
     *
     * @param newParams Map of parameters
     */
    void addQueryParameters(Map<String, String> newParams) {
        Assert.notNull(newParams);
        Assert.isTrue(!newParams.isEmpty());
        queryParams.putAll(newParams);
    }

    /**
     * Add multiple http headers
     * {@link #headers}
     *
     * @param newHeaders Map of headers
     */
    void addHeaders(Map<String, String> newHeaders) {
        Assert.notNull(newHeaders);
        Assert.isTrue(!newHeaders.isEmpty());
        newHeaders.entrySet().forEach(headerEntry -> {

            List<String> headerValues = this.headers.get(headerEntry.getKey());
            if (headerValues == null) {
                headerValues = Collections.singletonList(headerEntry.getValue());
            } else {
                headerValues.add(headerEntry.getValue());
            }
            this.headers.put(headerEntry.getKey(), headerValues);
        });
    }

    /**
     * Set the http request body (POST request for example)
     * {@link #body}
     *
     * @param body json string body
     * @throws IOException json parse exception
     */
    void setBody(String body) throws IOException {
        Assert.notNull(body);
        Assert.isTrue(!body.isEmpty());
        try {
            this.body = objectMapper.readValue(body, Map.class);
        } catch (IOException e) {
           this.body_list =  objectMapper.readValue(body, List.class);;

        }
    }

    /**
     * Perform an http request
     * Store the http response to responseEntity {@link #responseEntity}
     *
     * @param resource resource to consume
     * @param method   HttpMethod
     */
    void request(String resource, HttpMethod method) {
        Assert.notNull(resource);
        Assert.isTrue(!resource.isEmpty());

        Assert.notNull(method);

        boolean writeMode = !HttpMethod.GET.equals(method)
             //   && !HttpMethod.DELETE.equals(method)
                && !HttpMethod.OPTIONS.equals(method)
                && !HttpMethod.HEAD.equals(method);

        if (!resource.contains("/")) {
            resource = "/" + resource;
        }



        HttpEntity httpEntity;

        if (writeMode) {
           if(body != null) {
            httpEntity = new HttpEntity(body, headers); }
           else httpEntity = new HttpEntity(body_list, headers);

        } else {
            httpEntity = new HttpEntity(headers);
        }
        try {
            responseEntity = this.template.exchange(baseUri + resource, method, httpEntity, String.class);
            Assert.notNull(responseEntity);
        } catch (HttpStatusCodeException e) {
            responseEntity = new ResponseEntity<String>(e.getResponseBodyAsString(), e.getResponseHeaders(), e.getStatusCode());

        }
    }


    void requestWithUrlEncodedParam(String resource, HttpMethod method, MultiValueMap<String, String> map) {


        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(map, headers);


        responseEntity = this.template.exchange(baseUri + resource, method, httpEntity, String.class);
        Assert.notNull(responseEntity);

    }


    /**
     * Check http response status code
     *
     * @param status expected/unexpected status
     * @param isNot  if true, test equality, inequality if false
     */
    void checkStatus(int status, boolean isNot) {
        Assert.isTrue(status > 0);
        Assert.isTrue(isNot ? responseEntity.getStatusCodeValue() != status : responseEntity.getStatusCodeValue() == status, "Actual status value is " + responseEntity.getStatusCodeValue());
    }

    /**
     * Check header existence
     *
     * @param headerName name of the header to find
     * @param isNot      if true, test equality, inequality if false
     * @return header values if found, null otherwise
     */
    List<String> checkHeaderExists(String headerName, boolean isNot) {
        Assert.notNull(headerName);
        Assert.isTrue(!headerName.isEmpty());
        Assert.notNull(responseEntity.getHeaders());
        if (!isNot) {
            Assert.notNull(responseEntity.getHeaders().get(headerName));
            return responseEntity.getHeaders().get(headerName);
        } else {
            Assert.isNull(responseEntity.getHeaders().get(headerName));
            return null;
        }
    }

    /**
     * Test header value
     *
     * @param headerName  name of the header to test
     * @param headerValue expected/unexpected value
     * @param isNot       if true, test equality, inequality if false
     */
    void checkHeaderEqual(String headerName, String headerValue, boolean isNot) {
        Assert.notNull(headerName);
        Assert.isTrue(!headerName.isEmpty());

        Assert.notNull(headerValue);
        Assert.isTrue(!headerValue.isEmpty());

        Assert.notNull(responseEntity.getHeaders());

        if (!isNot) {
            Assert.isTrue(responseEntity.getHeaders().get(headerName).contains(headerValue));
        } else {
            Assert.isTrue(!responseEntity.getHeaders().get(headerName).contains(headerValue));
        }
    }

    /**
     * Test response body is json typed
     * {@link #responseEntity}
     *
     * @throws IOException json parse exception
     */
    void checkJsonBody() throws IOException {
        String body = responseEntity.getBody();
        Assert.notNull(body);
        Assert.isTrue(!body.isEmpty());
        // body="[sdsdsdsd]";
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(body);
        } catch (IOException e) {
            Assert.isTrue(false, "No valid json: " + body);
        }
    }

    /**
     * Test body content
     * {@link #responseEntity}
     *
     * @param bodyValue expected content
     */
    void checkBodyContains(String bodyValue) {
        Assert.notNull(bodyValue);
        Assert.isTrue(!bodyValue.isEmpty());

        Assert.isTrue(responseEntity.getBody().contains(bodyValue), "The body is: " + responseEntity.getBody());
    }

    void checkBodyNotContains(String bodyValue) {
        Assert.isTrue(!responseEntity.getBody().contains(bodyValue), "The body is: " + responseEntity.getBody());
    }

    /**
     * Test body content
     * {@link #responseEntity}
     *
     * @param bodyValue expected content
     */
    void checkBodyEquals(String bodyValue) {
        Assert.notNull(bodyValue);
        Assert.isTrue(!bodyValue.isEmpty());
        Assert.isTrue(responseEntity.getBody().equals(bodyValue.trim()), "The body is: " + responseEntity.getBody() + "\nbut expected is: " + bodyValue);
    }

    /**
     * Test json path validity
     *
     * @param jsonPath json path query
     * @return value found using <code>jsonPath</code>
     */
    Object checkJsonPathExists(String jsonPath) {
        return getJsonPath(jsonPath);
    }

    /**
     * Test json path value
     *
     * @param jsonPath  json path query
     * @param jsonValue expected/unexpected json path value
     * @param isNot     if true, test equality, inequality if false
     */
    void checkJsonPath(String jsonPath, String jsonValue, boolean isNot) {
        Object pathValue = checkJsonPathExists(jsonPath);
        Assert.isTrue(!String.valueOf(pathValue).isEmpty());

        if (!isNot) {
            Assert.isTrue(pathValue.equals(jsonValue));
        } else {
            Assert.isTrue(!pathValue.equals(jsonValue));
        }
    }

    /**
     * Test json path is array typed and its size is matching the expected length
     *
     * @param jsonPath json path query
     * @param length   expected length (-1 to not control the size)
     */
    void checkJsonPathIsArray(String jsonPath, int length) {
        Object pathValue = getJsonPath(jsonPath);
        Assert.isTrue(pathValue instanceof Collection);
        if (length != -1) {
            Assert.isTrue(((Collection) pathValue).size() == length);
        }
    }

    /**
     * Store a given header in the scenario scope using the given alias
     *
     * @param headerName  header to save
     * @param headerAlias new header name in the scenario scope
     */
    void storeHeader(String headerName, String headerAlias) {

        Assert.notNull(headerName);
        Assert.isTrue(!headerName.isEmpty());

        Assert.notNull(headerAlias);
        Assert.isTrue(!headerAlias.isEmpty());

        List<String> headerValues = checkHeaderExists(headerName, false);
        Assert.notNull(headerValues);
        Assert.isTrue(!headerValues.isEmpty());

        scenarioScope.getHeaders().put(headerAlias, headerValues);
    }

    /**
     * Store a json path value using the given alias
     *
     * @param jsonPath      json path query
     * @param jsonPathAlias new json path alias in the scenario scope
     */
    void storeJsonPath(String jsonPath, String jsonPathAlias) {
        Assert.notNull(jsonPath);
        Assert.isTrue(!jsonPath.isEmpty());

        Assert.notNull(jsonPathAlias);
        Assert.isTrue(!jsonPathAlias.isEmpty());

        Object pathValue = getJsonPath(jsonPath);
        scenarioScope.getJsonPaths().put(jsonPathAlias, pathValue);
    }

    /**
     * Test a scenario variable existence
     *
     * @param property name of the variable
     * @param value    expected value
     */
    void checkScenarioVariable(String property, String value) {
        Assert.isTrue(scenarioScope.checkProperty(property, value));
    }

    /**
     * Parse the http response json body
     *
     * @return ReadContext instance
     */
    private ReadContext getBodyDocument() {
        //Object document = Configuration.defaultConfiguration().jsonProvider().parse();
        ReadContext ctx = JsonPath.parse(responseEntity.getBody());
        Assert.notNull(ctx);

        return ctx;
    }

    /**
     * Get values for a given json path query and the http response body
     *
     * @param jsonPath json path query
     * @return json path value
     */
    private Object getJsonPath(String jsonPath) {

        Assert.notNull(jsonPath);
        Assert.isTrue(!jsonPath.isEmpty());

        ReadContext ctx = getBodyDocument();
        Object pathValue = ctx.read(jsonPath);

        Assert.notNull(pathValue);

        return pathValue;
    }

    /**
     * Store http token code
     */
    void storeAccessToken() throws JSONException {
        //   Assert.isTrue(status > 0);
        //  Assert.isTrue(isNot ? responseEntity.getStatusCodeValue() != status : responseEntity.getStatusCodeValue() == status,"Actual status value is " + responseEntity.getStatusCodeValue());
        //   storeHeader("jwtToken","jwt");
        String responseBody = responseEntity.getBody();
        final JSONObject obj = new JSONObject(responseBody);
        final String jwtTokenFromJsonLogin = obj.getString("jwtToken");

        //  storeHeader("jwtToken","jwtTokenFromJsonLogin");
        globalToken = jwtTokenFromJsonLogin;
        System.out.println("jwtToken:   " + globalToken);


    }

    void storeAccessTokenRdPartyServerLogin() throws JSONException {
        String responseBody = responseEntity.getBody();
        final JSONObject obj = new JSONObject(responseBody);
        final String jwtTokenFromJsonLogin = obj.getString("access_token");

        //  storeHeader("jwtToken","jwtTokenFromJsonLogin");
        globalToken = jwtTokenFromJsonLogin;
        System.out.println("access_token:   " + globalToken);


    }

    void storeCustomerCodeAndContractCodeFromUaaLogin() throws JSONException {
        String responseBody = responseEntity.getBody();
        final JSONObject obj = new JSONObject(responseBody);
        final String contract_code_associated = obj.getString("contract_code_associated");
        globalContractCode = Integer.parseInt(contract_code_associated);
        System.out.println("contract_code_associated:   " + globalContractCode);

        final String customer_code = obj.getString("customer_code");
        globalCustomerCode = Integer.parseInt(customer_code);
        System.out.println("customer_code:   " + globalCustomerCode);


    }




    void storeCustomerCode() throws JSONException {

        String responseBody = responseEntity.getBody();
        final JSONObject obj = new JSONObject(responseBody);
        final int customerCodeFromJsonLogin = JsonPath.read(responseBody, "$.infoCliente.datiCliente.codiceCliente");
        globalCustomerCode = customerCodeFromJsonLogin;
        System.out.println("CustomerCode:   " + globalCustomerCode);

    }

    void storeContractCode() throws JSONException {

        String responseBody = responseEntity.getBody();
        final JSONObject obj = new JSONObject(responseBody);
        final int contractCodeFromJsonLogin = JsonPath.read(responseBody, "$.contrattoDefault.codiceContratto");
        globalContractCode = contractCodeFromJsonLogin;
        System.out.println("codiceContratto:   " + globalContractCode);

    }

    void storeApparatusCode() throws JSONException {

        String responseBody = responseEntity.getBody();
        final JSONObject obj = new JSONObject(responseBody);
        final int apparatusCodeFromJsonContract = JsonPath.read(responseBody, "$.titoli[0].supporto.codiceApparato");
        globalApparatusCode = apparatusCodeFromJsonContract;
        System.out.println("apparatusCode:   " + globalApparatusCode);

    }
    void storeCardCode() throws JSONException {

        String responseBody = responseEntity.getBody();
        final JSONObject obj = new JSONObject(responseBody);
        final int cardCode = JsonPath.read(responseBody, "$.data[0].cardCodeTodo");
        globalCardCode = String.valueOf(cardCode);
        System.out.println("cardCode:   " + cardCode);

    }

    void storeTransactionCode() throws JSONException {

        String responseBody = responseEntity.getBody();
        final JSONObject obj = new JSONObject(responseBody);
        final String TransCode = JsonPath.read(responseBody, "$.id");
        globalTransactionCode = String.valueOf(TransCode);
        System.out.println("globalTransactionCode:   " + globalTransactionCode);

    }



    /**
     * Return stored token code
     */
    void getStoredToken() {
        //   Assert.isTrue(status > 0);
        //  Assert.isTrue(isNot ? responseEntity.getStatusCodeValue() != status : responseEntity.getStatusCodeValue() == status,"Actual status value is " + responseEntity.getStatusCodeValue());
        boolean sdf = scenarioScope.getProperty("jwt");
        int yyy = 1;
        int ydyy = 1;
        //  return responseEntity.getStatusCodeValue();
    }


    /**
     * Return toDay Date
     *
     * @return
     */
    Date getTodayDate() throws JSONException {
        //  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date todayDate = cal.getTime();
        //  String todaydateString = dateFormat.format(todayDate);
        //   System.out.println("today is:   " + todaydateString);
        return todayDate;
        //    this.getExpirationDateFromVehicle();
    }


    public static Date addDays(Date date, int days) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);

        return cal.getTime();
    }

    /**
     * Return first expiring Date
     *
     * @return
     */
    void checkExpirationDateAndStatusFromVehicle() throws JSONException {


        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String responseBody = responseEntity.getBody();
        final JSONArray obj = new JSONArray(responseBody);
        Date todayDate = getTodayDate();
        System.out.println("No results for path expirationDate or state.");
        int arratLength = obj.length();
        for (int i = 0; i < arratLength; i++) {
            String status = "UNKNOWN";
            String expectedStatus = "UNKNOWN";

            try {
                String expDateString = JsonPath.read(responseBody, "$[" + i + "].policy.expirationDate");
                status = JsonPath.read(responseBody, "$[" + i + "].policy.status");
                String source = JsonPath.read(responseBody, "$[" + i + "].policy.source");


                Date expDate = dateFormat.parse(expDateString);

                System.out.println("expDateString:   " + expDateString);
                System.out.println("status:   " + status);
                System.out.println("source:   " + source);


                if (addDays(todayDate, -15).compareTo(expDate) < 0 && addDays(todayDate, 30).compareTo(expDate) > 0) {
                    expectedStatus = "EXPIRING";
                } else if (source.equals("EXTERNAL") && addDays(todayDate, 30).compareTo(expDate) < 0) {
                    expectedStatus = "UNKNOWN";
                } else if ((source.equals("MGA") || source.equals("BROKER")) && addDays(todayDate, 30).compareTo(expDate) < 0) {
                    expectedStatus = "ACTIVE";
                }

            } catch (Exception e) {
                System.out.println("No results for path expirationDate or state.");
                //  Assert.isTrue(false, "Expected status and expired date");

            }


            Assert.isTrue(status.equals(expectedStatus), "Expected status: " + expectedStatus);


        }
        //   return expDate;
        //    System.out.println("ExpDate:   " + expDate);


    }


    void request_new(String resource, HttpMethod method) {

        // workaround fot PATCH with
        // Add Apache HttpClient as TestRestTemplate
        //  this.template = restTemplate.getRestTemplate();
        HttpClient httpClient = HttpClientBuilder.create().build();
        this.template.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
        // End Step #2


        if (!resource.contains("/")) {
            resource = "/" + resource;
        }


        HttpEntity httpEntity;
        if(body != null) {
            httpEntity = new HttpEntity(body, headers); }
           else httpEntity = new HttpEntity(body_list, headers);

      //  httpEntity = new HttpEntity(body_list, headers);


        try {
            responseEntity = this.template.exchange(baseUri + resource, method, httpEntity, String.class);
            Assert.notNull(responseEntity);
        } catch (HttpStatusCodeException e) {
            responseEntity = new ResponseEntity<String>(e.getResponseBodyAsString(), e.getResponseHeaders(), e.getStatusCode());

        }
    }


}
