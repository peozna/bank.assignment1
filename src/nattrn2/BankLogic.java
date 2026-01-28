package nattrn2;

import java.util.ArrayList;
import java.util.List;

public class BankLogic {
    List<Customer> customers = new ArrayList<>();

    static void main() {

    }
    /**
     * Metoden returnerar en lista med strängar som representeras bankens kunder.
     * Om inga kunder finns returneras en tom lista.
     * */
    public List<String> getAllCustomers() {
        List<String> customerString = new ArrayList<>();;
        if (customers.isEmpty()) {
            return customerString;
        }

        for (Customer customer : customers) {
            String addCustomer = String.valueOf(customer.getPersonalNumber()) + " " +
            customer.getFirstName() + " " +
            customer.getLastName();

            customerString.add(addCustomer);
        }
        return customerString;
    }
}
