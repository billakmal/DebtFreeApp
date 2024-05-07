package org.bilal.debtfree.Model;

import java.time.LocalDateTime;

public class Piutang {
    private int piutang_id;
    private int user_id;
    private int jumlah_piutang;
    private String penerima_piutang;
    private LocalDateTime piutang_date;

    public Piutang(int piutang_id, int user_id, int jumlah_piutang, String penerima_piutang, LocalDateTime piutang_date) {
        this.piutang_id = piutang_id;
        this.user_id = user_id;
        this.jumlah_piutang = jumlah_piutang;
        this.penerima_piutang = penerima_piutang;
        this.piutang_date = piutang_date;
    }

    public Piutang() {

    }

    public Piutang(int userId, int jumlahPiutang, String penerimaPiutang, Object o) {
        this.user_id = userId;
        this.jumlah_piutang = jumlahPiutang;
        this.penerima_piutang = penerimaPiutang;
    }

    @Override
    public String toString() {
        return "Piutang{" +
                "piutang_id=" + piutang_id +
                ", user_id=" + user_id +
                ", jumlah_piutang=" + jumlah_piutang +
                ", penerima_piutang='" + penerima_piutang + '\'' +
                ", piutang_date=" + piutang_date +
                '}';
    }

    public int getPiutang_id() {
        return piutang_id;
    }

    public void setPiutang_id(int piutang_id) {
        this.piutang_id = piutang_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getJumlah_piutang() {
        return jumlah_piutang;
    }

    public void setJumlah_piutang(int jumlah_piutang) {
        this.jumlah_piutang = jumlah_piutang;
    }

    public String getPenerima_piutang() {
        return penerima_piutang;
    }

    public void setPenerima_piutang(String penerima_piutang) {
        this.penerima_piutang = penerima_piutang;
    }

    public LocalDateTime getPiutang_date() {
        return piutang_date;
    }

    public void setPiutang_date(LocalDateTime piutang_date) {
        this.piutang_date = piutang_date;
    }
}
