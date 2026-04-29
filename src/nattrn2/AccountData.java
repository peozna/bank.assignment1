package nattrn2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

    public class AccountData {
        public int accountId;
        public BigDecimal balance;
        public String accountType;
        public List<TransactionData> transactions = new ArrayList<>();
    }


