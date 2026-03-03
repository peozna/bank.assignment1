package nattrn2;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Transaction.java
 *
 *
 * @author Nathalie Törnkvist
 * Användarnamn: nattrn-2
 * */

public class Transaction {
    private LocalDateTime timeStamp;
    private BigDecimal amount;
    private BigDecimal newBalance;

    public Transaction (BigDecimal amount, BigDecimal newBalance) {
        this.amount = amount;
        this.newBalance = newBalance;
        this.timeStamp = LocalDateTime.now();
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getNewBalance() {
        return newBalance;
    }

    public String getTransaction() {
        LocalDateTime timeStamp = getTimeStamp();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatted = timeStamp.format(formatter);

        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.of("sv", "SE"));
        String amount = nf.format(getAmount());
        String newBalance = nf.format(getNewBalance());

        String transactionInfo = formatted + " " + amount + " " + "Saldo: " + newBalance;
        return transactionInfo;
    }
}
