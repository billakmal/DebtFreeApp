package org.bilal.debtfree.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bilal.debtfree.Model.Pengeluaran;

import java.sql.*;
import java.time.LocalDateTime;

import static org.bilal.debtfree.Utility.JDBCConnection.getConnection;

public class PengeluaranDAO implements daoInterface<Pengeluaran>{

    public ObservableList<Pengeluaran> observablePengeluaranList = FXCollections.observableArrayList();

    @Override
    public boolean addData(Pengeluaran data) {
        boolean success = true;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO pengeluaran (user_id, jumlah_pengeluaran, keterangan_pengeluaran) VALUES (?, ?, ?)")) {
            stmt.setInt(1, data.getUser_id());
            stmt.setInt(2, data.getJumlah_pengeluaran());
            stmt.setString(3, data.getKeterangan_pengeluaran());
            System.out.println(stmt.toString());
            stmt.execute();
            observablePengeluaranList.add(data);

        } catch (Exception e) {
            success = false;
            System.out.println(e.getMessage());
        }
        return success;
    }

    @Override
    public int deleteData(Pengeluaran data) {
        int rowsAffected = 0;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM pengeluaran WHERE pengeluaran_id = ?")) {
            stmt.setInt(1, data.getPengeluaran_id());
            rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                observablePengeluaranList.remove(data);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowsAffected;
    }

    @Override
    public int updateData(Pengeluaran data) {
        int rowsAffected = 0;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE pengeluaran SET jumlah_pengeluaran = ?, keterangan_pengeluaran = ? WHERE pengeluaran_id = ?")) {
            stmt.setInt(1, data.getJumlah_pengeluaran());
            stmt.setString(2, data.getKeterangan_pengeluaran());
            stmt.setInt(3, data.getPengeluaran_id());
            rowsAffected = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public ObservableList<Pengeluaran> showData(int user_id) {
        observablePengeluaranList.clear();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM pengeluaran WHERE user_id = ?")) {

            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pengeluaran pengeluaran = new Pengeluaran();
                pengeluaran.setPengeluaran_id(rs.getInt("pengeluaran_id"));
                pengeluaran.setJumlah_pengeluaran(rs.getInt("jumlah_pengeluaran"));
                pengeluaran.setKeterangan_pengeluaran(rs.getString("keterangan_pengeluaran"));
                Timestamp timestamp = rs.getTimestamp("pengeluaran_date");
                LocalDateTime localDateTime = null;
                if (timestamp != null) {
                    localDateTime = timestamp.toLocalDateTime();
                }
                pengeluaran.setPengeluaran_date(localDateTime);
                observablePengeluaranList.add(pengeluaran);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return observablePengeluaranList;
    }
}
