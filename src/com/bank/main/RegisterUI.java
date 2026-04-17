package com.bank.main;

import com.bank.model.User;
import com.bank.service.UserService;

import javax.swing.*;
import java.awt.*;

public class RegisterUI extends JFrame {

    private UserService userService = new UserService();

    public RegisterUI() {

        setTitle("FINIO Register");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 2));

        Color dark = new Color(10, 18, 32);
        Color accent = new Color(45, 212, 191);
        Color textColor = new Color(220, 230, 240);

        // 🔷 LEFT PANEL
        JPanel left = new JPanel();
        left.setBackground(new Color(245, 247, 250));
        left.setLayout(null);

        JLabel title = new JLabel("Create FINIO Account");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setBounds(50, 50, 350, 30);

        JLabel tagline = new JLabel("Join the future of banking");
        tagline.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tagline.setForeground(Color.GRAY);
        tagline.setBounds(50, 80, 300, 20);

        JTextField name = new JTextField();
        name.setBorder(BorderFactory.createTitledBorder("Full Name"));
        name.setBounds(50, 140, 300, 50);

        JTextField email = new JTextField();
        email.setBorder(BorderFactory.createTitledBorder("Email"));
        email.setBounds(50, 210, 300, 50);

        JPasswordField password = new JPasswordField();
        password.setBorder(BorderFactory.createTitledBorder("Password"));
        password.setBounds(50, 280, 300, 50);

        JButton register = new JButton("Sign Up");
        register.setBackground(new Color(20, 30, 60));
        register.setForeground(Color.BLACK);
        register.setFocusPainted(false);
        register.setBounds(50, 350, 300, 45);

        left.add(title);
        left.add(tagline);
        left.add(name);
        left.add(email);
        left.add(password);
        left.add(register);

        // 🔷 RIGHT PANEL
        JPanel right = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;

                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(10, 18, 32),
                        getWidth(), getHeight(), new Color(30, 64, 175)
                );

                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        right.setLayout(null);

        JLabel logo = new JLabel("FINIO");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 36));
        logo.setForeground(accent);
        logo.setBounds(120, 200, 300, 40);

        JLabel sub = new JLabel("Banking Made Simple & Powerful");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sub.setForeground(textColor);
        sub.setBounds(90, 250, 300, 20);

        right.add(logo);
        right.add(sub);

        add(left);
        add(right);

        // 🔥 REGISTER ACTION
        register.addActionListener(e -> {
            try {

                String userName = name.getText().trim();
                String userEmail = email.getText().trim();
                String userPassword = new String(password.getPassword()).trim();

                // 🔒 EMPTY VALIDATION
                if (userName.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields are required!");
                    return;
                }

                // 🔒 EMAIL VALIDATION
                if (!userEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                    JOptionPane.showMessageDialog(this, "Enter valid email!");
                    return;
                }

                // 🔥 DUPLICATE EMAIL CHECK (ADDED)
                if (userService.searchUser(userEmail) != null) {
                    JOptionPane.showMessageDialog(this, "Email already registered!");
                    return;
                }

                User u = new User();
                u.setName(userName);
                u.setEmail(userEmail);
                u.setPassword(userPassword);
                u.setBalance(0);

                // 🔥 GET GENERATED ID FROM DB
                int generatedId = userService.register(u);

                JOptionPane.showMessageDialog(
                        this,
                        "Registered Successfully!\n\nYour Customer ID: " + generatedId
                );

                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error registering user!");
            }
        });

        setVisible(true);
    }
}