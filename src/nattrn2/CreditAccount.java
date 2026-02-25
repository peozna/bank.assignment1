package nattrn2;

import java.math.BigDecimal;

/**
 * CreditAccount.java
 *
 *
 * @author Nathalie Törnkvist
 * Användarnamn: nattrn-2
 * */

public class CreditAccount extends Account {
    BigDecimal creditLimit = BigDecimal.valueOf(-5000);
    private BigDecimal positiveBalanceInterest = BigDecimal.valueOf(0.011);
    private BigDecimal negativeBalanceInterest = BigDecimal.valueOf(0.05);

    public CreditAccount (int accountId) {
        super(accountId);
        accountType = "Kreditkonto";
    }

    public boolean withdraw(int amount) {
        if (amount <= 0) return false;
        BigDecimal balance = getBalance();
        BigDecimal withdrawAmount = BigDecimal.valueOf(amount);
        BigDecimal futureBalance = balance.add(withdrawAmount.negate());

        if (futureBalance.compareTo(creditLimit) >= 0){
            updateBalance(withdrawAmount.negate());
            return true;
        }
        return false;
    }
}
