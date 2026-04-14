package com.bank.main;

import com.bank.model.Transaction;
import com.bank.service.UserService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TransactionUI extends JFrame {

    public TransactionUI(int userId) {

        setTitle("Transactions");
        setSize(600, 400);
        setLocationRelativeTo(null);

        UserService service = new UserService();
        List<Transaction> list = service.getTransactions(userId);

        String[] cols = {"Type", "Amount", "Date"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);

        for (Transaction t : list) {
            model.addRow(new Object[]{
                    t.getType(),
                    t.getAmount(),
                    t.getCreatedAt()
            });
        }

        JTable table = new JTable(model);
        add(new JScrollPane(table));

        setVisible(true);
    }
}