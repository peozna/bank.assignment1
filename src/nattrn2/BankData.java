package nattrn2;

import java.util.HashMap;
import java.util.List;

public class BankData {
    public List<Customer> customers;
    public HashMap<String, List<Account>> customerAccounts;
    public int nextAccountId;
}
