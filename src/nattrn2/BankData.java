package nattrn2;

import java.util.HashMap;
import java.util.List;

public class BankData {
    public List<Customer> customers;
    public HashMap<String, List<AccountData>> customerAccounts;
    public int nextAccountId;
}
