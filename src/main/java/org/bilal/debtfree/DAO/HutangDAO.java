package org.bilal.debtfree.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

import org.bilal.debtfree.Model.Hutang;

import static org.bilal.debtfree.Utility.JDBCConnection.getConnection;

public class HutangDAO implements daoInterface<Hutang>{

    public ObservableList<Hutang> observableHutangList = FXCollections.observableArrayList();

    @Override
    public boolean addData(Hutang data) {
        boolean success= true;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO hutang (user_id, jumlah_hutang, pemberi_hutang) VALUES (?, ?, ?)")) {
            stmt.setInt(1, data.getUser_id());
            stmt.setInt(2, data.getJumlah_hutang());
            stmt.setString(3, data.getPemberi_hutang());
            System.out.println(stmt.toString());
            stmt.execute();
            observableHutangList.add(data);

        } catch (SQLException e) {
            success = false;
            System.out.println(e.getMessage());
        }
        return success;
    }

    @Override
    public int deleteData(Hutang data) {
        int rowsAffected = 0;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM hutang WHERE hutang_id = ?")) {
            stmt.setInt(1, data.getHutang_id());
            rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                observableHutangList.remove(data);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowsAffected;
    }

    @Override
    public int updateData(Hutang data) {
        int rowsAffected = 0;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE hutang SET jumlah_hutang = ?, pemberi_hutang = ? WHERE hutang_id = ?")) {
            stmt.setInt(1, data.getJumlah_hutang());
            stmt.setString(2, data.getPemberi_hutang());
            stmt.setInt(3, data.getHutang_id());
            rowsAffected = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public List<Hutang> showData(int user_id) {
        observableHutangList.clear();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM hutang WHERE user_id = ?")) {

            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Hutang hutang = new Hutang();
                hutang.setHutang_id(rs.getInt("hutang_id"));
                hutang.setJumlah_hutang(rs.getInt("jumlah_hutang"));
                hutang.setPemberi_hutang(rs.getString("pemberi_hutang"));
                Timestamp timestamp = rs.getTimestamp("hutang_date");
                LocalDateTime localDateTime = null;
                if (timestamp != null) {
                    localDateTime = timestamp.toLocalDateTime();
                }
                hutang.setHutang_date(localDateTime);
                observableHutangList.add(hutang);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return observableHutangList;
    }

}
