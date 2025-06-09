package com.project.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.project.ViewFactory;
import com.project.Enums.AccountsOrederType;
import com.project.Enums.Errors;
import com.project.Events.NameChangedPublisher;
import com.project.Model.User;
import com.project.Utils.ExportEntity;

import javafx.collections.ObservableList;

/**
 * UsersDatabse
 */
public class UsersDatabse {
    public static User currentUser;

    public static Errors getAll(List<ExportEntity> list, AccountsOrederType ord) {
        if (!UsersDatabse.currentUser.isAdmin())
            return Errors.PERMISSION_DENIED;
        list.clear();
        // if (currentUser.getId() != 1)
        // return Errors.UNAUTHERAIZE;
        String sql = "SELECT * FROM User ORDER BY " + ord.getSqlName() + ";";
        try (
                Connection conn = Database.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);) {
            while (rs.next()) {
                User user = new User(rs.getInt("id"), rs.getString("user_name"), rs.getString("first_name"),
                        rs.getString("last_name"));
                list.add(user);
            }
            return Errors.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return Errors.GLOBAL_ERROR;
        }
    }

    public static Errors login(String username, String password) {
        String sql = "SELECT * FROM User WHERE user_name=?;";

        try (
                Connection conn = Database.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            System.out.println(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                if (!rs.getString("password").equals(password)) {
                    return Errors.PASSWORD_WRONG;
                }
                currentUser = new User(rs.getInt("id"), username, rs.getString("first_name"),
                        rs.getString("last_name"));
                NameChangedPublisher.instance.fireEvent();
                if (currentUser.getId() == 1) {
                    ViewFactory.instance.showAdminView();
                } else {
                    ViewFactory.instance.showUserView();
                }
                return Errors.SUCCESS;
            }
            return Errors.USER_NOT_FOUND;
        } catch (SQLException e) {
            return Errors.GLOBAL_ERROR;
        }
    }

    public static Errors getAll(ObservableList<User> list, AccountsOrederType ord) {
        if (!UsersDatabse.currentUser.isAdmin())
            return Errors.PERMISSION_DENIED;
        list.clear();
        // if (currentUser.getId() != 1)
        // return Errors.UNAUTHERAIZE;
        String sql = "SELECT * FROM User ORDER BY " + ord.getSqlName() + ";";
        try (
                Connection conn = Database.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);) {
            while (rs.next()) {
                User user = new User(rs.getInt("id"), rs.getString("user_name"), rs.getString("first_name"),
                        rs.getString("last_name"));
                list.add(user);
            }
            return Errors.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return Errors.GLOBAL_ERROR;
        }
    }

    public static Errors changePassword(String password) {
        String sql = "UPDATE User SET password=(?) WHERE id=(?)";

        try (
                Connection conn = Database.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, password);
            pstmt.setInt(2, currentUser.getId());
            if (pstmt.executeUpdate() > 0) {
                return Errors.SUCCESS;
            }
            return Errors.GLOBAL_ERROR;

        } catch (SQLException e) {
            e.printStackTrace();
            return Errors.GLOBAL_ERROR;
        }
    }

    public static Errors changeUsername(String username) {
        String sql = "UPDATE User SET user_name=(?) WHERE id=(?)";

        try (
                Connection conn = Database.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, username);
            pstmt.setInt(2, currentUser.getId());
            if (pstmt.executeUpdate() > 0) {
                currentUser.setUserName(username);
                return Errors.SUCCESS;
            }
            return Errors.GLOBAL_ERROR;

        } catch (SQLException e) {
            return Errors.GLOBAL_ERROR;
        }
    }

    public static Errors changeFirstname(String firstname) {
        if (currentUser.getId() != 1)
            return Errors.UNAUTHERAIZE;
        String sql = "UPDATE User SET first_name=(?) WHERE id=(?)";

        try (
                Connection conn = Database.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, firstname);
            pstmt.setInt(2, currentUser.getId());
            if (pstmt.executeUpdate() > 0) {
                return Errors.SUCCESS;
            }
            return Errors.GLOBAL_ERROR;

        } catch (SQLException e) {
            return Errors.GLOBAL_ERROR;
        }
    }

    public static Errors changeLastname(String lastname) {
        if (currentUser.getId() != 1)
            return Errors.UNAUTHERAIZE;
        String sql = "UPDATE User SET last_name=(?) WHERE id=(?)";

        try (
                Connection conn = Database.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, lastname);
            pstmt.setInt(2, currentUser.getId());
            if (pstmt.executeUpdate() > 0) {
                return Errors.SUCCESS;
            }
            return Errors.GLOBAL_ERROR;

        } catch (SQLException e) {
            return Errors.GLOBAL_ERROR;
        }
    }

    public static Errors add_user(String username, String firstN, String lastN, String password) {
        if (!UsersDatabse.currentUser.isAdmin())
            return Errors.PERMISSION_DENIED;
        String sql = "INSERT INTO User(user_name,first_name,last_name,password) VALUES (?,?,?,?)";
        try (
                Connection conn = Database.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, username);
            pstmt.setString(2, firstN);
            pstmt.setString(3, lastN);
            pstmt.setString(4, password);
            if (pstmt.executeUpdate() > 0) {
                return Errors.SUCCESS;
            }
            return Errors.GLOBAL_ERROR;

        } catch (SQLException e) {
            return Errors.GLOBAL_ERROR;
        }
    }

    public static Errors add_user(String username, String firstN, String lastN) {
        if (!UsersDatabse.currentUser.isAdmin())
            return Errors.PERMISSION_DENIED;
        String sql = "INSERT INTO User(user_name,first_name,last_name) VALUES (?,?,?)";
        try (
                Connection conn = Database.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, username);
            pstmt.setString(2, firstN);
            pstmt.setString(3, lastN);
            if (pstmt.executeUpdate() > 0) {
                return Errors.SUCCESS;
            }
            return Errors.GLOBAL_ERROR;

        } catch (SQLException e) {
            return Errors.GLOBAL_ERROR;
        }
    }

    public static Errors delete_user(int id) {
        if (currentUser.getId() != 1)
            return Errors.PERMISSION_DENIED;
        if (id == 1)
            return Errors.CAN_NOT_DELETE_ADMIN;

        String sql = "DELETE FROM User WHERE id=(?)";
        try (
                Connection conn = Database.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, id);
            if (pstmt.executeUpdate() > 0) {
                return Errors.SUCCESS;
            }
            return Errors.GLOBAL_ERROR;

        } catch (SQLException e) {
            return Errors.GLOBAL_ERROR;
        }
    }

    public static void logout() {
        currentUser = null;
        ViewFactory.instance.showLoginView();
    }
}
