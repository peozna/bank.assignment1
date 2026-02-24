package nattrn2;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Account.java
 *
 * Representerar ett konto(sparkonto) med konto ID, saldo, räntesats.
 * Klassen hanterar insättning, uttag, ränteberäkning och kontopresentation.
 *
 * Logik:
 * Endast logik relaterat till det egna konton.
 * Ingen kunskap om andra konton eller kunder.
 *
 * @author Nathalie Törnkvist
 * Användarnamn: nattrn-2
 * */

abstract class Account {
    private int accountId;
    private BigDecimal balance;
    private List <Transaction> transactions;
    protected String accountType;

    /**
     * Konstruktor Account.
     *
     * I denna uppgift kan endast ett sparkonto skapas,
     * därför sätts kontotypen alltid till Sparkonto och saldot initieras till 0.
     *
     * @param accountId Kontots unika nummer.
     * */
    public Account(int accountId) {
        this.accountId = accountId;
        this.balance = BigDecimal.ZERO;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public int getAccountId() {
        return accountId;
    }

    /**
     * För en insättning på kontot.
     * <p>
     * Metoden kontrollerar att beloppet är större än 0. Om beloppet är giltigt så adderas
     * det till saldot och metoden returnerar true. Annars returnerar den false och saldot
     * förblir detsamma.
     *
     * @param amount Belopp att sätta in, hela kronor.
     * @return true om beloppet är giltigt, false om beloppet är ogiltigt.
     *
     */
    public boolean makeDeposit(int amount) {
        if (amount <= 0) return false;

        //Amount omvandlas till BigDecimal för att matcha saldots datatyp
        balance = balance.add(BigDecimal.valueOf(amount));
        return true;
    }

    /**
     * Ta ut pengar från kontot.
     * <p>
     * Metoden kontrollerar att beloppet är större än 0 och är mindre än saldot. Om beloppet
     * är giltigt returneras true och beloppet dras från saldot, annars returneras false
     * och saldot förblir detsamma.
     *
     * @param amount Belopp att ta ut, hela kronor.
     * @return true om beloppet är giltigt, false om beloppet är ogiltigt.
     *
     */
    public abstract boolean withdraw(int amount);

    protected void updateBalance(BigDecimal totalAmount) {
            balance = balance.add(totalAmount);
    }

    /**
     * Presentera kontoinformation.
     *
     * Metoden hämtar kontoID, saldo, räntesats och kontotyp och sammanställer till en sträng.
     * Obs saldo och räntesats returneras som råa BigDecimal värden, ej formaterade.
     *
     * @return Kontoinformation som sträng
     */
    public String getAccountInfo() {
        //Returnerar oformatterad kontoinformation
        //BankLogic hanterar formattering
        return getAccountId() + " " + getBalance().toString() + " " + getInterest().toString() + " " +
        getAccountType();
     }

    /**
     * Beräkna ränta.
     *
     * Metoden beräknar ränta genom att ta saldo * räntesats och avrundar till 2 decimaler.
     *
     * @return interest räntan för kontot.
     * */
    public abstract BigDecimal calculateClosingInterest();
}
