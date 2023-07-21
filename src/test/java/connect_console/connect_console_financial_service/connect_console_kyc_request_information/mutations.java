package connect_console.connect_console_financial_service.connect_console_kyc_request_information;

import org.apache.commons.configuration.ConfigurationException;
import org.testng.annotations.Test;


import java.io.IOException;

public class mutations {
    @Test(description = "Check if Kyc_reqest_no will contain exactly 10 characters long, consists of alphanumeric capital letters, and does not contain any special characters")
    public void KycRequestInformationClientKycRequest() throws IOException, ConfigurationException {
        testcase user=new testcase();
        user.mutationKycRequestInformationClientKycRequest();
    }

    @Test(description = "Kyc Bulk: Check if File has been successfully uploaded")
    public void KycRequestInformationClientKycBulkRequest() throws IOException, ConfigurationException {
        testcase user=new testcase();
        user.mutationKycRequestInformationClientKycBulkRequest();
    }

    @Test(description = "Check if statement_request_no will contain exactly 10 characters long, consists of alphanumeric capital letters, and does not contain any special characters")
    public void StatementRequestInformationClientStatementRequest() throws IOException, ConfigurationException {
        testcase user=new testcase();
        user.mutationStatementRequestInformationClientStatementRequest();
    }

    @Test(description = "Statement Bulk: Check if File has been successfully uploaded")
    public void StatementRequestInformationClientStatementBulkRequest() throws IOException, ConfigurationException {
        testcase user=new testcase();
        user.mutationStatementRequestInformationClientStatementBulkRequest();
    }
    @Test(description = "Check if user is able to insert one request by KycRequestInformationInsertOne")
    public void KycRequestInformationInsertOne() throws IOException, ConfigurationException {
        testcase user=new testcase();
        user.mutationKycRequestInformationInsertOne();
    }

}
