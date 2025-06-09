package com.project.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.project.Enums.Errors;
import com.project.Enums.ProductsOrederType;
import com.project.Model.Product;
import com.project.Utils.ExportEntity;

import javafx.collections.ObservableList;

public class ProductsDatabase {

    public static Errors setProduct(Product product) {
        String sql = "UPDATE Product SET name=?,buing_price=?,selling_price=?,quantity=?,cat_id=? WHERE id=?";

        try (Connection conn = Database.connect();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(6, product.getId());
            stmt.setString(1, product.getName());
            stmt.setFloat(2, product.getBuing_price());
            stmt.setFloat(3, product.getSelling_price());
            stmt.setInt(4, product.getQuantity());
            stmt.setInt(5, product.getCat_id());
            // stmt.setBytes(7, product.getImage_data().readAllBytes());
            if (stmt.executeUpdate() > 0) {
                return Errors.SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Errors.GLOBAL_ERROR;

    }

    public static Errors addProduct(Product product) {

        if (!UsersDatabse.currentUser.isAdmin())
            return Errors.PERMISSION_DENIED;
        String sql = "INSERT INTO Product(id,name,buing_price,selling_price,quantity,cat_id,image_data) VALUES(?,?,?,?,?,?,?)";

        try (Connection conn = Database.connect();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, product.getId());
            stmt.setString(2, product.getName());
            stmt.setFloat(3, product.getBuing_price());
            stmt.setFloat(4, product.getSelling_price());
            stmt.setInt(5, product.getQuantity());
            stmt.setInt(6, product.getCat_id());
            stmt.setBytes(7, product.getImage_data());
            if (stmt.executeUpdate() > 0) {
                return Errors.SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Errors.GLOBAL_ERROR;

    }

    public static Errors getAll(ObservableList<Product> obs, ProductsOrederType ord, String Serach, String cat) {
        obs.clear();
        String sql = "SELECT Product.*,Category.name AS category_name,Category.critical_quantity FROM Product "
                + " LEFT JOIN Category ON Product.cat_id=Category.id"
                + " WHERE (Product.id=? OR Product.name LIKE ?)"
                + " AND category_name=?"
                + " ORDER BY " + ord.getSqlName() + ";";
        try (Connection conn = Database.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, Serach);
            pstmt.setString(2, "%" + Serach + "%");
            pstmt.setString(3, cat);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                int cat_id = rs.getInt("cat_id");
                int q = rs.getInt("quantity");
                int cq = rs.getInt("critical_quantity");
                String name = rs.getString("name");
                float buing_price = rs.getInt("buing_price");
                float selling_price = rs.getInt("selling_price");
                byte[] image_data = rs.getBytes("image_data");
                String cat_name;
                if (cat_id == 0) {
                    cat_name = "None";
                } else {
                    cat_name = rs.getString("category_name");
                }
                obs.add(new Product(id, name, q, cq, cat_id, cat_name, selling_price, buing_price,
                        image_data));
            }
            return Errors.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Errors.GLOBAL_ERROR;
        }

    }

    public static Errors getAllCategory(ObservableList<Product> obs, ProductsOrederType ord, String cat) {
        obs.clear();
        String sql = "SELECT Product.*,Category.name AS category_name,Category.critical_quantity FROM Product "
                + " LEFT JOIN Category ON Product.cat_id=Category.id"
                + " WHERE category_name=?"
                + " ORDER BY " + ord.getSqlName() + ";";
        try (Connection conn = Database.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, cat);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                int cat_id = rs.getInt("cat_id");
                int q = rs.getInt("quantity");
                int cq = rs.getInt("critical_quantity");
                String name = rs.getString("name");
                float buing_price = rs.getInt("buing_price");
                float selling_price = rs.getInt("selling_price");
                byte[] image_data = rs.getBytes("image_data");
                String cat_name;
                if (cat_id == 0) {
                    cat_name = "None";
                } else {
                    cat_name = rs.getString("category_name");
                }
                obs.add(new Product(id, name, q, cq, cat_id, cat_name, selling_price, buing_price,
                        image_data));
            }
            return Errors.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Errors.GLOBAL_ERROR;
        }
    }

    public static Errors getAll(ObservableList<Product> obs, ProductsOrederType ord, String Serach) {
        obs.clear();
        String sql = "SELECT Product.*,Category.name AS category_name,Category.critical_quantity FROM Product "
                + " LEFT JOIN Category ON Product.cat_id=Category.id"
                + " WHERE Product.id=? OR Product.name LIKE ?"
                + " ORDER BY " + ord.getSqlName() + ";";
        try (Connection conn = Database.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, Serach);
            pstmt.setString(2, "%" + Serach + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                int cat_id = rs.getInt("cat_id");
                int q = rs.getInt("quantity");
                int cq = rs.getInt("critical_quantity");
                String name = rs.getString("name");
                float buing_price = rs.getInt("buing_price");
                float selling_price = rs.getInt("selling_price");
                byte[] image_data = rs.getBytes("image_data");
                String cat_name;
                if (cat_id == 0) {
                    cat_name = "None";
                } else {
                    cat_name = rs.getString("category_name");
                }
                obs.add(new Product(id, name, q, cq, cat_id, cat_name, selling_price, buing_price,
                        image_data));
            }
            return Errors.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Errors.GLOBAL_ERROR;
        }

    }

    public static Errors getAll(List<ExportEntity> obs, ProductsOrederType ord) {
        obs.clear();
        String sql = "SELECT Product.*,Category.name AS category_name,Category.critical_quantity FROM Product "
                + " LEFT JOIN Category ON Product.cat_id=Category.id"
                + " ORDER BY " + ord.getSqlName() + ";";
        try (Connection conn = Database.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);) {
            while (rs.next()) {
                String id = rs.getString("id");
                int cat_id = rs.getInt("cat_id");
                int q = rs.getInt("quantity");
                int cq = rs.getInt("critical_quantity");
                String name = rs.getString("name");
                float buing_price = rs.getInt("buing_price");
                float selling_price = rs.getInt("selling_price");
                byte[] image_data = rs.getBytes("image_data");
                String cat_name;
                if (cat_id == 0) {
                    cat_name = "None";
                } else {
                    cat_name = rs.getString("category_name");
                }
                obs.add(new Product(id, name, q, cq, cat_id, cat_name, selling_price, buing_price,
                        image_data));
            }
            return Errors.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Errors.GLOBAL_ERROR;
        }

    }

    public static Errors getAll(ObservableList<Product> obs, ProductsOrederType ord) {
        obs.clear();
        String sql = "SELECT Product.*,Category.name AS category_name,Category.critical_quantity FROM Product "
                + " LEFT JOIN Category ON Product.cat_id=Category.id"
                + " ORDER BY " + ord.getSqlName() + ";";
        try (Connection conn = Database.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);) {
            while (rs.next()) {
                String id = rs.getString("id");
                int cat_id = rs.getInt("cat_id");
                int q = rs.getInt("quantity");
                int cq = rs.getInt("critical_quantity");
                String name = rs.getString("name");
                float buing_price = rs.getInt("buing_price");
                float selling_price = rs.getInt("selling_price");
                byte[] image_data = rs.getBytes("image_data");
                String cat_name;
                if (cat_id == 0) {
                    cat_name = "None";
                } else {
                    cat_name = rs.getString("category_name");
                }
                obs.add(new Product(id, name, q, cq, cat_id, cat_name, selling_price, buing_price,
                        image_data));
            }
            return Errors.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Errors.GLOBAL_ERROR;
        }

    }

    public static Errors deleteCategory(String id) {
        if (!UsersDatabse.currentUser.isAdmin())
            return Errors.PERMISSION_DENIED;
        String sql = "DELETE FROM Product WHERE id=(?)";

        try (Connection conn = Database.connect();
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, id);
            if (stmt.executeUpdate() > 0) {
                return Errors.SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Errors.GLOBAL_ERROR;
    }

    public static Errors getLowOnStock(ObservableList<Product> obs) {
        String sql = "SELECT Product.*, Category.name AS category_name, Category.critical_quantity\r\n" + //
                "FROM Product\r\n" + //
                "LEFT JOIN Category ON Product.cat_id = Category.id\r\n" + //
                "WHERE Product.quantity < Category.critical_quantity;";
        try (Connection conn = Database.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);) {
            while (rs.next()) {
                String id = rs.getString("id");
                int cat_id = rs.getInt("cat_id");
                int q = rs.getInt("quantity");
                System.out.println(q);
                int cq = rs.getInt("critical_quantity");
                String name = rs.getString("name");
                float buing_price = rs.getInt("buing_price");
                float selling_price = rs.getInt("selling_price");
                byte[] image_data = rs.getBytes("image_data");
                String cat_name;
                if (cat_id == 0) {
                    cat_name = "None";
                } else {
                    cat_name = rs.getString("category_name");
                }
                obs.add(new Product(id, name, q, cq, cat_id, cat_name, selling_price, buing_price,
                        image_data));
            }
            return Errors.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Errors.GLOBAL_ERROR;
        }

    }

}
