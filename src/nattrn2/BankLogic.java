package nattrn2;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Locale;

public class BankLogic {
    private List<Customer> customers = new ArrayList<>();
    private HashMap<String, List<Account>> customerAccounts = new HashMap<>();

    public static void main(String[] args) {

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
            String addCustomer = customer.getPNo() + " " +
            customer.getName() + " " +
            customer.getSurname();

            customerString.add(addCustomer);
        }
        return customerString;
    }
    /**
     * Metod för att skapa en ny kund.
     * */
    public boolean createCustomer(String name, String surname, String pNo) {
        for (Customer customer : customers) {
            if (customer.getPNo().equals(pNo)) return false;
        }
        Customer newCustomer = new Customer(name, surname, pNo);
        customers.add(newCustomer);

        customerAccounts.put(pNo, new ArrayList<Account>());

        return true;
    }

    /**
     * Hämtar information om en specifik kund och dens konto/n.
     *
     * Metoden söker efter kundens personnummer i benkens kundlista.
     * Om kunden hittas returneras en lista av strängar där:
     * Första elementet består av kundens personnummer och fullständiga namn.
     * Andra elementet innehåller information om kundens konto/n.
     *
     * Kontoinformation formateras med:
     * Saldo - valutaformat med svenska inställningar (NumberFormat.getCurrencyInstance)
     * Ränta - procentformat med max en decimal (NumberFormat.getPercentInstance)
     *
     * Om kunden inte finns returneras null.
     *
     * @param pNo personnummer på kunden som ska hämtas
     * @return Lista med kund och kontoinformation eller null om kunden inte finns.
     * */
    public List<String> getCustomer(String pNo) {
        List<String> customerInfo = new ArrayList<>();

        for (Customer customer : customers) {
            if (customer.getPNo().equals(pNo)) {
                String getCustomer = customer.getPNo() + " " + customer.getName() + " " + customer.getSurname() + " ";
                customerInfo.add(getCustomer);

                List<Account> accounts = customerAccounts.get(pNo);

                    for (Account account: accounts) {
                        BigDecimal balance = account.getBalance();
                        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.of("SV", "SE"));
                        String balanceString = nf.format(balance);

                        BigDecimal interest = account.getInterest();
                        NumberFormat pf = NumberFormat.getPercentInstance(Locale.of("SV", "SE"));
                        pf.setMaximumFractionDigits(1);
                        String interestString = pf.format(interest);

                        String accountInfo = String.valueOf(account.getAccountId()) + " " + balanceString + " " + account.getAccountType() + " " + interestString;

                        customerInfo.add(accountInfo);
                    }
                return customerInfo;
            }
        } return null;
    }
}
