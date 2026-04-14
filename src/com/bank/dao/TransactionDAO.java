package com.bank.dao;

import com.bank.model.Transaction;
import java.util.List;

public interface TransactionDAO {

    void addTransaction(int userId, String type, double amount);

    List<Transaction> getTransactions(int userId);
}