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
    private String sirname;
    private int pNo;
    private List<Integer> accountIds;

    /**
     * Konstruktor för customer
     * Skapar en kund utan konton
     * Konton skapas och kopplas till kunden senare av BankLogic
     * */
    public Customer (String name, String sirname, int pNo) {
        this.name = name;
        this.sirname = sirname;
        this.pNo = pNo;
        this.accountIds = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getSirname() {
        return sirname;
    }

    public int getPNo() {
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
    public void setSirname(String sirname) {
        this.sirname = sirname;
    }

    /**
     * Returnerar en sträng med kundinformation.
     * Innehåller namn, personnummer och kontonummer.
     *
     * */
    public String getCustomerInfo() {
        return name + " " + sirname + " " + String.valueOf(getPNo()) + " " + getAccountIds().toString();
    }
}
