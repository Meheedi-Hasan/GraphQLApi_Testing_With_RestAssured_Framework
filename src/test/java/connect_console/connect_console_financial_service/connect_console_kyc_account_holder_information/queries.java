package connect_console.connect_console_financial_service.connect_console_kyc_account_holder_information;

import org.apache.commons.configuration.ConfigurationException;
import org.testng.annotations.Test;

import java.io.IOException;

public class queries {

    @Test(description = "Check if Kyc Account Holder Information Pagination array is not null")
    public void KycAccountHolderInformationPagination() throws IOException, ConfigurationException {
        testcase user=new testcase();
        user.queryKycAccountHolderInformationPagination();
    }
}
