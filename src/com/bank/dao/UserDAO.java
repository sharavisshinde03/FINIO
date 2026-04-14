package com.bank.dao;

import com.bank.model.User;
import com.bank.model.Transaction;
import java.util.List;

public interface UserDAO {

    void addUser(User user);

    User login(String email, String password);

    double getBalance(int userId);

    void deposit(int userId, double amount);

    void withdraw(int userId, double amount);

    List<Transaction> getTransactions(int userId);

    void deleteUser(int userId);

    void updateUser(User user);

    // 🔥 ADDED (SEARCH)
    User getUserByEmail(String email);
}