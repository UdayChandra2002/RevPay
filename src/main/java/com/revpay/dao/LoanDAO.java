package com.revpay.dao;

import com.revpay.config.DBConnection;
import com.revpay.model.Loan;

import java.sql.*;

public class LoanDAO {

    public void applyLoan(Loan loan) throws SQLException {
        String sql = """
            INSERT INTO loans (
                loan_id, business_id, amount,
                interest_rate, tenure_months,
                purpose, status
            )
            VALUES (loan_seq.NEXTVAL, ?, ?, ?, ?, ?, 'APPLIED')
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, loan.getBusinessId());
            ps.setDouble(2, loan.getAmount());
            ps.setDouble(3, loan.getInterestRate());
            ps.setInt(4, loan.getTenureMonths());
            ps.setString(5, loan.getPurpose());
            ps.executeUpdate();
        }
    }

    public ResultSet getLoansByBusiness(int businessId) throws SQLException {

        String sql = """
        SELECT loan_id, amount, interest_rate,
               tenure_months, purpose, status, created_at
        FROM loans
        WHERE business_id = ?
        ORDER BY created_at DESC
    """;

        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, businessId);

        return ps.executeQuery();
    }




}
