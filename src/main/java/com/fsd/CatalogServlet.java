package com.fsd;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/catalog")
public class CatalogServlet extends HttpServlet {

    private static final String URL =
            "jdbc:mysql://localhost:3306/productdb";
    private static final String USER = "root";
    private static final String PASSWORD = "akhila";

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String category = request.getParameter("category");
        List<Product> products = new ArrayList<>();

        String sql;

        if (category == null || category.isBlank()
                || category.equals("All")) {
            sql = "SELECT * FROM products";
        } else {
            sql = "SELECT * FROM products WHERE category = ?";
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection con =
                         DriverManager.getConnection(URL, USER, PASSWORD);
                 PreparedStatement ps = con.prepareStatement(sql)) {

                if (sql.contains("?")) {
                    ps.setString(1, category);
                }

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        products.add(new Product(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("category"),
                                rs.getDouble("price")
                        ));
                    }
                }
            }

            request.setAttribute("products", products);
            request.setAttribute("selectedCategory",
                    category == null || category.isBlank()
                            ? "All" : category);

            request.getRequestDispatcher("/catalog.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            throw new ServletException(
                    "Unable to retrieve products from MySQL.", e);
        }
    }

    public static class Product {
        private final int id;
        private final String name;
        private final String category;
        private final double price;

        public Product(int id, String name,
                       String category, double price) {
            this.id = id;
            this.name = name;
            this.category = category;
            this.price = price;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getCategory() {
            return category;
        }

        public double getPrice() {
            return price;
        }
    }
}