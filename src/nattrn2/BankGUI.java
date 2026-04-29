package nattrn2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Stack;

/**
 * BankGUI.java
 *
 * Denna klass implementerar ett grafiskt gränssnitt för ett banksystem med hjälp av Java Swing.
 *
 * Funktionalitet:
 * - Visar alla kunder i en lista.
 * - Visar alla konton för vald kund i en lista.
 * - Sätta in pengar.
 * - Ta ut pengar.
 * - Ta bort kund.
 * - Ta bort konto.
 *
 * @author Nathalie Törnkvist
 * Användarnamn: nattrn-2
 * */

public class BankGUI {
    private BankLogic bank;

    //Fönstret som allt visas i
    private JFrame frame;

    //Panelerna i GUIt
    private JPanel leftPanel;
    private JPanel middlePanel;
    private JPanel rightPanel;

    //Listor som visar kunder och konton
    private JList<String> customerList;
    private JList<String> accountList;

    //Modeller som uppdaterar listor dynamiskt
    private DefaultListModel<String> customerModel;
    private DefaultListModel<String> accountModel;

    //Sparar vad användaren har valt
    private String selectedPNo;
    private int selectedAccountId;

    /**
     * Konstruktor som skapar en instans av BankLogic, laddar testdata och startar GUIt.
     *
     * */
    public BankGUI(){
        bank = new BankLogic();
        TestData.setupBank(bank);
        createGUI();
    }

