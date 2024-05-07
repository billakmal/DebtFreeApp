package org.bilal.debtfree.DAO;

import org.bilal.debtfree.Model.Users;
import org.bilal.debtfree.Utility.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.bilal.debtfree.Utility.JDBCConnection.getConnection;

public class UsersDAO implements daoInterface<Users>{
    @Override
    public boolean addData(Users data) {


        boolean success = false;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (username, email, password) VALUES (?, ?, ?)")) {
            stmt.setString(1, data.getUsername());
            stmt.setString(2, data.getEmail());
            stmt.setString(3, data.getPassword());
            success = stmt.execute();
            success = true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return success;
    }

    public boolean checkLogin(String login, String password) {
        boolean success = false;
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE (username = ? OR email = ?) AND password = ?")) {
            stmt.setString(1, login);
            stmt.setString(2, login);
            stmt.setString(3, password);
            ResultSet rs = stmt.executeQuery();
            success = rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return success;
    }

    @Override
    public int deleteData(Users data) {
        return 0;
    }

    @Override
    public int updateData(Users data) {
        return 0;
    }

    @Override
    public List<Users> showData(int user_id) {
        return List.of();
    }
}
