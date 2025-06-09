package com.project.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.project.Enums.CategoryOrederType;
import com.project.Enums.Errors;
import com.project.Model.Category;
import com.project.Utils.ExportEntity;

import javafx.collections.ObservableList;

public class CategoryDatabase {

    public static Errors addCategory(Category cat) {

        String sql = "INSERT INTO Category(name,critical_quantity) VALUES(?,?)";

        try (Connection conn = Database.connect();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, cat.getName());
            stmt.setInt(2, cat.getQuantity());
            if (stmt.executeUpdate() > 0) {
                return Errors.SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Errors.GLOBAL_ERROR;

    }

    public static Errors getAllExport(List<ExportEntity> obs) {
        obs.clear();
        String sql = "SELECT * FROM Category;";
        try (Connection conn = Database.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int cq = rs.getInt("critical_quantity");
                String name = rs.getString("name");

                obs.add(new Category(name, id, cq));
            }
            return Errors.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Errors.GLOBAL_ERROR;
        }

    }

    public static Errors getAll(List<Category> obs) {
        obs.clear();
        String sql = "SELECT id,name FROM Category;";
        try (Connection conn = Database.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");

                obs.add(new Category(name, id, 0));
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
                int q = rs.getInt("critical_quantity");
                String name = rs.getString("name");

                obs.add(new Category(name, id, q));
            }
            return Errors.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Errors.GLOBAL_ERROR;
        }

    }

    public static Errors deleteCategory(int id) {
        String sql = "DELETE FROM Category WHERE id=(?)";

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
}
