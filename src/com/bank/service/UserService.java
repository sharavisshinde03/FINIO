package com.bank.service;

import com.bank.dao.*;
import com.bank.model.*;

import java.util.List;

public class UserService {

    private UserDAO userDAO = new UserDAOImpl();
    private TransactionDAO transactionDAO = new TransactionDAOImpl();

    public void register(User user) {
        userDAO.addUser(user);
    }

    public User login(String email, String password) {
        return userDAO.login(email, password);
    }

    public void deposit(int id, double amount) {
        userDAO.deposit(id, amount);
        transactionDAO.addTransaction(id, "CREDIT", amount);
    }

    public void withdraw(int id, double amount) {
        userDAO.withdraw(id, amount);
        transactionDAO.addTransaction(id, "DEBIT", amount);
    }

    public double checkBalance(int id) {
        return userDAO.getBalance(id);
    }

    public List<Transaction> getTransactions(int userId) {
        return transactionDAO.getTransactions(userId);
    }

    // 🔥 NEW FEATURES

    public User searchUser(String email) {
        return userDAO.getUserByEmail(email);
    }

    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    public void deleteUser(int userId) {
        userDAO.deleteUser(userId);
    }
}