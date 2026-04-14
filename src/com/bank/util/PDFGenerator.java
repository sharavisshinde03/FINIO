package com.bank.util;

import com.bank.model.Transaction;

import java.io.FileWriter;
import java.util.List;

public class PDFGenerator {

    public static void generateStatement(List<Transaction> list) {
        try {
            FileWriter writer = new FileWriter("statement.txt");

            writer.write("BANK STATEMENT\n\n");

            for (Transaction t : list) {
                writer.write(
                        t.getType() + " | ₹" +
                                t.getAmount() + " | " +
                                t.getCreatedAt() + "\n"
                );
            }

            writer.close();
            System.out.println("Statement downloaded!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}