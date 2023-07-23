package connect_console.connect_console_financial_service.connect_console_kyc_document_information;

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

    public void queryKycDocumentInformationPagination() throws IOException, ConfigurationException {
        prop.load(file);
        RestAssured.baseURI = prop.getProperty("baseUrl2");

        String query = "{\"query\":\"query ConnectConsoleFinancialServiceKycDocumentInformationPagination($input: PaginationInput!) {\\r\\n  connectConsoleFinancialServiceKycDocumentInformationPagination(input: $input) {\\r\\n    result {\\r\\n      client_app {\\r\\n        name\\r\\n      }\\r\\n      document_type {\\r\\n        name\\r\\n      }\\r\\n      sort_order\\r\\n      is_active\\r\\n    }\\r\\n  }\\r\\n}\",\"variables\":{\"input\":{\"skip\":0,\"take\":5}},\"operationName\":\"ConnectConsoleFinancialServiceKycDocumentInformationPagination\"}";

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

        String value = response.get("data.connectConsoleFinancialServiceKycDocumentInformationPagination.result").toString();
        System.out.println("Array is "+ value);

        if(value != null){
            Assert.assertNotNull("Array is Not Empty", "data.connectConsoleFinancialServiceKycDocumentInformationPagination.result");
            System.out.println("Array is Not Empty");
        }else if (value == null) {
            Assert.assertNull("Error: Array should not be null but showing null", "data.connectConsoleFinancialServiceKycDocumentInformationPagination.result");
            System.out.println("Error: Array should not be null but showing null");
        }else{
            System.out.println("Errors");
            Assert.fail("Errors");
        }
    }
}
