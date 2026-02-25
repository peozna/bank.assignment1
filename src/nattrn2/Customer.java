package nattrn2;
import java.util.ArrayList;
import java.util.List;

/**
 * Customer.java
 *
 * Representerar en kund med förnamn, efternamn, personnummer och en lista med tillhörande kontonummer.
 * Klassen hanterar ändring av namn och presentation av kundinfo.
 * Logik:
 * Den innehåller endast information om kunden ingen logik för konton eller transaktioner.
 *
 * @author Nathalie Törnkvist
 * Användarnamn: nattrn-2
 * */

public class Customer {
    private String name;
    private String surname;
    private String pNo;
    private List<Integer> accountIds;

    /**
     * Konstruktor för customer
     * Skapar en kund utan konton
     * Konton skapas och kopplas till kunden senare av BankLogic
     * */
    public Customer (String name, String surname, String pNo) {
        this.name = name;
        this.surname = surname;
        this.pNo = pNo;
        this.accountIds = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPNo() {
        return pNo;
    }

    public List<Integer> getAccountIds() {
        return accountIds;
    }

    //Ändrar kundens förnamn
    public void setName(String name) {
        this.name = name;
    }

    //Ändrar kundens efternamn
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Returnerar en sträng med kundinformation.
     * Innehåller namn, personnummer och kontonummer.
     *
     * */
    public String getCustomerInfo() {
        return name + " " + surname + " " + pNo + " " + getAccountIds().toString();
    }
}
