package com.project.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.project.Controllers.DashboardController;
import com.project.Enums.CategoryOrederType;
import com.project.Enums.Errors;
import com.project.Enums.HistoryOrederType;
import com.project.Model.Category;
import com.project.Model.History;
import com.project.Utils.ExportEntity;

import javafx.collections.ObservableList;
import javafx.util.Pair;

public class HistoryDatabase {

    public static Errors addHistory(History history) {

        String sql = "INSERT INTO History(action,quantity,price,user_id,product_id) VALUES(?,?,?,?,?)";

        try (Connection conn = Database.connect();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, history.getAction());
            stmt.setInt(2, history.getQuantity());
            stmt.setFloat(3, history.getPrice());
            stmt.setInt(4, history.getUser_id());
            stmt.setString(5, history.getProduct_id());
            if (stmt.executeUpdate() > 0) {
                return Errors.SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Errors.GLOBAL_ERROR;

    }

    public static Errors getAllUserHistory(List<History> obs, HistoryOrederType ord) {
        obs.clear();
        String sql = "SELECT History.*,Product.name,User.user_name FROM History " +
                "JOIN Product ON History.product_id=Product.id " +
                "JOIN User ON History.user_id=User.id " +
                "WHERE History.user_id=? " +
                "ORDER BY " + ord.getSqlName() + ";";
        try (Connection conn = Database.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, UsersDatabse.currentUser.getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                Timestamp t = rs.getTimestamp("occur_date");
                String product_name = rs.getString("name");
                String user_name = rs.getString("user_name");
                String product_id = rs.getString("product_id");
                int user_id = rs.getInt("user_id");
                int quantity = rs.getInt("quantity");
                int action = rs.getInt("action");
                float price = rs.getFloat("price");

                obs.add(new History(id, user_id, product_id, user_name, product_name, action, quantity, price, t));
            }
            return Errors.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Errors.GLOBAL_ERROR;
        }
    }

    public static Errors getAll(List<History> obs, HistoryOrederType ord) {
        obs.clear();
        String sql = "SELECT History.*,Product.name,User.user_name FROM History " +
                "JOIN Product ON History.product_id=Product.id " +
                "JOIN User ON History.user_id=User.id " +
                "ORDER BY " + ord.getSqlName() + ";";
        try (Connection conn = Database.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);) {
            while (rs.next()) {
                int id = rs.getInt("id");
                Timestamp t = rs.getTimestamp("occur_date");
                String product_name = rs.getString("name");
                String user_name = rs.getString("user_name");
                String product_id = rs.getString("product_id");
                int user_id = rs.getInt("user_id");
                int quantity = rs.getInt("quantity");
                int action = rs.getInt("action");
                float price = rs.getFloat("price");

                obs.add(new History(id, user_id, product_id, user_name, product_name, action, quantity, price, t));
            }
            return Errors.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Errors.GLOBAL_ERROR;
        }
    }

    public static Errors getAllUserHistoryExport(List<ExportEntity> obs, HistoryOrederType ord) {
        obs.clear();
        String sql = "SELECT History.*,Product.name,User.user_name FROM History " +
                "JOIN Product ON History.product_id=Product.id " +
                "JOIN User ON History.user_id=User.id " +
                "WHERE History.user_id=? " +
                "ORDER BY " + ord.getSqlName() + ";";
        try (Connection conn = Database.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, UsersDatabse.currentUser.getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                Timestamp t = rs.getTimestamp("occur_date");
                String product_name = rs.getString("name");
                String user_name = rs.getString("user_name");
                String product_id = rs.getString("product_id");
                int user_id = rs.getInt("user_id");
                int quantity = rs.getInt("quantity");
                int action = rs.getInt("action");
                float price = rs.getFloat("price");

                obs.add(new History(id, user_id, product_id, user_name, product_name, action, quantity, price, t));
            }
            return Errors.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Errors.GLOBAL_ERROR;
        }
    }

    public static Errors getAllExport(List<ExportEntity> obs, HistoryOrederType ord) {
        obs.clear();
        String sql = "SELECT History.*,Product.name,User.user_name FROM History " +
                "JOIN Product ON History.product_id=Product.id " +
                "JOIN User ON History.user_id=User.id " +
                "ORDER BY " + ord.getSqlName() + ";";
        try (Connection conn = Database.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);) {
            while (rs.next()) {
                int id = rs.getInt("id");
                Timestamp t = rs.getTimestamp("occur_date");
                String product_name = rs.getString("name");
                String user_name = rs.getString("user_name");
                String product_id = rs.getString("product_id");
                int user_id = rs.getInt("user_id");
                int quantity = rs.getInt("quantity");
                int action = rs.getInt("action");
                float price = rs.getFloat("price");

                obs.add(new History(id, user_id, product_id, user_name, product_name, action, quantity, price, t));
            }
            return Errors.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Errors.GLOBAL_ERROR;
        }
    }

    public static Errors getAll(ObservableList<Category> obs, CategoryOrederType ord) {
        obs.clear();
        String sql = "SELECT * FROM Category ORDER BY " + ord.getSqlName() + ";";
        try (Connection conn = Database.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int q = rs.getInt("quantity");
                String name = rs.getString("name");

                obs.add(new Category(name, id, q));
            }
            return Errors.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Errors.GLOBAL_ERROR;
        }

    }

    public static Errors deleteHistory(int id) {
        String sql = "DELETE FROM History WHERE id=(?)";

        try (Connection conn = Database.connect();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, id);
            if (stmt.executeUpdate() > 0) {
                return Errors.SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Errors.GLOBAL_ERROR;
    }

    public static Errors changeName(int id, String name) {
        String sql = "UPDATE Category SET name=(?) WHERE id=(?)";

        try (
                Connection conn = Database.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, name);
            pstmt.setInt(2, id);
            if (pstmt.executeUpdate() > 0) {
                return Errors.SUCCESS;
            }
            return Errors.GLOBAL_ERROR;

        } catch (SQLException e) {
            return Errors.GLOBAL_ERROR;
        }
    }
    public static Errors getTopSellingProducts(List<DashboardController.ProductInfo> list){
        String sql = " SELECT product_id , quantity , price FROM History WHERE occur_date >= datetime('now', '-1 month');";
        HashMap<String, Integer> map = new HashMap<>();
        HashMap<String, Double> map_price = new HashMap<>();
        try (Connection conn = Database.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);) {

            while (rs.next()) {
                String productId = rs.getString("product_id");
                Integer quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                map.put(productId, map.getOrDefault(productId, 0) + quantity);
                map_price.put(productId, map_price.getOrDefault(productId, (double) 0) + price);
            }
            for (Map.Entry<String, Integer> entry : map.entrySet()) {

                //System.out.println(entry.getKey() + ": " + entry.getValue());
                String sql2 = " SELECT name FROM Product WHERE id=(?);";
                try (PreparedStatement stmt2 = conn.prepareStatement(sql2);) 
                {
                    stmt2.setString(1, entry.getKey());
                    ResultSet rs2 = stmt2.executeQuery();
                    while (rs2.next()) {
                        String productname = rs2.getString("name");
                        Integer quantity = entry.getValue();
                        Double price = map_price.getOrDefault(entry.getKey(), (double) 0);
                        list.add(new DashboardController.ProductInfo (productname , quantity , price) );
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return Errors.SUCCESS;
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            return Errors.GLOBAL_ERROR;
        }
    }
}
