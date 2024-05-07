package org.bilal.debtfree.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

import org.bilal.debtfree.Model.Pendapatan;

import static org.bilal.debtfree.Utility.JDBCConnection.getConnection;

public class PendapatanDAO implements daoInterface<Pendapatan>{

    public ObservableList<Pendapatan> observablePendapatanList = FXCollections.observableArrayList();

    @Override
    public boolean addData(Pendapatan data) {
        boolean success= true;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO pendapatan (user_id, jumlah_pendapatan, sumber_pendapatan) VALUES (?, ?, ?)")) {
            stmt.setInt(1, data.getUser_id());
            stmt.setInt(2, data.getJumlah_pendapatan());
            stmt.setString(3, data.getSumber_pendapatan());
            System.out.println(stmt.toString());
            stmt.execute();
            observablePendapatanList.add(data);

        } catch (SQLException e) {
            success = false;
            System.out.println(e.getMessage());
        }
        return success;
    }

    @Override
    public int deleteData(Pendapatan data) {
        int rowsAffected = 0;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM pendapatan WHERE pendapatan_id = ?")) {
            stmt.setInt(1, data.getPendapatan_id());
            rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                observablePendapatanList.remove(data);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowsAffected;
    }

    @Override
    public int updateData(Pendapatan data) {
        int rowsAffected = 0;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE pendapatan SET jumlah_pendapatan = ?, sumber_pendapatan = ? WHERE pendapatan_id = ?")) {
            stmt.setInt(1, data.getJumlah_pendapatan());
            stmt.setString(2, data.getSumber_pendapatan());
            stmt.setInt(3, data.getPendapatan_id());
            rowsAffected = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public ObservableList<Pendapatan> showData(int user_id) {
        observablePendapatanList.clear();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM pendapatan WHERE user_id = ?")) {

            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pendapatan pendapatan = new Pendapatan();
                pendapatan.setPendapatan_id(rs.getInt("pendapatan_id"));
                pendapatan.setJumlah_pendapatan(rs.getInt("jumlah_pendapatan"));
                pendapatan.setSumber_pendapatan(rs.getString("sumber_pendapatan"));
                Timestamp timestamp = rs.getTimestamp("pendapatan_date");
                LocalDateTime localDateTime = null;
                if (timestamp != null) {
                    localDateTime = timestamp.toLocalDateTime();
                }
                pendapatan.setPendapatan_date(localDateTime);
                observablePendapatanList.add(pendapatan);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return observablePendapatanList;
    }
}
