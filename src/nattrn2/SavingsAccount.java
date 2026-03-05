package nattrn2;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * SavingsAccount.java
 *
 * Representerar ett sparkonto i banken.
 * Sparkontot har en fast ränta och ett fritt uttag.
 * Efter det första uttaget tas en avgift på 2% av uttagsbeloppet.
 *
 * @author Nathalie Törnkvist
 * Användarnamn: nattrn-2
 * */

public class SavingsAccount extends Account {
    //Kontots fasta ränta
    private static final BigDecimal INTEREST_RATE = BigDecimal.valueOf(0.024);
    //Anger om det fria uttaget har använts.
    private boolean freeWithdrawalUsed = false;

    public SavingsAccount (int accountId) {
        super(accountId);
        accountType = "Sparkonto";
    }

    /**
     * Hämtar räntesatsen för sparkontot.
     *
     * @return räntan för kontot
     */
    @Override
    public BigDecimal getInterestRate() {
        return INTEREST_RATE;
    }

    /**
     * Beräknar räntan som ska betalas ut när kontot avslutas.
     *
     * @return räntan baserad på aktuellt saldo
     */
    @Override
    public BigDecimal calculateClosingInterest() {
        BigDecimal interest = (getBalance().multiply(INTEREST_RATE)).setScale(2,
                RoundingMode.HALF_UP);
        return interest;
    }

    /**
     * Tar ut pengar från sparkontot.
     * Det första uttaget är gratis. Efterföljande uttag har en avgift på 2 %.
     *
     * @param amount beloppet som ska tas ut
     * @return true om uttaget lyckades, annars false
     */
    public boolean withdraw(int amount) {
        if (amount <= 0) return false;
        BigDecimal balance = getBalance();
        BigDecimal withdrawAmount = BigDecimal.valueOf(amount);

        //Om det fria uttaget inte har använts.
        if (!freeWithdrawalUsed) {
            if (withdrawAmount.compareTo(balance) <= 0) {
                updateBalance(withdrawAmount.negate());
                freeWithdrawalUsed = true;
                Transaction newTransaction = new Transaction(withdrawAmount.negate(), getBalance());
                transactions.add(newTransaction);
                return true;
            }
            else {
                return false;
            }
        }
            //Avgift på 2% för uttag efter det först fria uttaget.
            BigDecimal fee = withdrawAmount.multiply(BigDecimal.valueOf(0.02));
            BigDecimal totalAmount = withdrawAmount.add((fee));
            if (totalAmount.compareTo(balance) <= 0) {
                updateBalance(totalAmount.negate());
                Transaction newTransaction = new Transaction(totalAmount.negate(), getBalance());
                transactions.add(newTransaction);
                return true;
            }

            return false;
        }
    }

