package nattrn2;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.text.NumberFormat;


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
    protected List <Transaction> transactions = new ArrayList<>();
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

    public String getAccountType() {return accountType;}

    public abstract BigDecimal getInterestRate();

    //Formatteringsmetoder
    private String formatBalance() {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.of("sv", "SE"));
        return nf.format(balance);
    }

    private String formatInterestRate(BigDecimal rate) {
        NumberFormat pf = NumberFormat.getPercentInstance(Locale.of("sv", "SE"));
        pf.setMaximumFractionDigits(1);
        return pf.format(rate);
    }

    private String formatMoney(BigDecimal amount) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.of("sv", "SE"));
        return nf.format(amount);
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

        BigDecimal depositAmount = BigDecimal.valueOf(amount);
        balance = balance.add(depositAmount);

        Transaction transaction = new Transaction(depositAmount, getBalance());
        transactions.add(transaction);

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
        return accountId + " " +
                formatBalance() + " " +
                accountType + " " +
                formatInterestRate(getInterestRate());
     }

    /**
     * Returnerar kontoinformation när kontot stängs.
     * Informationen innehåller kontonummer, saldo, kontotyp samt räntan som beräknas vid stängning av kontot.
     *
     * @return en formaterad sträng med kontots information
     */
    public String getAccountInfoOnClose() {
        return accountId + " " +
                formatBalance() + " " +
                accountType + " " +
                formatMoney(calculateClosingInterest());
    }

    /**
     * Hämtar alla transaktioner för kontot i formaterad text.
     *
     * @return en lista med strängar där varje element representerar
     *         en formaterad transaktion
     */
    public List<String> formattedTransactions() {
        List<String> formattedTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            formattedTransactions.add(transaction.getTransaction());
        }
        return formattedTransactions;
    }

    /**
     * Beräkna ränta.
     *
     * Metoden beräknar ränta genom att ta kalla på metoden calculateClosingInterest.
     * */
    public abstract BigDecimal calculateClosingInterest();

}


