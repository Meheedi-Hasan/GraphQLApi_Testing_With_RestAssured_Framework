import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.Assert;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

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
                        then()
                        .assertThat().statusCode(200).extract().response();

        JsonPath response = res.jsonPath();
        //System.out.println(res.asString());

        String value = response.get("data.connectConsoleFinancialServiceKycRequestInformationClientKycRequest").toString();
        //System.out.println("Array is "+ value);

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

}
