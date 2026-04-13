package nattrn2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Stack;

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

    public BankGUI(){
        bank = new BankLogic();
        TestData.setupBank(bank);
        createGUI();
    }

    public static void main(String[] args) {
        new BankGUI();
    }

    private void createGUI() {
        createFrame();
        createPanels();
        createCustomerList();
        createAccountList();
        createButtons();
        createMenu();
        frame.setVisible(true);
    }

    private void createFrame() {
        frame = new JFrame("Bank");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
    }

    private void createPanels() {
        leftPanel = new JPanel(new BorderLayout());
        middlePanel = new JPanel(new BorderLayout());
        rightPanel = new JPanel();

        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(middlePanel, BorderLayout.CENTER);
        frame.add(rightPanel, BorderLayout.EAST);
    }

    private void createCustomerList() {
        customerModel = new DefaultListModel<>();
        customerList = new JList<>(customerModel);
        selectedAccountId = 0;

        leftPanel.add(new JScrollPane(customerList), BorderLayout.CENTER);

        updateCustomerList();

        customerList.addListSelectionListener(e -> {
            String selected = customerList.getSelectedValue();
                if (!e.getValueIsAdjusting()) {
                    selectedPNo = selected.split(" ")[0];

                    updateAccountList(selectedPNo);
        }}
        );
    }

    private void createAccountList() {
        accountModel = new DefaultListModel<>();
        accountList = new JList<>(accountModel);

        middlePanel.add(new JScrollPane(accountList), BorderLayout.CENTER);

        accountList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = accountList.getSelectedValue();

                if(selected != null) {
                    String[] parts = selected.split(" ");
                    selectedAccountId = Integer.parseInt(parts[1]);
                }
            }}
        );
    }

    private void createButtons() {
        JButton depositButton = new JButton("Deposit money");
        JButton withdrawButton = new JButton("Withdraw money");
        JButton closeAccountButton = new JButton("Close account");
        JButton deleteCustomerButton = new JButton("Delete customer");
        JTextField amountField = new JTextField(10);

        rightPanel.add(depositButton);
        rightPanel.add(withdrawButton);
        rightPanel.add(closeAccountButton);
        rightPanel.add(deleteCustomerButton);
        rightPanel.add(amountField);

        depositButton.addActionListener(e -> {
            int amount = Integer.parseInt(amountField.getText());

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
            updateAccountList(selectedPNo);
        });

        withdrawButton.addActionListener(e -> {
            int amount = Integer.parseInt(amountField.getText());

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
        });

        closeAccountButton.addActionListener(e -> {
            if (selectedPNo == null) {
                JOptionPane.showMessageDialog(frame, "Select a customer first");
                return;
            }

            String info = bank.closeAccount(selectedPNo, selectedAccountId);

            JOptionPane.showMessageDialog(frame, info);

            updateAccountList(selectedPNo);
        });

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

            // Uppdaterar GUI
            updateCustomerList();
            accountModel.clear();
        });
    }

    private void updateCustomerList() {
        customerModel.clear();

        List<String> customers = bank.getAllCustomers();

        for (String c : customers) {
            customerModel.addElement(c);
        }
    }

    private void updateAccountList(String pNo) {
        if (pNo == null) return;

        accountModel.clear();

        List<String> accounts = bank.getCustomerAccounts(pNo);

        for (String a : accounts) {
            accountModel.addElement(a);
        }
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Archive");

        JMenuItem save = new JMenuItem("Save Bank");
        JMenuItem load = new JMenuItem("Load Bank");
        JMenuItem saveTrans = new JMenuItem("Save transaction");

        menu.add(save);
        menu.add(load);
        menu.add(saveTrans);

        menuBar.add(menu);

        frame.setJMenuBar(menuBar);
    }

}
