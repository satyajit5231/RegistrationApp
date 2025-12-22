package com.dao;

import com.util.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OtpDAO {

    // Save or update OTP (one active OTP per email)
    public void saveOtp(String email, int otp, long createdAt) throws Exception {

        String sql = """
            INSERT INTO otp_store (email, otp, created_at, used)
            VALUES (?, ?, ?, false)
            ON DUPLICATE KEY UPDATE
            otp = VALUES(otp),
            created_at = VALUES(created_at),
            used = false
        """;

        try (Connection conn = DBConnect.getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setInt(2, otp);
            ps.setLong(3, createdAt);
            ps.executeUpdate();
        }
    }

    // Validate OTP with expiry & reuse prevention
    public boolean validateOtp(String email, int enteredOtp, long expiryMillis)
            throws Exception {

        String sql = """
            SELECT otp, created_at, used
            FROM otp_store
            WHERE email = ?
        """;

        try (Connection conn = DBConnect.getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                return false;
            }

            int storedOtp = rs.getInt("otp");
            long createdAt = rs.getLong("created_at");
            boolean used = rs.getBoolean("used");

            long now = System.currentTimeMillis();

            // ❌ OTP already used
            if (used) return false;

            // ❌ OTP expired
            if ((now - createdAt) > expiryMillis) return false;

            // ❌ OTP mismatch
            if (storedOtp != enteredOtp) return false;

            // ✅ OTP valid → mark as used
            markOtpUsed(email);
            return true;
        }
    }

    // Mark OTP as used (prevents reuse)
    public void markOtpUsed(String email) throws Exception {

        String sql = "UPDATE otp_store SET used = true WHERE email = ?";

        try (Connection conn = DBConnect.getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.executeUpdate();
        }
    }

    // Optional cleanup (expired OTPs)
    public void deleteExpiredOtps(long expiryMillis) throws Exception {

        String sql = """
            DELETE FROM otp_store
            WHERE (? - created_at) > ?
        """;

        try (Connection conn = DBConnect.getConn();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, System.currentTimeMillis());
            ps.setLong(2, expiryMillis);
            ps.executeUpdate();
        }
    }
}
