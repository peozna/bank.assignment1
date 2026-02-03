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
    private int nextAccountId = 1001;

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

    /**
     * Ändrar namn på kunden med angivet personnummer
     *
     * Metoden hämtar kunden med det angivna personnummmret
     * Om för eller efternamn skickas in som tomma strängar så behålls det gamla värdet.
     * Returnerar endast true om minst ett av namnet har ändrats.
     *
     * @param name Nytt förnamn på kunden, om tomt behålls det gamla namnet.
     * @param surname Nytt efternamn på kunden, om tomt behålls det gamla namnet.
     * @param pNo Personnummer på kunden.
     * @return true Om minst ett av namnet ändrats, annars false.
     * */
    boolean changeCustomerName(String name, String surname, String pNo) {
        for (Customer customer : customers) {
            if (customer.getPNo().equals(pNo)) {
                boolean changed = false;

                if(!name.isEmpty()) {
                    customer.setName(name);
                    changed = true;
                }

                if(!surname.isEmpty()) {
                    customer.setSurname(surname);
                    changed = true;
                }
                return changed;
            }
        }
        return false;
    }

    /**
     * Skapar ett nytt sparkonto för kund med angivet personnummer.
     *
     * Metoden loopar genom listan med kunder. Om kunden finns:
     *  - Skapas ett nytt sparkonto med unikt ID.
     *  - Kontot läggs till i kundens lista med konton HashMap customerAccounts.
     *  - Kontonummret returneras
     *  Om kunden inte finns returneras -1.
     *
     * @param pNo Personnummer på kunden
     * @return KontoID för det nya sparkontot eller -1 om kunden inte finns.
     * */
    int createSavingsAccount(String pNo) {
        for (Customer customer: customers) {
            if (customer.getPNo().equals(pNo)) {
                Account savingsAccount = new Account(nextAccountId);
                nextAccountId ++;

                List<Account> accounts = customerAccounts.get(pNo);
                if(accounts == null) {
                    accounts = new ArrayList<>();
                    customerAccounts.put(pNo, accounts);
                }

                accounts.add(savingsAccount);
                return savingsAccount.getAccountId();
            }
        }
        return -1;
    }
}
