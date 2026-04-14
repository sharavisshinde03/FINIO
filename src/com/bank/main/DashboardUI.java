package com.bank.main;

import com.bank.model.User;
import com.bank.model.Transaction;
import com.bank.service.UserService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class DashboardUI extends JFrame {

    private UserService userService;
    private User currentUser;

    private JLabel balanceLabel;
    private DefaultTableModel tableModel;
    private JTable table;

    public DashboardUI(User user) {
        this.userService = new UserService();
        this.currentUser = user;

        setTitle("FINIO Dashboard");
        setSize(1300, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);
        add(createMainPanel(), BorderLayout.CENTER);

        refreshData();
        setVisible(true);
    }

    // ================= SIDEBAR =================
    private JPanel createSidebar() {

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(10, 18, 32));
        sidebar.setPreferredSize(new Dimension(230, 750));

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBackground(new Color(10, 18, 32));
        top.setBorder(BorderFactory.createEmptyBorder(40, 10, 40, 10));

        JLabel logo = new JLabel("FINIO");
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        logo.setForeground(new Color(45, 212, 191));
        logo.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JLabel user = new JLabel("Hello, " + currentUser.getName());
        user.setAlignmentX(Component.CENTER_ALIGNMENT);
        user.setForeground(Color.WHITE);
        user.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        top.add(logo);
        top.add(Box.createVerticalStrut(8));
        top.add(user);

        sidebar.add(top);
        sidebar.add(Box.createVerticalStrut(20));

        sidebar.add(createSidebarItem("Dashboard"));
        sidebar.add(createSidebarItem("Deposit"));
        sidebar.add(createSidebarItem("Withdraw"));
        sidebar.add(createSidebarItem("Transactions"));
        sidebar.add(createSidebarItem("UPI"));
        sidebar.add(createSidebarItem("Download"));
        sidebar.add(createSidebarItem("Exit"));

        return sidebar;
    }

    private JPanel createSidebarItem(String text) {

        JPanel item = new JPanel(new BorderLayout());
        item.setBackground(new Color(10, 18, 32));
        item.setMaximumSize(new Dimension(220, 50));
        item.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        item.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel label = new JLabel(text);
        label.setForeground(new Color(180, 200, 230));
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));

        item.add(label, BorderLayout.CENTER);

        item.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                item.setBackground(new Color(30, 41, 59));
            }

            public void mouseExited(MouseEvent e) {
                item.setBackground(new Color(10, 18, 32));
            }

            public void mouseClicked(MouseEvent e) {
                handleAction(text);
            }
        });

        return item;
    }

    // ================= MAIN =================
    private JPanel createMainPanel() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(18, 28, 45));

        panel.add(createTopCards(), BorderLayout.NORTH);

        // 🔥 DELETE BUTTON
        JButton deleteBtn = new JButton("Delete Selected");
        deleteBtn.setBackground(new Color(220, 38, 38));
        deleteBtn.setForeground(Color.BLACK);
        deleteBtn.setPreferredSize(new Dimension(160, 35));

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a transaction first");
            } else {
                tableModel.removeRow(row);
            }
        });

        // 🔥 RIGHT ALIGN FIX
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topBar.setBackground(new Color(18, 28, 45));
        topBar.add(deleteBtn);

        JPanel tableWrapper = new JPanel(new BorderLayout());
        tableWrapper.add(topBar, BorderLayout.NORTH);
        tableWrapper.add(createTransactionTable(), BorderLayout.CENTER);

        panel.add(tableWrapper, BorderLayout.CENTER);

        return panel;
    }

    // ================= CARDS =================
    private JPanel createTopCards() {

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 20));
        top.setBackground(new Color(18, 28, 45));

        top.add(createDebitCard());
        top.add(createCreditCard());

        return top;
    }

    private JPanel createDebitCard() {

        JPanel card = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(99, 102, 241),
                        getWidth(), getHeight(), new Color(59, 130, 246)
                );
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
            }
        };

        card.setLayout(null);
        card.setPreferredSize(new Dimension(350, 200));

        JLabel type = new JLabel("DEBIT");
        type.setForeground(Color.WHITE);
        type.setBounds(20, 20, 100, 20);
        card.add(type);

        JLabel number = new JLabel("3829 4820 4629 5025");
        number.setForeground(Color.WHITE);
        number.setBounds(20, 80, 300, 30);
        card.add(number);

        JLabel name = new JLabel(currentUser.getName().toUpperCase());
        name.setForeground(Color.WHITE);
        name.setBounds(20, 140, 200, 20);
        card.add(name);

        balanceLabel = new JLabel("₹ 0");
        balanceLabel.setForeground(new Color(250, 204, 21));
        balanceLabel.setBounds(220, 140, 120, 20);
        card.add(balanceLabel);

        return card;
    }

    private JPanel createCreditCard() {

        JPanel card = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(20, 184, 166),
                        getWidth(), getHeight(), new Color(45, 212, 191)
                );
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
            }
        };

        card.setLayout(null);
        card.setPreferredSize(new Dimension(350, 200));

        JLabel type = new JLabel("CREDIT");
        type.setForeground(Color.BLACK);
        type.setBounds(20, 20, 100, 20);
        card.add(type);

        JLabel number = new JLabel("5299 1122 3344 7788");
        number.setForeground(Color.BLACK);
        number.setBounds(20, 80, 300, 30);
        card.add(number);

        JLabel name = new JLabel(currentUser.getName().toUpperCase());
        name.setForeground(Color.BLACK);
        name.setBounds(20, 140, 200, 20);
        card.add(name);

        return card;
    }

    // ================= TABLE =================
    private JScrollPane createTransactionTable() {

        String[] cols = {"Transaction", "Amount", "Type"};
        tableModel = new DefaultTableModel(cols, 0);

        table = new JTable(tableModel);
        table.setRowHeight(35);

        return new JScrollPane(table);
    }

    // ================= ACTION =================
    private void handleAction(String action) {

        try {

            if (action.equals("Deposit")) {
                String amt = JOptionPane.showInputDialog("Enter amount:");
                if (amt != null) {
                    userService.deposit(currentUser.getId(), Double.parseDouble(amt));
                    refreshData();
                }
            }

            else if (action.equals("Withdraw")) {
                String amt = JOptionPane.showInputDialog("Enter amount:");
                if (amt != null) {
                    userService.withdraw(currentUser.getId(), Double.parseDouble(amt));
                    refreshData();
                }
            }

            else if (action.equals("UPI")) {
                new UPIUI(currentUser.getId());
            }

            else if (action.equals("Exit")) {
                System.exit(0);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input");
        }
    }

    // ================= REFRESH =================
    private void refreshData() {

        double balance = userService.checkBalance(currentUser.getId());
        balanceLabel.setText("₹ " + balance);

        tableModel.setRowCount(0);

        List<Transaction> list = userService.getTransactions(currentUser.getId());

        for (Transaction t : list) {
            tableModel.addRow(new Object[]{
                    t.getType(),
                    t.getAmount(),
                    t.getType()
            });
        }
    }
}