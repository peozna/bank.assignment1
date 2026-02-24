package nattrn2;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * SavingsAccount.java
 *
 *
 * @author Nathalie Törnkvist
 * Användarnamn: nattrn-2
 * */

public class SavingsAccount extends Account {
    private static final BigDecimal INTEREST_RATE = BigDecimal.valueOf(0.024);
    private boolean freeWithdrawalUsed = false;

    public SavingsAccount (int accountId) {
        super(accountId);
        accountType = "Sparkonto";
    }

    public BigDecimal calculateClosingInterest() {
        BigDecimal interest = (getBalance().multiply(INTEREST_RATE)).setScale(2,
                RoundingMode.HALF_UP);
        return interest;
    }

    public boolean withdraw(int amount) {
        if (amount <= 0) return false;
        BigDecimal balance = getBalance();
        BigDecimal withdrawAmount = BigDecimal.valueOf(amount);

        if (!freeWithdrawalUsed) {
            if (withdrawAmount.compareTo(balance) <= 0) {
                updateBalance(withdrawAmount.negate());
                freeWithdrawalUsed = true;
                //registrera transaktion
                return true;
            }
            else {
                return false;
            }
        }
            BigDecimal fee = withdrawAmount.multiply(BigDecimal.valueOf(0.02));
            BigDecimal totalAmount = withdrawAmount.add((fee));
            if (totalAmount.compareTo(balance) <= 0) {
                updateBalance(totalAmount.negate());
                //REGISTRERA TRANSAKTION
                return true;
            }

            return false;
        }
    }
}
