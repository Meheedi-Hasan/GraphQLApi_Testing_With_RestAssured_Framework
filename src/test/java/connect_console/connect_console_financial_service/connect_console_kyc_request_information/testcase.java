package connect_console.connect_console_financial_service.connect_console_kyc_request_information;

import connect_console.Utils.Utils;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

public class testcase {

    Properties prop = new Properties();
    FileInputStream file;

    {
        try {
            file = new FileInputStream("./src/test/resources/config.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String token;

    public void queryCountryInfo() throws IOException, ConfigurationException {
        prop.load(file);
        RestAssured.baseURI = prop.getProperty("baseUrl2");

        String query = "{\"query\":\"query CoreLocalisationServiceCountryInformationFindAll {\\r\\n  coreLocalisationServiceCountryInformationFindAll {\\r\\n    id\\r\\n    region {\\r\\n      countries {\\r\\n        name\\r\\n        id\\r\\n        districts {\\r\\n          id\\r\\n          name\\r\\n          upazilas {\\r\\n            name\\r\\n            id\\r\\n            state {\\r\\n              id\\r\\n              name\\r\\n            }\\r\\n          }\\r\\n        }\\r\\n      }\\r\\n    }\\r\\n  }\\r\\n}\",\"variables\":{},\"operationName\":\"CoreLocalisationServiceCountryInformationFindAll\"}";

        Response res =
                given()
                        .contentType("application/json")
                        .body(query).
                        when()
                        .post("/graphql").
                        then().log().all()
                        .assertThat().statusCode(200).extract().response();

        JsonPath response = res.jsonPath();
        //System.out.println(res.asString());

        String value = response.get("data.coreLocalisationServiceCountryInformationFindAll").toString();
        //System.out.println("Array is "+ value);

        if(value != "null"){
            Assert.assertNotNull("Array is not null", "data.coreLocalisationServiceCountryInformationFindAll[0]");
            System.out.println("Array is not null");
        } else if (value == "null") {
            Assert.assertNull("Error: Array should not be null but showing null", "data.coreLocalisationServiceCountryInformationFindAll[0]");
            System.out.println("Error: Array should not be null but showing null");
        }else{
            System.out.println("Errors");
            Assert.fail("Errors");
        }


//        Assert.assertEquals(response.get("data.coreLocalisationServiceCountryInformationFindAll[0].id").toString(), "5e9f919f-2d42-42ec-8b6c-adf7f7b3cd0b");
//        System.out.println(response.get("data.coreLocalisationServiceCountryInformationFindAll[0].id").toString());
        //System.out.println(res.asString());
    }

    public void loginAPI() throws IOException, ConfigurationException {
        prop.load(file);
        RestAssured.baseURI = prop.getProperty("baseUrl2");

        //Mehedi Hasan account
        //String query = "{\"query\":\"query ConnectConsoleLoginWithPassword($input: ConnectConsoleLoginWithPasswordInput!) {\\r\\n  connectConsoleLoginWithPassword(input: $input) {\\r\\n    connect_id\\r\\n    device_is_verified\\r\\n    _token\\r\\n  }\\r\\n}\",\"variables\":{\"input\":{\"connect_id\":\"+8801303865986\",\"user_pass\":\"123123\"}},\"operationName\":\"ConnectConsoleLoginWithPassword\"}";
        //String query ="{\"query\":\"query ConnectConsoleLoginWithPassword($input: ConnectConsoleLoginWithPasswordInput!) {\\r\\n  connectConsoleLoginWithPassword(input: $input) {\\r\\n    connect_id\\r\\n    device_is_verified\\r\\n    _token\\r\\n  }\\r\\n}\",\"variables\":{\"input\":{\"connect_id\":\"+8801303865986\",\"user_pass\":\"123123\",\"device_token\":\"617404\"}},\"operationName\":\"ConnectConsoleLoginWithPassword\"}";
        //Mozzammel Haque acount
        String query = "{\"query\":\"query ConnectConsoleLoginWithPassword($input: ConnectConsoleLoginWithPasswordInput!) {\\n  connectConsoleLoginWithPassword(input: $input) {\\n    connect_id\\n    device_is_verified\\n    _token\\n  }\\n}\",\"variables\":{\"input\":{\"connect_id\":\"+8801674831500\",\"user_pass\":\"123123\"}},\"operationName\":\"ConnectConsoleLoginWithPassword\"}";

        Response res =
                given()
                        .contentType("application/json").header("x-device-id", prop.getProperty("x-device-id"))
                        .body(query).
                        when()
                        .post("/graphql").
                        then().log().all()
                        .assertThat().statusCode(200).extract().response();

        JsonPath response = res.jsonPath();
        token = response.get("data.connectConsoleLoginWithPassword._token");
        //System.out.println("Token is: "+token);
        Utils.setEnvVariable("token", token);
        System.out.println(res.asString());


        String value = response.get("data.connectConsoleLoginWithPassword._token").toString();
//        System.out.println("Array is "+ value);

        if(value != null){
            Assert.assertNotNull("Array is not null", "data.connectConsoleLoginWithPassword");
            System.out.println("Array is Not null");
        } else if (value == null) {
            Assert.assertNull("Error: Array should not be null but showing null", "data.connectConsoleLoginWithPassword");
            System.out.println("Error: Array should not be null but showing null");
        }else{
            System.out.println("Errors");
            Assert.fail("Errors");
        }
    }

    public void queryUserInfoFindAll() throws IOException, ConfigurationException {
        prop.load(file);
        RestAssured.baseURI = prop.getProperty("baseUrl2");

        String query = "{\"query\":\"query ConnectConsoleUserServiceUserInformationFindAll {\\r\\n  connectConsoleUserServiceUserInformationFindAll {\\r\\n    connect_id\\r\\n    last_name\\r\\n  }\\r\\n}\",\"variables\":{},\"operationName\":\"ConnectConsoleUserServiceUserInformationFindAll\"}";
        Response res =
                given()
                        .contentType("application/json").headers("Authorization", prop.getProperty("token")).header("x-device-id", prop.getProperty("x-device-id")).header("x-app-id", prop.getProperty("x-app-id"))
                        .body(query).
                        when()
                        .post("/graphql").
                        then().log().all()
                        .assertThat().statusCode(200).extract().response();

        JsonPath response = res.jsonPath();
        System.out.println(res.asString());

        String value = response.get("data.connectConsoleUserServiceUserInformationFindAll").toString();
        System.out.println("Array is "+ value);

        if(value != null){
            Assert.assertNotNull("Array is not null", "data.connectConsoleUserServiceUserInformationFindAll");
            System.out.println("Array is Not null");
        } else if (value == null) {
            Assert.assertNull("Error: Array should not be null but showing null", "data.connectConsoleUserServiceUserInformationFindAll");
            System.out.println("Error: Array should not be null but showing null");
        }else{
            System.out.println("Errors");
            Assert.fail("Errors");
        }

        Assert.assertEquals(response.get("data.connectConsoleUserServiceUserInformationFindAll[0].connect_id").toString(), "+8801521315574");
        String c_id = response.get("data.connectConsoleUserServiceUserInformationFindAll[0].connect_id").toString();
        System.out.println(c_id);
        Assert.assertEquals(response.get("data.connectConsoleUserServiceUserInformationFindAll[0].last_name").toString(), "Shuvo");
        String l_name = response.get("data.connectConsoleUserServiceUserInformationFindAll[0].last_name").toString();
        System.out.println(l_name);
    }

    public void queryKycRequestInformationClientKycRequestsByUserAndApp() throws IOException, ConfigurationException {
        prop.load(file);
        RestAssured.baseURI = prop.getProperty("baseUrl2");

        String query = "{\"query\":\"query ConnectConsoleFinancialServiceKycRequestInformationClientKycRequestsByUserAndApp($appId: String!, $input: PaginationInput!) {\\r\\n  connectConsoleFinancialServiceKycRequestInformationClientKycRequestsByUserAndApp(app_id: $appId, input: $input) {\\r\\n    result {\\r\\n      kyc_request_no\\r\\n      kyc_accounts {\\r\\n        account_holder_name\\r\\n      }\\r\\n    }\\r\\n  }\\r\\n}\",\"variables\":{\"input\":{\"skip\":0,\"take\":10},\"appId\":\"ef7551e0-936a-4727-8efd-6c77a156df9e\"},\"operationName\":\"ConnectConsoleFinancialServiceKycRequestInformationClientKycRequestsByUserAndApp\"}";
        Response res =
                given()
                        .contentType("application/json").headers("Authorization", prop.getProperty("token")).header("x-device-id", prop.getProperty("x-device-id")).header("x-app-id", prop.getProperty("x-app-id"))
                        .body(query).
                        when()
                        .post("/graphql").
                        then().log().all()
                        .assertThat().statusCode(200).extract().response();

        JsonPath response = res.jsonPath();
        System.out.println(res.asString());

        String value = response.get("data.connectConsoleFinancialServiceKycRequestInformationClientKycRequestsByUserAndApp.result").toString();
        System.out.println("Array is "+ value);

        if(value != null){
            Assert.assertNotNull("Array is Not Empty", "data.connectConsoleFinancialServiceKycRequestInformationClientKycRequestsByUserAndApp.result");
            System.out.println("Array is Not Empty");
        } else if (value == null) {
            Assert.assertNull("Error: Array should not be null but showing null", "data.connectConsoleFinancialServiceKycRequestInformationClientKycRequestsByUserAndApp.result");
            System.out.println("Error: Array should not be null but showing null");
        }else{
            System.out.println("Errors");
            Assert.fail("Errors");
        }

//        String[] name = new String[5];
//        for(int i = 0; i <= 5; i++) {
//            name[i] = response.get("data.connectConsoleFinancialServiceKycRequestInformationClientKycRequestsByUserAndApp.result["+i+"].kyc_accounts[0].account_holder_name").toString();
//            System.out.println("Account Holder name = "+name[i]);
//        }
//        Assert.assertEquals(name[1], "Mehedi Hasan");

//        JSONArray kycAccounts = response.getJSONArray("data.connectConsoleFinancialServiceKycRequestInformationClientKycRequestsByUserAndApp.result[]");
//        int arrayLength = kycAccounts.length();
//        System.out.println(arrayLength);
//
//        for (int j = 0; j < arrayLength; j++) {
//            String name = kycAccounts.getJSONObject(j).getString("account_holder_name");
//            System.out.println("Account Holder name = " + name);
//        }
    }

    public void queryStatementRequestInformationClientStatementRequestsByUserAndApp() throws IOException, ConfigurationException {
        prop.load(file);
        RestAssured.baseURI = prop.getProperty("baseUrl2");

        String query = "{\"query\":\"query ConnectConsoleFinancialServiceStatementRequestInformationClientStatementRequestsByUserAndApp($input: PaginationInput!, $appId: String!) {\\r\\n  connectConsoleFinancialServiceStatementRequestInformationClientStatementRequestsByUserAndApp(input: $input, app_id: $appId) {\\r\\n    result {\\r\\n      statement_accounts {\\r\\n        account_holder_name\\r\\n      }\\r\\n      statement_request_no\\r\\n    }\\r\\n  }\\r\\n}\",\"variables\":{\"input\":{\"skip\":0,\"take\":10},\"appId\":\"ef7551e0-936a-4727-8efd-6c77a156df9e\"},\"operationName\":\"ConnectConsoleFinancialServiceStatementRequestInformationClientStatementRequestsByUserAndApp\"}";

        Response res =
                given()
                        .contentType("application/json").headers("Authorization", prop.getProperty("token")).header("x-device-id", prop.getProperty("x-device-id")).header("x-app-id", prop.getProperty("x-app-id"))
                        .body(query).
                        when()
                        .post("/graphql").
                        then().log().all()
                        .assertThat().statusCode(200).extract().response();

        JsonPath response = res.jsonPath();
        System.out.println(res.asString());

        String value = response.get("data.connectConsoleFinancialServiceStatementRequestInformationClientStatementRequestsByUserAndApp.result").toString();
        System.out.println("Array is "+ value);

        if(value != null){
            Assert.assertNotNull("Array is Not Empty", "data.connectConsoleFinancialServiceStatementRequestInformationClientStatementRequestsByUserAndApp.result");
            System.out.println("Array is Not Empty");
        } else if (value == null) {
            Assert.assertNull("Error: Array should not be null but showing null", "data.connectConsoleFinancialServiceStatementRequestInformationClientStatementRequestsByUserAndApp.result");
            System.out.println("Error: Array should not be null but showing null");
        }else{
            System.out.println("Errors");
            Assert.fail("Errors");
        }

    }

    public void mutationKycRequestInformationClientKycRequest() throws IOException, ConfigurationException {
        prop.load(file);
        RestAssured.baseURI = prop.getProperty("baseUrl2");
        //Monir
//        String query = "{\"query\":\"mutation ConnectConsoleFinancialServiceKycRequestInformationClientKycRequest($input: KycRequestConnectConsoleFinancialServiceKycRequestInformationInput!) {\\r\\n  connectConsoleFinancialServiceKycRequestInformationClientKycRequest(input: $input) {\\r\\n    id\\r\\n    kyc_request_no\\r\\n  }\\r\\n}\\r\\n\",\"variables\":{\"input\":{\"account_holder_calling_code\":\"+88\",\"account_holder_date_of_birth\":\"12-10-1996\",\"account_holder_document_no\":\"215478469712348\",\"account_holder_name\":\"Monir\",\"account_number\":\"110100157963\",\"account_holder_phone_number\":\"01838344119\",\"gkyc_document_type_id\":\"6b494ee9-5e02-48b6-a28b-00b5ae99e416\",\"psp_account_type_id\":\"de0b99cb-566c-4ba8-a2c0-5e9df1356b27\",\"psp_branch_or_agent_id\":\"cea07b09-8fe2-4ef7-9c6e-53084e3db14a\",\"psp_id\":\"4171b743-6f3e-438d-a662-ce412efdbe12\"}},\"operationName\":\"ConnectConsoleFinancialServiceKycRequestInformationClientKycRequest\"}";

        //Mehedi Invalid query
//        String query = "{\"query\":\"mutation ConnectConsoleFinancialServiceKycRequestInformationClientKycRequest($input: KycRequestConnectConsoleFinancialServiceKycRequestInformationInput!) {\\r\\n  connectConsoleFinancialServiceKycRequestInformationClientKycRequest(input: $input) {\\r\\n    id\\r\\n    kyc_request_no\\r\\n  }\\r\\n}\\r\\n\",\"variables\":{\"input\":{\"account_holder_calling_code\":\"+88\",\"account_holder_date_of_birth\":\"30-11-1999\",\"account_holder_document_no\":\"2154745042121480\",\"account_holder_name\":\"Mehedi Hasan\",\"account_number\":\"1101001897450\",\"account_holder_phone_number\":\"01303865986\",\"gkyc_document_type_id\":\"6b494ee9-5e02-48b6-a28b-00b5ae99e416\",\"psp_account_type_id\":\"de0b99cb-566c-4ba8-a2c0-5e9df1356b27\",\"psp_branch_or_agent_id\":\"cea07b09-8fe2-4ef7-9c6e-53084e3db14a\",\"psp_id\":\"4171b743-6f3e-438d-a662-ce412efdbe12\"}},\"operationName\":\"ConnectConsoleFinancialServiceKycRequestInformationClientKycRequest\"}";

        //Mehedi Valid query
        String query ="{\"query\":\"mutation ConnectConsoleFinancialServiceKycRequestInformationClientKycRequest($input: KycRequestConnectConsoleFinancialServiceKycRequestInformationInput!) {\\r\\n  connectConsoleFinancialServiceKycRequestInformationClientKycRequest(input: $input) {\\r\\n    id\\r\\n    kyc_request_no\\r\\n  }\\r\\n}\\r\\n\",\"variables\":{\"input\":{\"account_holder_calling_code\":\"+88\",\"account_holder_date_of_birth\":\"11-30-1999\",\"account_holder_document_no\":\"2154745042121480\",\"account_holder_name\":\"Mehedi Hasan\",\"account_number\":\"1101001897450\",\"account_holder_phone_number\":\"01303865986\",\"gkyc_document_type_id\":\"6b494ee9-5e02-48b6-a28b-00b5ae99e416\",\"psp_account_type_id\":\"de0b99cb-566c-4ba8-a2c0-5e9df1356b27\",\"psp_branch_or_agent_id\":\"cea07b09-8fe2-4ef7-9c6e-53084e3db14a\",\"psp_id\":\"4171b743-6f3e-438d-a662-ce412efdbe12\"}},\"operationName\":\"ConnectConsoleFinancialServiceKycRequestInformationClientKycRequest\"}";


        Response res =
                given()
                        .contentType("application/json").headers("Authorization", prop.getProperty("token")).header("x-device-id", prop.getProperty("x-device-id")).header("x-app-id", prop.getProperty("x-app-id"))
                        .body(query).
                        when()
                        .post("/graphql").
                        then().log().all()
                        .assertThat().statusCode(200).extract().response();

        JsonPath response = res.jsonPath();
        System.out.println(res.asString());

        String value = response.get("data.connectConsoleFinancialServiceKycRequestInformationClientKycRequest").toString();
        //System.out.println("Array is "+ value);

        String kyc_request_no = response.get("data.connectConsoleFinancialServiceKycRequestInformationClientKycRequest.kyc_request_no").toString();
        System.out.println("kyc_request_no is "+ kyc_request_no);

        // Will validate exactly 10 characters long, consists of alphanumeric capital letters, and does not contain any special characters:
        String pattern = "^[A-Z0-9]{10}$";
        boolean isValid = Pattern.matches(pattern, kyc_request_no);
        Assert.assertEquals(true, isValid);
        System.out.println("Expected Matching "+isValid);

        if( value != null){
            Assert.assertNotNull("Array is Not null", "data.connectConsoleFinancialServiceKycRequestInformationClientKycRequest");
            System.out.println("Array is not null");
        } else if (value == null) {
            Assert.assertNull("Error: Array should not be null but showing null", "data.connectConsoleFinancialServiceKycRequestInformationClientKycRequest");
            System.out.println("Error: Array should not be null but showing null");
        }else{
            System.out.println("Errors");
            Assert.fail("Errors");
        }
    }

    public void mutationKycRequestInformationClientKycBulkRequest() throws IOException, ConfigurationException {

        prop.load(file);
        RestAssured.baseURI = prop.getProperty("baseUrl2");

        String file = "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";

        String query = "{\"query\":\"mutation ConnectConsoleFinancialServiceKycRequestInformationClientKycBulkRequest($appId: String!, $file: String!) {\\r\\n  connectConsoleFinancialServiceKycRequestInformationClientKycBulkRequest(app_id: $appId, file: $file)\\r\\n}\\r\\n\",\"variables\":{\"appId\":\"ef7551e0-936a-4727-8efd-6c77a156df9e\",\"file\":" +
                "\"" + file + "\"" + "}," + "\"operationName\":\"ConnectConsoleFinancialServiceKycRequestInformationClientKycBulkRequest\"}";
        Response res =
                given()
                        .contentType("application/json").headers("Authorization", prop.getProperty("token")).header("x-device-id", prop.getProperty("x-device-id")).header("x-app-id", prop.getProperty("x-app-id"))
                        .body(query).
                        when()
                        .post("/graphql").
                        then().log().all()
                        .assertThat().statusCode(200).extract().response();

        JsonPath response = res.jsonPath();
        //System.out.println(res.asString());

        String message = response.get("data.connectConsoleFinancialServiceKycRequestInformationClientKycBulkRequest").toString();
        System.out.println("Message is: "+ message);

        Assert.assertEquals("File has been successfully uploaded.",message);

        String value = response.get("data.connectConsoleFinancialServiceKycRequestInformationClientKycBulkRequest").toString();
        //System.out.println("Array is "+ value);

        if( value != null){
            Assert.assertNotNull("Array is Not null", "data.connectConsoleFinancialServiceKycRequestInformationClientKycBulkRequest");
            System.out.println("Array is not null");
        } else if (value == null) {
            Assert.assertNull("Error: Array should not be null but showing null", "data.connectConsoleFinancialServiceKycRequestInformationClientKycBulkRequest");
            System.out.println("Error: Array should not be null but showing null");
        }else{
            System.out.println("Errors");
            Assert.fail("Errors");
        }
    }

    public void mutationStatementRequestInformationClientStatementRequest() throws IOException, ConfigurationException {
        prop.load(file);
        RestAssured.baseURI = prop.getProperty("baseUrl2");

        //Mehedi Valid query
        String query ="{\"query\":\"mutation ConnectConsoleFinancialServiceStatementRequestInformationClientStatementRequest($input: StatementRequestConnectConsoleFinancialServiceStatementRequestInformationInput!) {\\r\\n  connectConsoleFinancialServiceStatementRequestInformationClientStatementRequest(input: $input) {\\r\\n    client_app_id\\r\\n    statement_request_no\\r\\n  }\\r\\n}\",\"variables\":{\"input\":{\"account_holder_calling_code\":\"+88\",\"account_holder_date_of_birth\":\"11-30-1999\",\"account_holder_document_no\":\"2154745042121480\",\"account_holder_name\":\"Mehedi Hasan\",\"account_number\":\"1101001897450\",\"account_holder_phone_number\":\"01303865986\",\"gkyc_document_type_id\":\"6b494ee9-5e02-48b6-a28b-00b5ae99e416\",\"client_app_id\":\"ef7551e0-936a-4727-8efd-6c77a156df9e\",\"psp_account_type_id\":\"de0b99cb-566c-4ba8-a2c0-5e9df1356b27\",\"psp_branch_or_agent_id\":\"cea07b09-8fe2-4ef7-9c6e-53084e3db14a\",\"psp_id\":\"4171b743-6f3e-438d-a662-ce412efdbe12\",\"from_date\":\"01-12-2023\",\"to_date\":\"07-12-2023\"}},\"operationName\":\"ConnectConsoleFinancialServiceStatementRequestInformationClientStatementRequest\"}";

        Response res =
                given()
                        .contentType("application/json").headers("Authorization", prop.getProperty("token")).header("x-device-id", prop.getProperty("x-device-id")).header("x-app-id", prop.getProperty("x-app-id"))
                        .body(query).
                        when()
                        .post("/graphql").
                        then().log().all()
                        .assertThat().statusCode(200).extract().response();

        JsonPath response = res.jsonPath();
        System.out.println(res.asString());

        String value = response.get("data.connectConsoleFinancialServiceStatementRequestInformationClientStatementRequest").toString();
        //System.out.println("Array is "+ value);

        String statement_request_no = response.get("data.connectConsoleFinancialServiceStatementRequestInformationClientStatementRequest.statement_request_no").toString();
        System.out.println("kyc_request_no is "+ statement_request_no);

        // Will validate exactly 10 characters long, consists of alphanumeric capital letters, and does not contain any special characters:
        String pattern = "^[A-Z0-9]{10}$";
        boolean isValid = Pattern.matches(pattern, statement_request_no);
        Assert.assertEquals(true, isValid);
        System.out.println("Expected Matching "+isValid);

        if( value != null){
            Assert.assertNotNull("Array is Not null", "data.connectConsoleFinancialServiceStatementRequestInformationClientStatementRequest");
            System.out.println("Array is not null");
        } else if (value == null) {
            Assert.assertNull("Error: Array should not be null but showing null", "data.connectConsoleFinancialServiceStatementRequestInformationClientStatementRequest");
            System.out.println("Error: Array should not be null but showing null");
        }else{
            System.out.println("Errors");
            Assert.fail("Errors");
        }
    }

    public void mutationStatementRequestInformationClientStatementBulkRequest() throws IOException, ConfigurationException {

        prop.load(file);
        RestAssured.baseURI = prop.getProperty("baseUrl2");

        String file = "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";

        String query = "{\"query\":\"mutation ConnectConsoleFinancialServiceStatementRequestInformationClientStatementBulkRequest($appId: String!, $file: String!) {\\r\\n  connectConsoleFinancialServiceStatementRequestInformationClientStatementBulkRequest(app_id: $appId, file: $file)\\r\\n}\",\"variables\":{\"appId\":\"ef7551e0-936a-4727-8efd-6c77a156df9e\",\"file\":" +
                "\"" + file + "\"},\"operationName\":\"ConnectConsoleFinancialServiceStatementRequestInformationClientStatementBulkRequest\"}";

        Response res =
                given()
                        .contentType("application/json").headers("Authorization", prop.getProperty("token")).header("x-device-id", prop.getProperty("x-device-id")).header("x-app-id", prop.getProperty("x-app-id"))
                        .body(query).
                        when()
                        .post("/graphql").
                        then().log().all()
                        .assertThat().statusCode(200).extract().response();

        JsonPath response = res.jsonPath();
        //System.out.println(res.asString());

        String message = response.get("data.connectConsoleFinancialServiceStatementRequestInformationClientStatementBulkRequest").toString();
        System.out.println("Message is: "+ message);

        Assert.assertEquals("File has been successfully uploaded.",message);

        String value = response.get("data.connectConsoleFinancialServiceStatementRequestInformationClientStatementBulkRequest").toString();
        //System.out.println("Array is "+ value);

        if( value != null){
            Assert.assertNotNull("Array is Not null", "data.connectConsoleFinancialServiceStatementRequestInformationClientStatementBulkRequest");
            System.out.println("Array is not null");
        } else if (value == null) {
            Assert.assertNull("Error: Array should not be null but showing null", "data.connectConsoleFinancialServiceStatementRequestInformationClientStatementBulkRequest");
            System.out.println("Error: Array should not be null but showing null");
        }else{
            System.out.println("Errors");
            Assert.fail("Errors");
        }
    }

    public void mutationKycRequestInformationInsertOne() throws IOException, ConfigurationException {
        prop.load(file);
        RestAssured.baseURI = prop.getProperty("baseUrl2");

        String query ="{\"query\":\"mutation ConnectConsoleFinancialServiceKycRequestInformationInsertOne($input: CreateConnectConsoleFinancialServiceKycRequestInformationInput!) {\\r\\n  connectConsoleFinancialServiceKycRequestInformationInsertOne(input: $input) {\\r\\n    is_active\\r\\n    gkyc_user {\\r\\n      first_name\\r\\n      middle_name\\r\\n      last_name\\r\\n    }\\r\\n    client_app {\\r\\n      name\\r\\n    }\\r\\n  }\\r\\n}\",\"variables\":{\"input\":{\"client_app_id\":\"ef7551e0-936a-4727-8efd-6c77a156df9e\",\"gkyc_user_id\":\"a338d7dc-7c70-438f-864f-cd283ffd3fdf\"}},\"operationName\":\"ConnectConsoleFinancialServiceKycRequestInformationInsertOne\"}";

        Response res =
                given()
                        .contentType("application/json").headers("Authorization", prop.getProperty("token")).header("x-device-id", prop.getProperty("x-device-id")).header("x-app-id", prop.getProperty("x-app-id"))
                        .body(query).
                        when()
                        .post("/graphql").
                        then().log().all()
                        .assertThat().statusCode(200).extract().response();

        JsonPath response = res.jsonPath();
        System.out.println(res.asString());

        String value = response.get("data.connectConsoleFinancialServiceKycRequestInformationInsertOne").toString();
        //System.out.println("Array is "+ value);

        if( value != null) {
            Assert.assertNotNull("Array is Not null", "data.connectConsoleFinancialServiceKycRequestInformationInsertOne");
            System.out.println("Array is not null");
        }else if (value == null) {
            Assert.assertNull("Error: Array should not be null but showing null", "data.connectConsoleFinancialServiceKycRequestInformationInsertOne");
            System.out.println("Error: Array should not be null but showing null");
        }else{
            System.out.println("Errors");
            Assert.fail("Errors");
        }
    }

    public void queryKycRequestInformationPagination() throws IOException, ConfigurationException {
        prop.load(file);
        RestAssured.baseURI = prop.getProperty("baseUrl2");

        String query = "{\"query\":\"query ConnectConsoleFinancialServiceKycRequestInformationPagination($input: PaginationInput!) {\\n  connectConsoleFinancialServiceKycRequestInformationPagination(input: $input) {\\n    result {\\n      client_app {\\n        name\\n      }\\n      gkyc_user {\\n        first_name\\n        middle_name\\n        last_name\\n      }\\n      status\\n      sort_order\\n      is_active\\n    }\\n  }\\n}\",\"variables\":{\"input\":{\"skip\":0,\"take\":5}},\"operationName\":\"ConnectConsoleFinancialServiceKycRequestInformationPagination\"}";
        Response res =
                given()
                        .contentType("application/json").headers("Authorization", prop.getProperty("token")).header("x-device-id", prop.getProperty("x-device-id")).header("x-app-id", prop.getProperty("x-app-id"))
                        .body(query).
                        when()
                        .post("/graphql").
                        then().log().all()
                        .assertThat().statusCode(200).extract().response();

        JsonPath response = res.jsonPath();
        System.out.println(res.asString());

        String value = response.get("data.connectConsoleFinancialServiceKycRequestInformationPagination.result").toString();
        System.out.println("Array is "+ value);

        if(value != null){
            Assert.assertNotNull("Array is Not Empty", "data.connectConsoleFinancialServiceKycRequestInformationPagination.result");
            System.out.println("Array is Not Empty");
        }else if (value == null) {
            Assert.assertNull("Error: Array should not be null but showing null", "data.connectConsoleFinancialServiceKycRequestInformationPagination.result");
            System.out.println("Error: Array should not be null but showing null");
        }else{
            System.out.println("Errors");
            Assert.fail("Errors");
        }
    }
}
