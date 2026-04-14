package com.bank.dao;

import com.bank.model.User;
import com.bank.model.Transaction;
import com.bank.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public void addUser(User user) {
        try (Connection con = DBConnection.getConnection()) {

            String sql = "INSERT INTO users (id, name, email, password, balance) VALUES (user_seq.NEXTVAL, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, user.getName().trim());
            ps.setString(2, user.getEmail().trim());
            ps.setString(3, user.getPassword().trim());
            ps.setDouble(4, user.getBalance());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public User login(String email, String password) {
        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT * FROM users WHERE LOWER(email)=LOWER(?) AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, email.trim());
            ps.setString(2, password.trim());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getDouble("balance")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public double getBalance(int userId) {
        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT balance FROM users WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("balance");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void deposit(int userId, double amount) {
        try (Connection con = DBConnection.getConnection()) {

            String sql = "UPDATE users SET balance = balance + ? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setDouble(1, amount);
            ps.setInt(2, userId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void withdraw(int userId, double amount) {
        try (Connection con = DBConnection.getConnection()) {

            String sql = "UPDATE users SET balance = balance - ? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setDouble(1, amount);
            ps.setInt(2, userId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔥 FIXED DELETE (SAFE)
    @Override
    public void deleteUser(int userId) {
        try (Connection con = DBConnection.getConnection()) {

            // delete transactions first
            PreparedStatement ps1 = con.prepareStatement(
                    "DELETE FROM transactions WHERE user_id=?"
            );
            ps1.setInt(1, userId);
            ps1.executeUpdate();

            // delete user
            PreparedStatement ps2 = con.prepareStatement(
                    "DELETE FROM users WHERE id=?"
            );
            ps2.setInt(1, userId);
            ps2.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(User user) {
        try (Connection con = DBConnection.getConnection()) {

            String sql = "UPDATE users SET name=?, email=?, password=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔥 NEW SEARCH METHOD
    @Override
    public User getUserByEmail(String email) {

        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT * FROM users WHERE LOWER(email)=LOWER(?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, email.trim());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getDouble("balance")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Transaction> getTransactions(int userId) {

        List<Transaction> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT * FROM transactions WHERE user_id=? ORDER BY date_time DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Transaction t = new Transaction();
                t.setType(rs.getString("type"));
                t.setAmount(rs.getDouble("amount"));
                t.setCreatedAt(rs.getTimestamp("date_time").toString());
                list.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}