    /**
     * Programmets startpunkt, skapar ett nytt GUI fönster.
     *
     * */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BankGUI());
    }

    /**
     * Skapar GUIt genom att anropa metoder som bygger olika delar av gränssnittet.
     * */
    private void createGUI() {
        createFrame();
        createMenu();
        createPanels();
        createCustomerList();
        createAccountList();
        createButtons();
        frame.setVisible(true);
    }

    /**
     * Skapar fönstret och sätter en layout.
     *
     * */
    private void createFrame() {
        frame = new JFrame("Bank");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
    }

    /**
     * Skapar tre paneler för layout:
     *
     * Vänster: kunder
     * Mitten: konton
     * Höger: knappar och textfält.
     * */
    private void createPanels() {
        leftPanel = new JPanel(new BorderLayout());
        middlePanel = new JPanel(new BorderLayout());
        rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(6, 1));

        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(middlePanel, BorderLayout.CENTER);
        frame.add(rightPanel, BorderLayout.EAST);
    }

    /**
     * Skapar lista som visar alla kunder.
     * När en kund väljs uppdateras kontolista.
     * */
    private void createCustomerList() {
        customerModel = new DefaultListModel<>();
        customerList = new JList<>(customerModel);
        selectedAccountId = 0;

        leftPanel.add(new JScrollPane(customerList), BorderLayout.CENTER);

        updateCustomerList();

        //Lyssnar på val av kund
        customerList.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;

            String selected = customerList.getSelectedValue();
            if (selected == null) return;

            //Hämtar personnummer från vald rad
            selectedPNo = selected.split(" ")[0];
            //Uppdaterar konton för vald kund
            updateAccountList(selectedPNo);

                }
        );
    }

    /**
     * Skapar lista över konton för vald kund.
     * */
    private void createAccountList() {
        accountModel = new DefaultListModel<>();
        accountList = new JList<>(accountModel);

        middlePanel.add(new JScrollPane(accountList), BorderLayout.CENTER);

        //Lyssnar på val av konto
        accountList.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;

            String selected = accountList.getSelectedValue();
            if (selected == null) return;

            try {
                //Hämta kontoId från vald rad
                String[] parts = selected.split(" ");
                selectedAccountId = Integer.parseInt(parts[0]);
            } catch (Exception ex) {
                System.out.println("Fel format på konto: " + selected);
            }}
        );
    }

    /**
     * Skapar knappar och textfält för alla bankoperationer.
     * Metoden innehåller även eventhantering för varje knapp.
     *
     * */
    private void createButtons() {
        JButton depositButton = new JButton("Deposit money");
        JButton withdrawButton = new JButton("Withdraw money");
        JButton closeAccountButton = new JButton("Close account");
        JButton deleteCustomerButton = new JButton("Delete customer");
        JTextField amountField = new JTextField(10);
        JLabel amountLabel = new JLabel("Fill in amount: ");

        rightPanel.add(depositButton);
        rightPanel.add(withdrawButton);
        rightPanel.add(closeAccountButton);
        rightPanel.add(deleteCustomerButton);
        rightPanel.add(amountLabel);
        rightPanel.add(amountField);

        //Insättning
        depositButton.addActionListener(e -> {
            int amount = 0;
            try {
                amount = Integer.parseInt(amountField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Enter valid number");
                return;
            }

            //Kontroll att kund och konto är valda.
            if (selectedPNo == null) {
                JOptionPane.showMessageDialog(frame, "Select a customer first");
                return;
            }

            if (selectedAccountId == 0) {
                JOptionPane.showMessageDialog(frame, "Select an account first");
                return;
            }

            boolean ok = bank.deposit(selectedPNo, selectedAccountId, amount);

            if (ok) {
                JOptionPane.showMessageDialog(frame, "Deposit successful");
            } else {
                JOptionPane.showMessageDialog(frame, "Error");
            }
            updateAccountList(selectedPNo); //Uppdaterar saldo
            amountField.setText(" ");
        });

        //Uttag
        withdrawButton.addActionListener(e -> {
            int amount = 0;
            try {
                amount = Integer.parseInt(amountField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Enter valid number");
                return;
            }

            if (selectedPNo == null) {
                JOptionPane.showMessageDialog(frame, "Select a customer first");
                return;
            }

            if (selectedAccountId == 0) {
                JOptionPane.showMessageDialog(frame, "Select an account first");
                return;
            }

            boolean ok = bank.withdraw(selectedPNo, selectedAccountId, amount);

            if (ok) {
                JOptionPane.showMessageDialog(frame, "Withdraw successful");
            } else {
                JOptionPane.showMessageDialog(frame, "Error");
            }
            updateAccountList(selectedPNo);
            amountField.setText("");
        });

        //Stäng konto
        closeAccountButton.addActionListener(e -> {
            if (selectedPNo == null) {
                JOptionPane.showMessageDialog(frame, "Select a customer first");
                return;
            }

            String info = bank.closeAccount(selectedPNo, selectedAccountId);

            //Visar info om avslutat konto.
            JOptionPane.showMessageDialog(frame, info);

            updateAccountList(selectedPNo);
        });

        //Ta bort kund.
        deleteCustomerButton.addActionListener(e -> {
            if (selectedPNo == null) {
                JOptionPane.showMessageDialog(frame, "Select a customer first");
                return;
            }

            java.util.List<String> info = bank.deleteCustomer(selectedPNo);

            // Visar all information
            if (info != null) {
                JOptionPane.showMessageDialog(frame, info.toString());
            }

            updateCustomerList(); //Uppdaterar kundlistan
            accountModel.clear(); //Tömmer kontolistan
        });
    }

    /**
     * Uppdaterar kundlistan med alla kunder från BankLogic.
     * */
    private void updateCustomerList() {
        customerModel.clear();

        List<String> customers = bank.getAllCustomers();

        for (String c : customers) {
            customerModel.addElement(c);
        }
    }

    /**
     * Uppdtaerar kontolistan för vald kund.
     *
     * @param pNo personnummer för vald kund.
     * */
    private void updateAccountList(String pNo) {
        DefaultListModel<String> newModel = new DefaultListModel<>();

        List<String> accounts = bank.getCustomerAccounts(pNo);

        for (String a : accounts) {
            newModel.addElement(a);
        }

        accountList.setModel(newModel);

        //Uppdaterar GUI
        accountList.revalidate();
        accountList.repaint();
    }

    /**
     * Skapar meny fält med alternativ för framtida funktioner.
     *
     * */
    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Archive");

        JMenuItem save = new JMenuItem("Save Bank");
        JMenuItem load = new JMenuItem("Load Bank");
        JMenuItem saveTrans = new JMenuItem("Save transaction");

        save.addActionListener(e -> {
            if(bank.saveToFile()) {
                JOptionPane.showMessageDialog(frame, "Saved!");
            } else {
                JOptionPane.showMessageDialog(frame, "Error saving file");
            }
        });

        load.addActionListener(e -> {
            if(bank.loadFromFile()) {
                updateCustomerList();
                accountModel.clear();
                JOptionPane.showMessageDialog(frame, "Loaded!");
            } else {
                JOptionPane.showMessageDialog(frame, "Error loading file");
            }
        });

        saveTrans.addActionListener (e -> {
            if(selectedPNo == null || selectedAccountId == 0) {
                JOptionPane.showMessageDialog(frame, "Select customer and account");
                return;
            }

            if(bank.saveTransactionsToFile(selectedPNo, selectedAccountId)) {
                JOptionPane.showMessageDialog(frame, "Transactions saved!");
            } else {
                JOptionPane.showMessageDialog(frame, "Error saving transactions");
            }
        });

        menu.add(save);
        menu.add(load);
        menu.add(saveTrans);

        menuBar.add(menu);

        frame.setJMenuBar(menuBar);
    }

}
