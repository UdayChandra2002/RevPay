package com.revpay.dao;

import com.revpay.config.DBConnection;
import com.revpay.model.SecurityQuestion;

import java.sql.*;

public class SecurityQuestionDAO {

    public void saveQuestion(SecurityQuestion sq) throws SQLException {
        String sql = """
            INSERT INTO security_questions (
                sq_id, user_id, question, answer_hash
            )
            VALUES (sq_seq.NEXTVAL, ?, ?, ?)
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, sq.getUserId());
            ps.setString(2, sq.getQuestion());
            ps.setString(3, sq.getAnswerHash());
            ps.executeUpdate();
        }
    }

    /* ✅ REQUIRED FOR FORGOT PASSWORD */
    public SecurityQuestion getByUserId(int userId) throws SQLException {
        String sql = """
            SELECT * FROM security_questions
            WHERE user_id = ?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                SecurityQuestion sq = new SecurityQuestion();
                sq.setSqId(rs.getInt("sq_id"));
                sq.setUserId(rs.getInt("user_id"));
                sq.setQuestion(rs.getString("question"));
                sq.setAnswerHash(rs.getString("answer_hash"));
                return sq;
            }
        }
        return null;
    }
}
