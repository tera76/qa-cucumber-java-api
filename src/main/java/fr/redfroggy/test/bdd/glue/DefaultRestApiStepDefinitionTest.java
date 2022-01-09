package fr.redfroggy.test.bdd.glue;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;


/**
 * Default step definition for consuming a rest api
 * {@link ContextConfiguration} ContextConfiguration and {@link SpringBootTest} @SpringBootTest annotation
 * are mandatory to be able to run cucumber unit test on spring rest controllers
 */
@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class DefaultRestApiStepDefinitionTest extends AbstractStepDefinitionConsumer {

    /**
     * url variables
     */

    // skipass
    // String skipass_api_gateway = "https://skipass-api-test.wisetools.it/api";
    //  String skipass_api_gateway = "https://skipass-api-test.wisetools.it/api";
    String skipass_api = "https://skipass-api-test.wisetools.it/api";
    String skipass_api_gateway = "https://skipass-gateway-api-test.wisetools.it/api";
    //   String skipass_api_gateway = "http://localhost:8081/api";

    //skipass test env
    String skidata_dev_api_gateway = "https://skidata-dev.wisetools.it/api";
    // skipass customer friends
    //  String skidata_customer_friends_dev_api_gateway = "https://customer-friends-api-dev.wisetools.it/api";
    String skidata_customer_friends_dev_api_gateway = "https://customer-friends-api-test.wisetools.it/api";
    // String skidata_customer_friends_dev_api_gateway = "http://localhost:8082/api";
    String skidata_customer_dev_api_gateway = "http://localhost:8085/api";


    // mga
    String rca_api_gateway = "https://rca-gateway-api-dev.wisetools.it/api";
    String customer_api_gateway = "https://customer-gateway-api-dev.wisetools.it/api";
    String broker_rca_policy = "https://broker-rca-policy-dev.wisetools.it/api";


    /**
     * First step is to retrieve the base uri
     *
     * @param uri base uri
     */
    @Given("^baseUri is (.*)$")
    public void baseUri(String uri) {
        Assert.notNull(uri);
        Assert.isTrue(!uri.isEmpty());
        baseUri = uri;
    }

    /**
     * Set baseUri for skipass gateway
     */
    @Given("^I set base uri for skipass gateway$")
    public void setBaseUriForSkipassGateway() {

        baseUri = skipass_api_gateway;

    }

    /**
     * Set baseUri for skipass api
     */
    @Given("^I set base uri for skipass api$")
    public void setBaseUriForSkipassApi() {

        baseUri = skipass_api;

    }

    /**
     * Set baseUri for skidata dev gateway
     */
    @Given("^I set base uri for skidata dev gateway$")
    public void setBaseUriForSkidataTestGateway() {
        baseUri = skidata_dev_api_gateway;
    }

    /**
     * Set baseUri for customer friends dev gateway
     */
    @Given("^I set base uri for skipass customer friends gateway$")
    public void setBaseUriForSkipassCustomerFriendsGateway() {
        baseUri = skidata_customer_friends_dev_api_gateway;
    }

    /**
     * Set baseUri for skipass customer gateway
     */
    @Given("^I set base uri for skipass customer gateway$")
    public void setBaseUriForSkipassCustomerGateway() {
        baseUri = skidata_customer_dev_api_gateway;
    }

    /**
     * Set baseUri for rca gateway
     */
    @Given("^I set base uri for rca gateway$")
    public void setBaseUriForRcaGateway() {

        baseUri = rca_api_gateway + "/v1";

    }

    /**
     * Set baseUri for broker rca policy
     */
    @Given("^I set base uri for broker rca policy$")
    public void setBaseUriForBrokerRcaPolicy() {

        baseUri = broker_rca_policy + "/v1";

    }

    /**
     * Set baseUri for rca healt gateway
     */
    @Given("^I set base uri for rca healt gateway$")
    public void setBaseUriForRcaHealtGateway() {
        baseUri = rca_api_gateway + "health";

    }

    /**
     * Set baseUri for customer gateway
     */
    @Given("^I set base uri for customer gateway$")
    public void setBaseUriForCustomerGateway() {
        baseUri = customer_api_gateway + "/v1";

    }

    /**
     * Set the request body
     * A json string structure is accepted
     * The body will be parse to be sure the json is valid
     *
     * @param body body to send
     * @throws IOException parsing exception
     */
    @Given("^I set body to (.*)$")
    public void setBodyTo(String body) throws IOException {
        this.setBody(body);
    }

    @Given("^I set body to$")
    public void setBodyToDocString(String body) throws IOException {
        this.setBody(body);
    }

    /**
     * Add a new http header
     *
     * @param headerName  header name
     * @param headerValue header value
     */
    @Given("^I set (.*) header to (.*)$")
    public void header(String headerName, String headerValue) {
        this.setHeader(headerName, headerValue);
    }

    /**
     * Add a list of query parameter to the url
     * Gherkin table can be used to pass several headers
     *
     * @param parameters Map of parameters with name and value
     */
    @Given("^I set query parameters to:$")
    public void queryParameters(Map<String, String> parameters) {
        this.addQueryParameters(parameters);
    }

    /**
     * Add multiple http headers
     *
     * @param parameters Map of headers to send with name and value
     */
    @Given("^I set headers to:$")
    public void headers(Map<String, String> parameters) {
        this.addHeaders(parameters);
    }

    /**
     * Perform an HTTP GET request
     * Url will be baseUri+resource
     * The trailing slash is checked, so the value can be "/resource" or "resource"
     *
     * @param resource resource name
     */
    @When("^I GET (.*)$")
    public void get(String resource) {
        this.request(resource, HttpMethod.GET);
    }


    /**
     * Perform an HTTP POST request. It supposes that a body exists,
     * i.e that {@link #setBodyTo} must have been called
     * Url will be baseUri+resource
     * The trailing slash is checked, so the value can be "/resource" or "resource"
     *
     * @param resource resource name
     */
    @When("^I POST (.*)$")
    public void post(String resource) {
        this.request(resource, HttpMethod.POST);
    }

    /**
     * Perform an HTTP PUT request. It supposes that a body exists,
     * i.e that {@link #setBodyTo} must have been called
     * Url will be baseUri+resource
     * The trailing slash is checked, so the value can be "/resource" or "resource"
     *
     * @param resource resource name
     */
    @When("^I PUT (.*)$")
    public void put(String resource) {
        this.request(resource, HttpMethod.PUT);
    }

    /**
     * Perform an HTTP DELETE request. It supposes that a body exists,
     * i.e that {@link #setBodyTo} must have been called
     * Url will be baseUri+resource
     * The trailing slash is checked, so the value can be "/resource" or "resource"
     *
     * @param resource resource name
     */
    @When("^I DELETE (.*)$")
    public void delete(String resource) {
        this.request(resource, HttpMethod.DELETE);
    }

    /**
     * Perform an HTTP PATCH request. It supposes that a body exists,
     * i.e that {@link #setBodyTo} must have been called
     * Url will be baseUri+resource
     * The trailing slash is checked, so the value can be "/resource" or "resource"
     *
     * @param resource resource name
     */
    @When("^I PATCH (.*)$")
    public void patch(String resource) {

        // header("X-HTTP-Method-Override","PATCH");


        this.request_new(resource, HttpMethod.PATCH);

    }

    /**
     * Perform an HTTP OPTIONS request.
     * Url will be baseUri+resource
     * The trailing slash is checked, so the value can be "/resource" or "resource"
     *
     * @param resource resource name
     */
    @When("^I request OPTIONS for $resource$")
    public void options(String resource) {
        this.request(resource, HttpMethod.OPTIONS);
    }

    /**
     * Perform an HTTP HEAD request.
     * Url will be baseUri+resource
     * The trailing slash is checked, so the value can be "/resource" or "resource"
     *
     * @param resource resource name
     */
    @When("^I request HEAD for $resource$")
    public void head(String resource) {
        this.request(resource, HttpMethod.HEAD);
    }

    /**
     * Test response status code is equal to a given status
     *
     * @param status status code to test
     */
    @Then("^response code should be (\\d+)$")
    public void responseCode(Integer status) {
        this.checkStatus(status, false);
    }

    /**
     * Test response status code is not equal to a given code
     *
     * @param status status code to test
     */
    @Then("^response code should not be (\\d+)$")
    public void notResponseCode(Integer status) {
        this.checkStatus(status, true);
    }

    /**
     * Test that a given http header exists
     *
     * @param headerName name of the header to find
     */
    @Then("^response header (.*) should exist$")
    public void headerExists(String headerName) {
        this.checkHeaderExists(headerName, false);
    }

    /**
     * Test that a given http header does not exists
     *
     * @param headerName name of the header to not find
     */
    @Then("^response header (.*) should not exist$")
    public void headerNotExists(String headerName) {
        this.checkHeaderExists(headerName, true);
    }

    /**
     * Test if a given header value is matching the expected value
     *
     * @param headerName  name of the header to find
     * @param headerValue expected value of the header
     */
    @Then("^response header (.*) should be (.*)$")
    public void headerEqual(String headerName, String headerValue) {
        this.checkHeaderEqual(headerName, headerValue, false);
    }

    /**
     * Test if a given header value is not matching the expected value
     *
     * @param headerName  name of the header to find
     * @param headerValue unexpected value of the header
     */
    @Then("^response header (.*) should not be (.*)$")
    public void headerNotEqual(String headerName, String headerValue) {
        this.checkHeaderEqual(headerName, headerValue, true);
    }

    /**
     * Test if the response body is a valid json.
     * The string response is parsed as a JSON object ot check the integrity
     *
     * @throws IOException if the body is not json format
     */
    @Then("^response body should be valid json$")
    public void bodyIsValid() throws IOException {
        this.checkJsonBody();
    }

    /**
     * Test if the response body contains a given value
     *
     * @param bodyValue value which the body must contain
     * @throws IOException json parse exception
     */
    @Then("^response body should contain (.*)$")
    public void bodyContains(String bodyValue) throws IOException {
        this.checkBodyContains(bodyValue);
    }

    @Then("^response body should not contain (.*)$")
    public void bodyNotContains(String bodyValue) throws IOException {
        this.checkBodyNotContains(bodyValue);
    }

    @Then("^response body should be equal to$")
    public void bodyIsEqual(String bodyValue) throws IOException {
        this.checkBodyEquals(bodyValue);
    }

    /**
     * Test the given json path query exists in the response body
     *
     * @param jsonPath json path query
     * @throws IOException json parse exception
     */
    @Then("^response body path (.*) should exists$")
    public void bodyPathExists(String jsonPath) throws IOException {
        this.checkJsonPathExists(jsonPath);
    }

    /**
     * Test the given json path exists in the response body and match the given value
     *
     * @param jsonPath json path query
     * @param value    expected value
     * @throws IOException json parse exception
     */
    @Then("^response body path (.*) should be (.*)$")
    public void bodyPathEqual(String jsonPath, String value) throws IOException {
        this.checkJsonPath(jsonPath, value, false);
    }

    /**
     * Test the given json path exists and does not match the given value
     *
     * @param jsonPath json path query
     * @param value    unexpected value
     * @throws IOException json parse exception
     */
    @Then("^response body path (.*) should not be (.*)$")
    public void bodyPathNotEqual(String jsonPath, String value) throws IOException {
        this.checkJsonPath(jsonPath, value, true);
    }

    /**
     * Test if the json path exists in the response body and is array typed
     *
     * @param jsonPath json path query
     * @throws IOException json parse exception
     */
    @Then("^response body is typed as array for path (.*)$")
    public void bodyPathIsArray(String jsonPath) throws IOException {
        this.checkJsonPathIsArray(jsonPath, -1);
    }

    /**
     * Test if the json path exists in the response body, is array typed and as the expected length
     *
     * @param jsonPath json path query
     * @param length   expected length
     * @throws IOException json parse exception
     */
    @Then("^response body is typed as array using path (.*) with length (\\d+)$")
    public void bodyPathIsArrayWithLength(String jsonPath, int length) throws IOException {
        this.checkJsonPathIsArray(jsonPath, length);
    }

    /**
     * Store a given response header to the scenario scope
     * The purpose is to reuse its value in another scenario
     * The most common use case is the authentication process
     *
     * @param headerName  http header name
     * @param headerAlias http header alias (which will be stored in the scenario scope)
     * @throws IOException json parse exception
     * @see fr.redfroggy.test.bdd.scope.ScenarioScope
     */
    @Then("^I store the value of response header (.*) as (.*) in scenario scope$")
    public void storeResponseHeader(String headerName, String headerAlias) throws IOException {
        this.storeHeader(headerName, headerAlias);
    }

    /**
     * Store a given json path value to the scenario scope
     * The purpose is to reuse its value in another scenario
     * The most common use case is the authentication process
     *
     * @param jsonPath      json path query
     * @param jsonPathAlias json path alias (which will be stored in the scenario scope)
     * @throws IOException json parse exception
     * @see fr.redfroggy.test.bdd.scope.ScenarioScope
     */
    @Then("^I store the value of body path (.*) as (.*) in scenario scope$")
    public void storeResponseJsonPath(String jsonPath, String jsonPathAlias) throws IOException {
        this.storeJsonPath(jsonPath, jsonPathAlias);
    }

    /**
     * Test a scenario scope variable value match the expected one
     *
     * @param property scenario scope property
     * @param value    expected property value
     * @throws IOException json parse exception
     * @see fr.redfroggy.test.bdd.scope.ScenarioScope
     */
    @Then("^value of scenario variable (.*) should be (.*)$")
    public void scenarioVariableIsValid(String property, String value) throws IOException {
        this.checkScenarioVariable(property, value);
    }


    @Given("^I set standard header$")
    public void iSetStandardHeader() throws Throwable {

        header("Accept", "application/json");
        header("Content-Type", "application/json; charset=UTF-8");
        // do to
        header("X-Sistema-Chiamante", "KTNio");

    }

    @Given("^I set auth token for customer gateway$")
    public void iSetAuthTokenForCustomerGateway() throws Throwable {

        String token = "eyJraWQiOiJraWQtdGVzdCIsInR5cCI6IkpXVCIsImFsZyI6IlJTMjU2In0.eyJhdWQiOiJ3aXNlLmN1c3RvbWVyLWdhdGV3YXkiLCJzdWIiOiJ0ZXN0LnJjYS1nYXRld2F5IiwiaXNzIjoid2lzZS5jdXN0b21lci1nYXRld2F5IiwiZ3JvdXBzIjpbInYxLnRlc3Qud2lzZSJdLCJleHAiOjQwOTk3NjI4MDAsImlhdCI6MTU5NDA0NzIwNn0.rMpGiYeU9HvwTyu7AV46cMpxTCjkg6f7B1_8PaZ7UYXEJlMgCngh0KChaHp1NFHuMpgODauGpWqnlVxT-hL0DDsXBQLQP4rB1OJ-6Q7H1TgAooQ9_PdLVMnaCzsn8chPo7bdoFe9Cbv7hoJ87bW29prWndukA6OGud7JU9UDFikmxleJoR7OEmyO5v6rXoLip-6V6N3b2-X56tWSz_EVzAIFkZJH7l6sAV-OHocf_wiRg_U2Ab6tUxkQJ_C7Qv4-9c2DGeonbZgwOa9UVizrVExsRmrcy7EqsmBxhY-E_7UGJGYTiq7K1wt3tj98j7iO5PAAIwJaX1vpcfBEHG6isw";
        header("Authorization", "Bearer " + token);

    }

    @Given("^I have a valid token for username (.*)$")
    public void iHaveValidTokenForUsername(String username) throws Throwable {
        setBody("{ \"userId\": \"" + username + "\", \"password\": \"" + username + "\", \"registraAccesso\": true, \"fromApp\": true }\n");
        this.request("/utente/login", HttpMethod.POST);
        this.storeAccessToken();
        String ciccio = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjpudWxsLCJpc0NvbnRyYWN0SG9sZGVyIjp0cnVlLCJpc1dlYk1haW5Vc2VyIjp0cnVlLCJ1c2VyX25hbWUiOiJnaW9yZ2lvLnZlcmRlMjgiLCJ0aXRsZSI6MzUxODcyNzcsImRldmljZUlkIjpudWxsLCJ1c2VySWRlbnRpZmljYXRpb25Db2RlIjoiMDY4OTc5MjMtN0ZCOS00MkVELTlDNTYtMzRGRDAwNzIxMEQ4IiwiYXV0aG9yaXRpZXMiOlsiREVWSUNFX1NVQlNDUklCRVIiXSwiY2xpZW50X2lkIjoidHBheS1hcHAiLCJwcm9kdWN0Q29kZSI6IkZCIiwiY29udHJhY3RfY29kZV9hc3NvY2lhdGVkIjozMDAwMzUxMywicHluZyI6IjEwMDAwMDkxODEiLCJzY29wZSI6WyJwcm9maWxlIiwiZGV2aWNlSWQiLCJ1c2VySWRlbnRpZmljYXRpb25Db2RlIiwicHJvZmlsZVRscCJdLCJwZXJzb25JZCI6IjI1MjY3MDM4IiwiZW5hYmxlZFNlcnZpY2VzIjpbMTIsMjYsMzgsNTddLCJleHAiOjE2MDcwOTQ2NjIsImNvbnRyYWN0Q29kZSI6NzUwMDAxMDYxLCJjdXN0b21lcl9jb2RlIjoyNTI2NzAzOCwianRpIjoiYzdlZWUwNWQtN2EzMi00ZDIxLWE0YTgtYmI3YTZhZGFmYTc0IiwiaXNUcnlBbmRCdXkiOmZhbHNlfQ.S1WBXJVt_xBd2ULUiBYCklRzDOZUJxEVOxhm4l60k610fXigGBKgzHo6UUMuCHmkToBeHx12biALrcX7G2uA83wwHuyE7Hb98V6Mi-8NaQ-LGuVPkVSKge3f09v60_uCbyknMiXeTKmg2ORvifui5Z-_k04hiYVq4pBu9NqRgvirYFblVmivySVLk0ZPtLlq_IdrmzYZ1C93Bg8DM-jwgqm5UXtJeGQPoMv60mJkAYqaP0IOR5n9jsS4NhDXjcydCVDUsE8OkpM4xoGTdIQTqGGzU2N6iLvBjuW8p2VwYObj-2MWYp-jAb1ZpuykQISRa4F9O_SUcm3Vjgeqli17Mw";
        header("Authorization", "Bearer " + globalToken);
        //   header("Authorization", "Bearer " + ciccio);

    }

    @Given("^I have a valid token and customerCode for username (.*)$")
    public void iHaveValidTokenAndCustomerCodeForUsername(String username) throws Throwable {
        setBody("{ \"userId\": \"" + username + "\", \"password\": \"" + username + "\", \"registraAccesso\": true, \"fromApp\": true }\n");
        this.request("/utente/login", HttpMethod.POST);
        this.storeAccessToken();
        this.storeCustomerCode();
        header("Authorization", "Bearer " + globalToken);
    }

    @Given("^I have a valid token and contractCode for username (.*)$")
    public void iHaveValidTokenAndContractCodeForUsername(String username) throws Throwable {
        setBody("{ \"userId\": \"" + username + "\", \"password\": \"" + username + "\", \"registraAccesso\": true, \"fromApp\": true }\n");
        this.request("/utente/login", HttpMethod.POST);
        this.storeAccessToken();
        this.storeContractCode();
        header("Authorization", "Bearer " + globalToken);
    }

    @Then("^I write the token$")
    public void iWriteToken() throws Throwable {
        // this.getStoredToken();
        String ddd = globalToken;
        int sss = 1;
    }


    @When("^I get (.*) for customerCode$")
    public void getForCustomerCode(String resource) {
        this.request(resource + "?customerCode=" + globalCustomerCode, HttpMethod.GET);
    }

    @Then("^policy status should be coherent with expirationDate$")
    public void statusShouldBeCoherentWithExpirationDate() throws JSONException {

        this.checkExpirationDateAndStatusFromVehicle();
    }

    @When("^I post (.*) for customerCode$")
    public void postForCustomerCode(String resource) {
        this.request(resource + "?customerCode=" + globalCustomerCode, HttpMethod.POST);
    }

    @When("^I post (.*) for tera76 customer_code$")
    public void postFortera76Customer_code(String resource) {
        this.request(resource + "?tera76[customer_code]=" + globalCustomerCode, HttpMethod.POST);
    }

    @When("^I get (.*) for tera76 customer_code$")
    public void getFortera76Customer_code(String resource) {
        this.request(resource + "?tera76[customer_code]=" + globalCustomerCode, HttpMethod.GET);
    }

    @Given("^I have a valid token by 3rd-party server login$")
    public void iHaveAValidTokenBy3RdPartyServerLogin() throws Throwable {

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("grant_type", "client_credentials");
        header("Content-Type", "application/x-www-form-urlencoded");
        header("X-TPay-App-Version", "2.0.4");
        header("Authorization", "Basic d2lzZS1zZXJ2ZXI6MkE0eHg4TmdnbTJuNHkkIQ==");
        this.requestWithUrlEncodedParam("/api/uaa/oauth/token", HttpMethod.POST, map);
        this.storeAccessTokenRdPartyServerLogin();
        header("Authorization", "Bearer " + globalToken);
    }

    @Given("^I have a valid token by 3rd-party login for username (.*)$")
    public void iHaveAValidTokenBy3RdPartyLoginForUsername(String username) throws Throwable {
        String password = username;
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("grant_type", "2fa");
        map.add("client_id", "tpay-app");
        map.add("username", username);
        map.add("password", password);
        map.add("device_id", "f57d7f463109a029");
        header("Authorization", "Basic dHBheS1hcHA6dHBheS1zM2NyM3Q=");
        header("X-TPay-Device-Id", "f57d7f463109a029");
        header("X-TPay-OS-Type", "ANDROID");
        header("X-TPay-OS-Version", "26");
        header("X-TPay-App-Version", "3.3.2");
        header("X-TPay-Latitude", "45.4627124");
        header("X-TPay-Longitude", "9.432343");
        header("X-TPay-GPS-Error", "79.9");
        header("X-TPay-Connection-Type", "Wi-Fi");
        header("X-TPay-Mobile-Network-Type", "3g");
        this.requestWithUrlEncodedParam("/api/uaa/oauth/token", HttpMethod.POST, map);
        this.storeAccessTokenRdPartyServerLogin();
        header("Authorization", "Bearer " + globalToken);
    }

    @Given("^I have a valid token, customerCode and contractCode by 3rd-party login for username (.*)$")
    public void iHaveAValidTokenCustomerCodeContractCodeBy3RdPartyLoginForUsername(String username) throws Throwable {
        String password = username;
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("grant_type", "2fa");
        map.add("client_id", "tpay-app");
        map.add("username", username);
        map.add("password", password);
        map.add("device_id", "f57d7f463109a029");
        header("Authorization", "Basic dHBheS1hcHA6dHBheS1zM2NyM3Q=");
        header("X-TPay-Device-Id", "f57d7f463109a029");
        header("X-TPay-OS-Type", "ANDROID");
        header("X-TPay-OS-Version", "26");
        header("X-TPay-App-Version", "3.3.2");
        header("X-TPay-Latitude", "45.4627124");
        header("X-TPay-Longitude", "9.432343");
        header("X-TPay-GPS-Error", "79.9");
        header("X-TPay-Connection-Type", "Wi-Fi");
        header("X-TPay-Mobile-Network-Type", "3g");
        this.requestWithUrlEncodedParam("/api/uaa/oauth/token", HttpMethod.POST, map);
        this.storeAccessTokenRdPartyServerLogin();
        this.storeCustomerCodeAndContractCodeFromUaaLogin();
        header("Authorization", "Bearer " + globalToken);
    }


    @Given("I set body for modifica targa with codiceContratto and apparatusCode for targa (.*)")
    public void iSetBodyForModificaTargaWithCodiceContrattoForTarga(String arg0) throws IOException {
        String body = "{\n" +
                "\t\"codiceContratto\": " + globalContractCode + ",\n" +
                "\t\"codiceSocieta\": 55,\n" +
                "\t\"nuovaTarga\": {\n" +
                "\t\t\"codiceApparato\": 782691562,\n" +
                "\t\t\"codiceContratto\": " + globalContractCode + ",\n" +
                "\t\t\"codiceNazioneTarga\": \"I\",\n" +
                "\t\t\"codiceTarga\": \"" + arg0 + "\",\n" +
                "\t\t\"dataFineValidita\": \"9999-12-31 23:59:59.999999\",\n" +
                "\t\t\"dataInizioValidita\": \"2019-03-19 12:50:19.872685\",\n" +
                "\t\t\"flagTargaRubata\": \"N\",\n" +
                "\t\t\"isStatoAreaCModificabile\": false,\n" +
                "\t\t\"livelloAdesione\": \"A\"\n" +
                "\t}\n" +
                "}";
        setBody(body);

    }

    @And("^I have a valid ApparatusCode$")
    public void iHaveAValidApparatusCode() throws Throwable {
        setBody("{\"codiceContratto\":" + globalContractCode + ",\"codiciStato\":[\"01\"],\"leggiTarghe\":true,\"statoAutorizzazione\":true}");
        this.request("/titoli/leggi-titoli-contratto", HttpMethod.POST);
        this.storeApparatusCode();

    }

    @And("^I have a cardCode for the user$")
    public void iHaveACardCodeForTheUser() throws Throwable {
        //  setBody("{\"codiceContratto\":" + globalContractCode + ",\"codiciStato\":[\"01\"],\"leggiTarghe\":true,\"statoAutorizzazione\":true}");
        this.request("/v2/cards?contractCode=" + globalContractCode + "&customerCode=" + globalCustomerCode, HttpMethod.GET);
        this.storeCardCode();

    }


    @Given("^cardCode is (.*)$")
    public void setCardCodeValue(String cardCodeValue) {

        globalCardCode = cardCodeValue;
    }


    @When("^I post (.*) for cardCode contractCode and customerCode$")
    public void postForCardCodeContractCodeCustomerCode(String resource) {
        this.request(resource + "/" + globalCardCode + "?customerCode=" + globalCustomerCode + "&contractCode=" + globalContractCode, HttpMethod.POST);

    }

    @When("^I post /v2/payment/axess/card/entrance for card code$")
    public void postEntranceForCardCode() throws Throwable {
        this.request("/v2/payment/axess/" + globalCardCode + "/entrance", HttpMethod.POST);
        this.storeTransactionCode();
    }

}
