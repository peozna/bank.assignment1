package nattrn2;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String firstName;
    private String lastName;
    private int personalNumber;
    private List<Integer> accountIds;

    public Customer (String firstName, String lastName, int personalNumber, List<Integer>accountIds ) {
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCustomerInfo() {
        return firstName + " " + lastName + " " + String.valueOf(getPersonalNumber()) + " " + getAccountIds().toString();
    }
}
