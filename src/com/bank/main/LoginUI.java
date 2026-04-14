package com.bank.main;

import com.bank.model.User;
import com.bank.service.UserService;

import javax.swing.*;
import java.awt.*;

public class LoginUI extends JFrame {

    private UserService userService = new UserService();

    public LoginUI() {

        setTitle("FINIO Login");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2));

        Color dark = new Color(10, 18, 32);
        Color accent = new Color(45, 212, 191);
        Color textColor = new Color(220, 230, 240);

        // 🔷 LEFT PANEL (FORM)
        JPanel left = new JPanel();
        left.setBackground(new Color(245, 247, 250));
        left.setLayout(null);

        JLabel title = new JLabel("Login to FINIO");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setBounds(50, 50, 300, 30);

        JLabel tagline = new JLabel("Secure Digital Banking Experience");
        tagline.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tagline.setForeground(Color.GRAY);
        tagline.setBounds(50, 80, 300, 20);

        JTextField email = new JTextField();
        email.setBorder(BorderFactory.createTitledBorder("Email"));
        email.setBounds(50, 140, 300, 50);

        JPasswordField password = new JPasswordField();
        password.setBorder(BorderFactory.createTitledBorder("Password"));
        password.setBounds(50, 210, 300, 50);

        JButton login = new JButton("Login");
        login.setBackground(new Color(20, 30, 60));
        login.setForeground(Color.BLACK);
        login.setFocusPainted(false);
        login.setBounds(50, 280, 300, 45);

        JButton register = new JButton("Create Account");
        register.setBackground(accent);
        register.setForeground(Color.BLACK);
        register.setFocusPainted(false);
        register.setBounds(50, 340, 300, 45);

        left.add(title);
        left.add(tagline);
        left.add(email);
        left.add(password);
        left.add(login);
        left.add(register);

        // 🔷 RIGHT PANEL (FINTECH STYLE)
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
        logo.setBounds(120, 180, 300, 40);

        JLabel sub = new JLabel("Smart. Secure. Seamless Banking.");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sub.setForeground(textColor);
        sub.setBounds(90, 230, 300, 20);

        right.add(logo);
        right.add(sub);

        add(left);
        add(right);

        // 🔥 LOGIN ACTION
        login.addActionListener(e -> {

            String emailText = email.getText().trim();
            String passwordText = new String(password.getPassword()).trim();

            User user = userService.login(emailText, passwordText);

            if (user != null) {
                dispose();
                new DashboardUI(user);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid login");
            }
        });

        register.addActionListener(e -> new RegisterUI());

        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginUI();
    }
}