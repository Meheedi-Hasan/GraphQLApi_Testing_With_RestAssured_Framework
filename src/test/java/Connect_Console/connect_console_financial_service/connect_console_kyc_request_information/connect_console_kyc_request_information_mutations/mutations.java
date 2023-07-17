package Connect_Console.connect_console_financial_service.connect_console_kyc_request_information.connect_console_kyc_request_information_mutations;

import Connect_Console.connect_console_financial_service.TestCase;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.annotations.Test;


import java.io.IOException;

public class mutations {
    @Test(description = "Check if Kyc_reqest_no will contain exactly 10 characters long, consists of alphanumeric capital letters, and does not contain any special characters")
    public void KycRequest() throws IOException, ConfigurationException {
        TestCase user=new TestCase();
        user.mutationKycRequest();
    }

    @Test(description = "Check if File has been successfully uploaded")
    public void KycBulkRequest() throws IOException, ConfigurationException {
        TestCase user=new TestCase();
        user.mutationKycBulkRequest();
    }

}
