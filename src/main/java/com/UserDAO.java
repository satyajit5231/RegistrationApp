package com;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    private Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    // ============================
    // CHECK IF USERNAME OR EMAIL EXISTS
    // ============================
    public boolean exists(String username, String email) {
        String sql = "SELECT id FROM users WHERE username = ? OR email = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, email);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
            return true;  // Block registration on DB error
        }
    }

    // ============================
    // REGISTER USER (HASHED PASSWORD)
    // ============================
    public boolean register(User u) {
        String sql = "INSERT INTO users(name, email, username, password) VALUES (?,?,?,?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getUsername());
            ps.setString(4, u.getPassword()); // already hashed
            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ============================
    // GET USER BY USERNAME
    // ============================
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password")); // hashed password
                return u;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ============================
    // GET USER BY ID
    // ============================
    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                return u;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ============================
    // GET PASSWORD BY USER ID
    // ============================
    public String getPasswordById(int id) {
        String sql = "SELECT password FROM users WHERE id=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("password");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ============================
    // UPDATE PASSWORD (HASHED)
    // ============================
    public boolean updatePassword(int id, String hashedPassword) {
        String sql = "UPDATE users SET password=? WHERE id=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hashedPassword);
            ps.setInt(2, id);

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
