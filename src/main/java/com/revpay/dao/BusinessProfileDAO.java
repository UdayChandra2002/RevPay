package com.revpay.dao;

import com.revpay.config.DBConnection;
import com.revpay.model.BusinessProfile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BusinessProfileDAO {

    public void saveBusinessProfile(BusinessProfile bp) throws SQLException {

        String sql = """
            INSERT INTO business_profiles (
                business_id,
                business_name,
                business_type,
                tax_id,
                address,
                verified
            )
            VALUES (?, ?, ?, ?, ?, 'N')
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bp.getBusinessId());
            ps.setString(2, bp.getBusinessName());
            ps.setString(3, bp.getBusinessType());
            ps.setString(4, bp.getTaxId());
            ps.setString(5, bp.getAddress());

            ps.executeUpdate();
        }
    }
}
