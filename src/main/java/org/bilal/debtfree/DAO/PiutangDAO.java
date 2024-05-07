package org.bilal.debtfree.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bilal.debtfree.Model.Piutang;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.bilal.debtfree.Utility.JDBCConnection.getConnection;

public class PiutangDAO implements daoInterface<Piutang>{

    public ObservableList<Piutang> observablePiutangList = FXCollections.observableArrayList();

    @Override
    public boolean addData(Piutang data) {
        boolean success= true;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO piutang (user_id, jumlah_piutang, penerima_piutang) VALUES (?, ?, ?)")) {
            stmt.setInt(1, data.getUser_id());
            stmt.setInt(2, data.getJumlah_piutang());
            stmt.setString(3, data.getPenerima_piutang());
            System.out.println(stmt.toString());
            stmt.execute();
            observablePiutangList.add(data);

        } catch (SQLException e) {
            success = false;
            System.out.println(e.getMessage());
        }
        return success;
    }

    @Override
    public int deleteData(Piutang data) {
        int rowsAffected = 0;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM piutang WHERE piutang_id = ?")) {
            stmt.setInt(1, data.getPiutang_id());
            rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                observablePiutangList.remove(data);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowsAffected;
    }

    @Override
    public int updateData(Piutang data) {
        return 0;
    }

    @Override
    public List<Piutang> showData(int user_id) {
        observablePiutangList.clear();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM piutang WHERE user_id = ?")) {

            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Piutang piutang = new Piutang();
                piutang.setPiutang_id(rs.getInt("piutang_id"));
                piutang.setJumlah_piutang(rs.getInt("jumlah_piutang"));
                piutang.setPenerima_piutang(rs.getString("penerima_piutang"));
                Timestamp timestamp = rs.getTimestamp("piutang_date");
                LocalDateTime localDateTime = null;
                if (timestamp != null) {
                    localDateTime = timestamp.toLocalDateTime();
                }
                piutang.setPiutang_date(localDateTime);
                observablePiutangList.add(piutang);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return observablePiutangList;
    }
}
