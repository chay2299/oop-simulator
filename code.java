import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

// BankAccount class
class BankAccount {
    private static int accountCounter = 1000;
    private int accountNumber;
    private double balance;

    public BankAccount(double initialBalance) {
        this.accountNumber = accountCounter++;
        this.balance = initialBalance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        }
    }

    public void credit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    @Override
    public String toString() {
        return "Account Number: " + accountNumber + ", Balance: " + balance;
    }
}

// Customer class
class Customer {
    private String username;
    private String password;
    private String name;
    private String address;
    private BankAccount account;

    public Customer(String username, String password, String name, String address, double initialDeposit) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.address = address;
        this.account = new BankAccount(initialDeposit);
    }

    public String getUsername() {
        return username;
    }

    public boolean verifyPassword(String password) {
        return this.password.equals(password);
    }

    public BankAccount getAccount() {
        return account;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Address: " + address + ", " + account.toString();
    }
}

// GUI Application
public class BankingSystemWithLogin {

    private Map<String, Customer> customers = new HashMap<>();
    private Customer loggedInCustomer = null;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BankingSystemWithLogin::new);
    }

    public BankingSystemWithLogin() {
        setPastelTheme();

        JFrame frame = new JFrame("Banking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));
        panel.setBackground(new Color(245, 245, 255));

        JButton registerButton = createButton("Register");
        JButton loginButton = createButton("Login");
        JButton exitButton = createButton("Exit");

        panel.add(registerButton);
        panel.add(loginButton);
        panel.add(exitButton);

        registerButton.addActionListener(e -> registerCustomer());
        loginButton.addActionListener(e -> loginCustomer());
        exitButton.addActionListener(e -> System.exit(0));

        frame.add(panel);
        frame.setVisible(true);
    }

    private void setPastelTheme() {
        try {
            UIManager.setLookAndFeel(new MetalLookAndFeel());
            UIManager.put("Button.background", new Color(204, 229, 255));
            UIManager.put("Button.foreground", Color.DARK_GRAY);
            UIManager.put("Button.font", new Font("Arial", Font.PLAIN, 14));
            UIManager.put("Panel.background", new Color(245, 245, 255));
            UIManager.put("OptionPane.background", new Color(255, 240, 245));
            UIManager.put("OptionPane.messageForeground", Color.DARK_GRAY);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(204, 229, 255));
        button.setForeground(Color.DARK_GRAY);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(153, 204, 255));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(204, 229, 255));
            }
        });

        return button;
    }

    private void registerCustomer() {
        JTextField usernameField = new JTextField();
        JTextField passwordField = new JPasswordField();
        JTextField nameField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField depositField = new JTextField();

        Object[] message = {
                "Username:", usernameField,
                "Password:", passwordField,
                "Name:", nameField,
                "Address:", addressField,
                "Initial Deposit:", depositField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Register", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String name = nameField.getText();
            String address = addressField.getText();
            try {
                double deposit = Double.parseDouble(depositField.getText());
                if (customers.containsKey(username)) {
                    JOptionPane.showMessageDialog(null, "Username already exists.");
                } else {
                    Customer customer = new Customer(username, password, name, address, deposit);
                    customers.put(username, customer);
                    JOptionPane.showMessageDialog(null, "Registration successful!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid deposit amount.");
            }
        }
    }

    private void loginCustomer() {
        JTextField usernameField = new JTextField();
        JTextField passwordField = new JPasswordField();

        Object[] message = {
                "Username:", usernameField,
                "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = passwordField.getText();

            Customer customer = customers.get(username);
            if (customer != null && customer.verifyPassword(password)) {
                loggedInCustomer = customer;
                JOptionPane.showMessageDialog(null, "Login successful!");
                showCustomerMenu();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password.");
            }
        }
    }

    private void showCustomerMenu() {
        JFrame frame = new JFrame("Customer Menu");
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));
        panel.setBackground(new Color(245, 245, 255));

        JButton depositButton = createButton("Deposit");
        JButton withdrawButton = createButton("Withdraw");
        JButton viewBalanceButton = createButton("View Balance");
        JButton sendMoneyButton = createButton("Send Money");
        JButton requestCreditButton = createButton("Request Credit");
        JButton logoutButton = createButton("Logout");

        panel.add(depositButton);
        panel.add(withdrawButton);
        panel.add(viewBalanceButton);
        panel.add(sendMoneyButton);
        panel.add(requestCreditButton);
        panel.add(logoutButton);

        depositButton.addActionListener(e -> handleTransaction(true));
        withdrawButton.addActionListener(e -> handleTransaction(false));
        viewBalanceButton.addActionListener(e -> viewBalance());
        sendMoneyButton.addActionListener(e -> sendMoney());
        requestCreditButton.addActionListener(e -> requestCredit());
        logoutButton.addActionListener(e -> {
            loggedInCustomer = null;
            frame.dispose();
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private void handleTransaction(boolean isDeposit) {
        String action = isDeposit ? "Deposit" : "Withdraw";
        String amountString = JOptionPane.showInputDialog("Enter amount to " + action + ":");
        try {
            double amount = Double.parseDouble(amountString);
            if (isDeposit) {
                loggedInCustomer.getAccount().deposit(amount);
                JOptionPane.showMessageDialog(null, action + " successful!");
            } else if (loggedInCustomer.getAccount().getBalance() >= amount) {
                loggedInCustomer.getAccount().withdraw(amount);
                JOptionPane.showMessageDialog(null, action + " successful!");
            } else {
                JOptionPane.showMessageDialog(null, "Insufficient funds.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid amount.");
        }
    }

    private void viewBalance() {
        JOptionPane.showMessageDialog(null, "Balance: " + loggedInCustomer.getAccount().getBalance());
    }

    private void sendMoney() {
        String recipientUsername = JOptionPane.showInputDialog("Enter the recipient's username:");
        if (!customers.containsKey(recipientUsername)) {
            JOptionPane.showMessageDialog(null, "Recipient not found.");
            return;
        }

        String amountString = JOptionPane.showInputDialog("Enter amount to send:");
        try {
            double amount = Double.parseDouble(amountString);
            if (loggedInCustomer.getAccount().getBalance() >= amount) {
                loggedInCustomer.getAccount().withdraw(amount);
                customers.get(recipientUsername).getAccount().deposit(amount);
                JOptionPane.showMessageDialog(null, "Money sent successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Insufficient funds.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid amount.");
        }
    }

    private void requestCredit() {
        double balance = loggedInCustomer.getAccount().getBalance();
        if (balance >= 10000) {
            double creditLimit = balance * 0.2;
            String amountString = JOptionPane.showInputDialog("Enter amount to request credit (up to 20% of balance):");
            try {
                double creditAmount = Double.parseDouble(amountString);
                if (creditAmount <= creditLimit) {
                    loggedInCustomer.getAccount().credit(creditAmount);
                    JOptionPane.showMessageDialog(null, "Credit of " + creditAmount + " approved and added to your balance.");
                } else {
                    JOptionPane.showMessageDialog(null, "Credit request exceeds the 20% limit.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid amount.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Minimum balance of 10,000 required for credit.");
        }
    }
}
