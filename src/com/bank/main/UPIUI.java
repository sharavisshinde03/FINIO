package com.bank.main;

import com.bank.service.UserService;

import javax.swing.*;
import java.awt.*;

public class UPIUI extends JFrame {

    private UserService service;
    private int userId;

    // ✅ Correct type
    private DashboardUI dashboard;

    // ✅ FIXED constructor
    public UPIUI(int userId, DashboardUI dashboard) {
        this.userId = userId;
        this.dashboard = dashboard;
        this.service = new UserService();

        setTitle("FINIO UPI Payment");
        setSize(420, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(createHeader(), BorderLayout.NORTH);
        add(createMainPanel(), BorderLayout.CENTER);

        getContentPane().setBackground(new Color(8, 18, 40));
        setVisible(true);
    }

    // ================= HEADER =================
    private JPanel createHeader() {

        JPanel header = new JPanel();
        header.setBackground(new Color(20, 180, 170));
        header.setPreferredSize(new Dimension(400, 80));

        JLabel title = new JLabel("Payment");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        header.add(title);
        return header;
    }

    // ================= MAIN =================
    private JPanel createMainPanel() {

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        String[] currencies = {"INR ₹", "USD $", "EUR €", "GBP £"};
        JComboBox<String> currencyBox = new JComboBox<>(currencies);
        currencyBox.setBounds(20, 20, 120, 30);
        panel.add(currencyBox);

        JLabel account = new JLabel("Account: FINIO BANK");
        account.setBounds(160, 20, 200, 30);
        panel.add(account);

        JTextField amount = new JTextField();
        amount.setBounds(20, 60, 360, 40);
        amount.setFont(new Font("Segoe UI", Font.BOLD, 16));
        amount.setBorder(BorderFactory.createTitledBorder("Enter Amount"));
        panel.add(amount);

        JLabel opt = new JLabel("Payment Options");
        opt.setBounds(20, 110, 200, 20);
        panel.add(opt);

        JRadioButton debit = new JRadioButton("Debit Card");
        JRadioButton net = new JRadioButton("Net Banking");
        JRadioButton wallet = new JRadioButton("Wallet");
        JRadioButton upi = new JRadioButton("UPI");

        debit.setBounds(20, 140, 200, 30);
        net.setBounds(20, 170, 200, 30);
        wallet.setBounds(20, 200, 200, 30);
        upi.setBounds(20, 230, 200, 30);

        ButtonGroup group = new ButtonGroup();
        group.add(debit);
        group.add(net);
        group.add(wallet);
        group.add(upi);

        panel.add(debit);
        panel.add(net);
        panel.add(wallet);
        panel.add(upi);

        JTextField upiField = new JTextField();
        upiField.setBounds(20, 270, 240, 40);
        upiField.setBorder(BorderFactory.createTitledBorder("Enter UPI ID or Phone"));

        JButton verify = new JButton("Verify");
        verify.setBounds(270, 270, 110, 40);
        verify.setBackground(new Color(20, 180, 170));
        verify.setForeground(Color.BLACK);

        panel.add(upiField);
        panel.add(verify);

        JButton pay = new JButton("PAY NOW");
        pay.setBounds(20, 340, 360, 50);
        pay.setBackground(new Color(20, 180, 170));
        pay.setForeground(Color.BLACK);
        pay.setFont(new Font("Segoe UI", Font.BOLD, 16));

        panel.add(pay);

        // ================= VERIFY =================
        verify.addActionListener(e -> {

            String input = upiField.getText().trim();

            if (isValidUPI(input) || isValidPhone(input)) {
                JOptionPane.showMessageDialog(this, "Verified Successfully");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid UPI ID / Phone Number");
            }
        });

        // ================= PAY =================
        pay.addActionListener(e -> {
            try {

                String input = upiField.getText().trim();

                if (!(isValidUPI(input) || isValidPhone(input))) {
                    JOptionPane.showMessageDialog(this, "Enter valid UPI ID or Phone");
                    return;
                }

                if (amount.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Amount cannot be empty");
                    return;
                }

                double amt = Double.parseDouble(amount.getText());

                if (amt <= 0) {
                    JOptionPane.showMessageDialog(this, "Enter valid amount");
                    return;
                }

                String selectedCurrency = (String) currencyBox.getSelectedItem();
                String symbol = getSymbol(selectedCurrency);

                boolean success = service.withdraw(userId, amt);

                if (!success) {
                    JOptionPane.showMessageDialog(this, "Insufficient Balance!");
                    return;
                }

                // 🔥 LIVE REFRESH
                dashboard.refreshBalance();
                dashboard.refreshTransactions();

                JOptionPane.showMessageDialog(
                        this,
                        "Payment Successful!\nTo: " + input + "\nAmount: " + symbol + " " + amt
                );

                dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter valid numeric amount");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Something went wrong");
            }
        });

        return panel;
    }

    private boolean isValidUPI(String input) {
        return input.matches("^[a-zA-Z0-9._-]+@[a-zA-Z]+$");
    }

    private boolean isValidPhone(String input) {
        return input.matches("\\d{10}");
    }

    private String getSymbol(String currency) {
        if (currency.contains("INR")) return "₹";
        if (currency.contains("USD")) return "$";
        if (currency.contains("EUR")) return "€";
        if (currency.contains("GBP")) return "£";
        return "₹";
    }
}