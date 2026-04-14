package com.bank.main;

import com.bank.service.UserService;
import com.bank.model.Transaction;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.List;

public class SimpleServer {

    static UserService userService = new UserService();

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // 🔹 BALANCE API
        server.createContext("/balance", (HttpExchange exchange) -> {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

            String query = exchange.getRequestURI().getQuery();
            int id = Integer.parseInt(query.split("=")[1]);

            double balance = userService.checkBalance(id);

            String response = String.valueOf(balance);

            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });

        // 🔹 DEPOSIT API
        server.createContext("/deposit", (exchange) -> {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

            String query = exchange.getRequestURI().getQuery();
            String[] params = query.split("&");

            int id = Integer.parseInt(params[0].split("=")[1]);
            double amt = Double.parseDouble(params[1].split("=")[1]);

            userService.deposit(id, amt);

            String response = "Deposit successful";

            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        });

        // 🔹 WITHDRAW API
        server.createContext("/withdraw", (exchange) -> {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

            String query = exchange.getRequestURI().getQuery();
            String[] params = query.split("&");

            int id = Integer.parseInt(params[0].split("=")[1]);
            double amt = Double.parseDouble(params[1].split("=")[1]);

            userService.withdraw(id, amt);

            String response = "Withdraw successful";

            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        });

        // 📊 TRANSACTIONS API
        server.createContext("/transactions", exchange -> {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

            String query = exchange.getRequestURI().getQuery();
            int userId = Integer.parseInt(query.split("=")[1]);

            List<Transaction> list = userService.getTransactions(userId);

            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < list.size(); i++) {
                Transaction t = list.get(i);
                json.append("{\"type\":\"")
                        .append(t.getType())
                        .append("\",\"amount\":")
                        .append(t.getAmount())
                        .append(",\"date\":\"")
                        .append(t.getCreatedAt()) // ✅ ADD DATE
                        .append("\"}");

                if (i < list.size() - 1) json.append(",");
            }
            json.append("]");

            exchange.sendResponseHeaders(200, json.length());
            exchange.getResponseBody().write(json.toString().getBytes());
            exchange.close();
        });

        System.out.println("🚀 Server started on http://localhost:8080");
        server.start();
    }
}