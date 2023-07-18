package Connect_Console.connect_console_financial_service.connect_console_kyc_request_information.connect_console_kyc_request_information_mutations;

import Connect_Console.connect_console_financial_service.TestCase;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.annotations.Test;


import java.io.IOException;

public class mutations {
    @Test(description = "Check if Kyc_reqest_no will contain exactly 10 characters long, consists of alphanumeric capital letters, and does not contain any special characters")
    public void MutationKycRequest() throws IOException, ConfigurationException {
        TestCase user=new TestCase();
        user.mutationKycRequest();
    }

    @Test(description = "Kyc Bulk: Check if File has been successfully uploaded")
    public void MutationKycBulkRequest() throws IOException, ConfigurationException {
        TestCase user=new TestCase();
        user.mutationKycBulkRequest();
    }

    @Test(description = "Check if statement_request_no will contain exactly 10 characters long, consists of alphanumeric capital letters, and does not contain any special characters")
    public void MutationStatementRequest() throws IOException, ConfigurationException {
        TestCase user=new TestCase();
        user.mutationStatementRequest();
    }

    @Test(description = "Statement Bulk: Check if File has been successfully uploaded")
    public void MutationStatementBulkRequest() throws IOException, ConfigurationException {
        TestCase user=new TestCase();
        user.mutationStatementBulkRequest();
    }

}
