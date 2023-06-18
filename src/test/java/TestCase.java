import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.Assert;

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
        System.out.println(res.asString());
    }

    public void loginAPI() throws IOException, ConfigurationException {
        prop.load(file);
        RestAssured.baseURI = prop.getProperty("baseUrl2");

        String query = "{\"query\":\"query ConnectConsoleLoginWithPassword($input: ConnectConsoleLoginWithPasswordInput!) {\\r\\n  connectConsoleLoginWithPassword(input: $input) {\\r\\n    connect_id\\r\\n    device_is_verified\\r\\n    _token\\r\\n  }\\r\\n}\",\"variables\":{\"input\":{\"connect_id\":\"+8801303865986\",\"user_pass\":\"123123\"}},\"operationName\":\"ConnectConsoleLoginWithPassword\"}";

        //String query ="{\"query\":\"query ConnectConsoleLoginWithPassword($input: ConnectConsoleLoginWithPasswordInput!) {\\r\\n  connectConsoleLoginWithPassword(input: $input) {\\r\\n    connect_id\\r\\n    device_is_verified\\r\\n    _token\\r\\n  }\\r\\n}\",\"variables\":{\"input\":{\"connect_id\":\"+8801303865986\",\"user_pass\":\"123123\",\"device_token\":\"617404\"}},\"operationName\":\"ConnectConsoleLoginWithPassword\"}";
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
                given().log().all()
                        .contentType("application/json").headers("Authorization", prop.getProperty("token")).header("x-device-id", prop.getProperty("x-device-id")).header("x-app-id", prop.getProperty("x-app-id"))
                        .body(query).
                        when()
                        .post("/graphql").
                        then().log().all()
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

}
