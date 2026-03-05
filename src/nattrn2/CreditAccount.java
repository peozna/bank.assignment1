package nattrn2;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * CreditAccount.java
 *
 * Representerar ett kreditkonto i banken.
 * Ett kreditkonto kan ha ett negativt saldo ner till en viss gräns.
 * Räntan kan vara positiv eller negativ.
 *
 * @author Nathalie Törnkvist
 * Användarnamn: nattrn-2
 * */

public class CreditAccount extends Account {
    //Kreditgränsen för kontot
    BigDecimal creditLimit = BigDecimal.valueOf(-5000);
    //Ränta när kontots saldo är positivt
    private BigDecimal positiveBalanceInterest = BigDecimal.valueOf(0.011);
    //Ränta när kontots saldo är negativt
    private BigDecimal negativeBalanceInterest = BigDecimal.valueOf(0.05);

    public CreditAccount (int accountId) {
        super(accountId);
        accountType = "Kreditkonto";
    }

    /**
     * Hämtar räntesatsen för kontot beroende på aktuellt saldo.
     *
     * @return räntan för kontot
     */
    @Override
    public BigDecimal getInterestRate() {
        BigDecimal balance = getBalance();
        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            return negativeBalanceInterest;
        }
        return positiveBalanceInterest;
    }

    /**
     * Beräknar räntan som ska betalas eller erhållas när kontot avslutas.
     *
     * @return räntan baserad på aktuellt saldo
     */
    @Override
    public BigDecimal calculateClosingInterest() {
        BigDecimal balance = getBalance();

        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            return balance.multiply(negativeBalanceInterest).setScale(2, RoundingMode.HALF_UP);
        }
        return balance.multiply(positiveBalanceInterest).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Tar ut pengar från kreditkontot.
     * Uttaget tillåts endast om det nya saldot inte överskrider kreditgränsen.
     *
     * @param amount beloppet som ska tas ut
     * @return true om uttaget lyckades, annars false
     */
    @Override
    public boolean withdraw(int amount) {
        if (amount <= 0) return false;
        BigDecimal balance = getBalance();
        BigDecimal withdrawAmount = BigDecimal.valueOf(amount);
        BigDecimal futureBalance = balance.add(withdrawAmount.negate());

        if (futureBalance.compareTo(creditLimit) >= 0){
            updateBalance(withdrawAmount.negate());

            int transactionAmount = withdrawAmount.negate().intValue();
            Transaction newTransaction = new Transaction(withdrawAmount.negate(), getBalance());
            transactions.add(newTransaction);
            return true;
        }
        return false;
    }
}
