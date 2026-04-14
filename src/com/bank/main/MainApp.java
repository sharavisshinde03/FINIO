package com.bank.main;

import com.bank.model.User;
import com.bank.service.UserService;
import com.bank.util.PasswordUtil;

import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        UserService userService = new UserService();

        System.out.println("\n===== ONLINE BANKING SYSTEM =====");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.print("Enter choice: ");

        int choice = sc.nextInt();
        sc.nextLine(); // clear buffer

        switch (choice) {

            // 🔹 REGISTER
            case 1:
                System.out.print("Name: ");
                String name = sc.nextLine();

                System.out.print("Email: ");
                String email = sc.nextLine();

                System.out.print("Password: ");
                String password = PasswordUtil.hashPassword(sc.nextLine());

                User newUser = new User(0, name, email, password, 0.0);
                userService.register(newUser);

                System.out.println("Registration Successful! Please login.\n");

                // 👉 After register, go to login
                loginFlow(sc, userService);
                break;

            // 🔹 LOGIN
            case 2:
                loginFlow(sc, userService);
                break;

            default:
                System.out.println("Invalid choice!");
        }
    }

    // 🔥 LOGIN METHOD (Opens UI)
    private static void loginFlow(Scanner sc, UserService userService) {

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Password: ");
        String password = PasswordUtil.hashPassword(sc.nextLine());

        User user = userService.login(email, password);

        if (user != null) {
            System.out.println("Login Successful! Welcome " + user.getName());

            // 🚀 OPEN YOUR DASHBOARD UI
            new DashboardUI(user);

        } else {
            System.out.println("Invalid credentials!");
        }
    }
}