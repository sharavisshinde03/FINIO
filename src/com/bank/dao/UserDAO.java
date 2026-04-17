package com.bank.dao;

import com.bank.model.User;
import com.bank.model.Transaction;
import java.util.List;

public interface UserDAO {

    int addUser(User user); // 🔥 changed from void → int

    User login(String email, String password);

    double getBalance(int userId);

    void deposit(int userId, double amount);

    void withdraw(int userId, double amount);

    void deleteUser(int userId);

    void updateUser(User user);
    User loginById(int id, String password);
    User getUserByEmail(String email);

    List<Transaction> getTransactions(int userId);
}