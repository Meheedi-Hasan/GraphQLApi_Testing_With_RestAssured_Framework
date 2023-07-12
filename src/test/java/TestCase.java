import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.Assert;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

public class TestCase {

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

    public void queryKycRequestInformation() throws IOException, ConfigurationException {
        prop.load(file);
        RestAssured.baseURI = prop.getProperty("baseUrl2");

        String query = "{\"query\":\"query ConnectConsoleFinancialServiceKycRequestInformationClientKycRequestsByUserAndApp($input: PaginationInput!) {\\r\\n  connectConsoleFinancialServiceKycRequestInformationClientKycRequestsByUserAndApp(input: $input) {\\r\\n    take\\r\\n    total\\r\\n    result {\\r\\n      status\\r\\n      kyc_accounts {\\r\\n        account_number\\r\\n        account_holder_name\\r\\n        psp_branch_or_agent {\\r\\n          id\\r\\n          name\\r\\n        }\\r\\n        psp_id\\r\\n        psp_branch_or_agent_id\\r\\n        psp_account_type_id\\r\\n        psp_account_type {\\r\\n          name\\r\\n        }\\r\\n      }\\r\\n      client_app_id\\r\\n      client_app {\\r\\n        name\\r\\n      }\\r\\n    }\\r\\n    skip\\r\\n  }\\r\\n}\",\"variables\":{\"input\":{\"take\":50,\"param\":{\"client_app_id\":\"ef7551e0-936a-4727-8efd-6c77a156df9e\"},\"skip\":0}},\"operationName\":\"ConnectConsoleFinancialServiceKycRequestInformationClientKycRequestsByUserAndApp\"}";

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
            Assert.assertNotNull("Array is Not Empty", "data.connectConsoleFinancialServiceKycRequestInformationClientKycRequestsByUserAndApp.result[0]");
            System.out.println("Array is Not Empty");
        } else if (value == null) {
            Assert.assertNull("Error: Array should not be null but showing null", "data.connectConsoleFinancialServiceKycRequestInformationClientKycRequestsByUserAndApp.result[0]");
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

    public void mutationKycRequest() throws IOException, ConfigurationException {
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

    public void mutationKycBulkRequest() throws IOException, ConfigurationException {

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


}
