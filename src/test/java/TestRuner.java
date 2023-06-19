import org.apache.commons.configuration.ConfigurationException;
import org.junit.jupiter.api.Test;


import java.io.IOException;

public class TestRuner {
    //POST
    //@Test(priority = 1, description = "User will be able to get the ID")
    @Test
    public void countryInfo() throws IOException, ConfigurationException {
        TestCase user=new TestCase();
        user.queryCountryInfo();
    }

    //@Test(priority = 1, description = "User will be able to login to connect console")
    @Test
    public void login() throws IOException, ConfigurationException {
        TestCase user=new TestCase();
        user.loginAPI();
    }
    @Test
    public void UserInfoFindAll() throws IOException, ConfigurationException {
        TestCase user=new TestCase();
        user.queryUserInfoFindAll();
    }
    @Test
    public void KycRequestInformation() throws IOException, ConfigurationException {
        TestCase user=new TestCase();
        user.queryKycRequestInformation();
    }

}
