package com.dao;

import com.model.User;
import com.util.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private final Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    /* =========================
       REGISTER USER
       ========================= */
    public boolean register(User user) {

        String sql = "INSERT INTO users(name, email, username, password) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPassword());

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /* =========================
       CHECK EXISTING USER
       ========================= */
    public boolean exists(String username, String email) {

        String sql = "SELECT id FROM users WHERE username=? OR email=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, email);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /* =========================
       GET PASSWORD BY USERNAME
       ========================= */
    public String getPasswordByUsername(String username) {

        String sql = "SELECT password FROM users WHERE username=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("password");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /* =========================
       UPDATE PASSWORD (LOGIN UPGRADE)
       ========================= */
    public void updatePasswordByUsername(String username, String newHash) {

        String sql = "UPDATE users SET password=? WHERE username=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newHash);
            ps.setString(2, username);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* =========================
       UPDATE PASSWORD (GENERAL)
       ========================= */
    public void updatePassword(String username, String newHash) {

        String sql = "UPDATE users SET password=? WHERE username=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newHash);
            ps.setString(2, username);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* =========================
       GET USER ID
       ========================= */
    public int getUserIdByUsername(String username) {

        String sql = "SELECT id FROM users WHERE username=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    public User getUserById(int id) {

        User user = null;

        String sql = "SELECT id, name, email, username FROM users WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }


    /* =========================
       GET ALL USERS
       ========================= */
    public List<User> getAllUsers() {

        List<User> list = new ArrayList<>();
        String sql = "SELECT id, name, email, username FROM users";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setUsername(rs.getString("username"));
                list.add(u);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
