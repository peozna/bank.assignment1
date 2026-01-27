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
    private String firstName;
    private String lastName;
    private int personalNumber;
    private List<Integer> accountIds;

    /**
     * Konstruktor för customer
     * Skapar en kund utan konton
     * Konton skapas och kopplas till kunden senare av BankLogic
     * */
    public Customer (String firstName, String lastName, int personalNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalNumber = personalNumber;
        this.accountIds = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getPersonalNumber() {
        return personalNumber;
    }

    public List<Integer> getAccountIds() {
        return accountIds;
    }

    //Ändrar kundens förnamn
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    //Ändrar kundens efternamn
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returnerar en sträng med kundinformation.
     * Innehåller namn, personnummer och kontonummer.
     *
     * */
    public String getCustomerInfo() {
        return firstName + " " + lastName + " " + String.valueOf(getPersonalNumber()) + " " + getAccountIds().toString();
    }
}
