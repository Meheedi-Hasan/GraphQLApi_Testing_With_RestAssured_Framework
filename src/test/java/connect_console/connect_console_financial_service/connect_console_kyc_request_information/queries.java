package connect_console.connect_console_financial_service.connect_console_kyc_request_information;

import org.apache.commons.configuration.ConfigurationException;
import org.testng.annotations.Test;

import java.io.IOException;

public class queries {
    @Test(description = "Check if Country info array is not null")
    public void countryInfo() throws IOException, ConfigurationException {
        testcase user=new testcase();
        user.queryCountryInfo();
    }

    @Test(description = "Check if user is able to login to connect console")
    public void login() throws IOException, ConfigurationException {
        testcase user=new testcase();
        user.loginAPI();
    }
    @Test(description = "Check if User Information FindAll array is not null")
    public void UserInfoFindAll() throws IOException, ConfigurationException {
        testcase user=new testcase();
        user.queryUserInfoFindAll();
    }
    @Test(description = "Check if Kyc Request Information array is not null")
    public void KycRequestInformation() throws IOException, ConfigurationException {
        testcase user=new testcase();
        user.queryKycRequestInformation();
    }
}
