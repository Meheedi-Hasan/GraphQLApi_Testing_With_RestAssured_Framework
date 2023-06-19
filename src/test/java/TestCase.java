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
                        then()
                        .assertThat().statusCode(200).extract().response();

        JsonPath response = res.jsonPath();
        Assert.assertEquals(response.get("data.coreLocalisationServiceCountryInformationFindAll[0].id").toString(), "5e9f919f-2d42-42ec-8b6c-adf7f7b3cd0b");
        System.out.println(response.get("data.coreLocalisationServiceCountryInformationFindAll[0].id").toString());
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
                        then()
                        .assertThat().statusCode(200).extract().response();

        JsonPath jsonpath = res.jsonPath();
        token = jsonpath.get("data.connectConsoleLoginWithPassword._token");
        //System.out.println("Token is: "+token);
        Utils.setEnvVariable("token", token);
        System.out.println(res.asString());
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
                        then()
                        .assertThat().statusCode(200).extract().response();

        JsonPath response = res.jsonPath();
        Assert.assertEquals(response.get("data.connectConsoleUserServiceUserInformationFindAll[0].connect_id").toString(), "+8801521315574");
        String c_id = response.get("data.connectConsoleUserServiceUserInformationFindAll[0].connect_id").toString();
        System.out.println(c_id);
        Assert.assertEquals(response.get("data.connectConsoleUserServiceUserInformationFindAll[0].last_name").toString(), "Shuvo");
        String l_name = response.get("data.connectConsoleUserServiceUserInformationFindAll[0].last_name").toString();
        System.out.println(l_name);
        System.out.println(res.asString());
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
                        then()
                        .assertThat().statusCode(200).extract().response();

        JsonPath response = res.jsonPath();
        System.out.println(res.asString());

        String[] name = new String[5];
        for(int i = 0; i <= 5; i++) {
            name[i] = response.get("data.connectConsoleFinancialServiceKycRequestInformationClientKycRequestsByUserAndApp.result["+i+"].kyc_accounts[0].account_holder_name").toString();
            System.out.println("Account Holder name = "+name[i]);
        }
        Assert.assertEquals(name[1], "Mehedi Hasan");

//        JSONArray kycAccounts = response.getJSONArray("data.connectConsoleFinancialServiceKycRequestInformationClientKycRequestsByUserAndApp.result[]");
//        int arrayLength = kycAccounts.length();
//        System.out.println(arrayLength);
//
//        for (int j = 0; j < arrayLength; j++) {
//            String name = kycAccounts.getJSONObject(j).getString("account_holder_name");
//            System.out.println("Account Holder name = " + name);
//        }

    }

}
