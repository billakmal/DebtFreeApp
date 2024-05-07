package org.bilal.debtfree.Model;

import java.time.LocalDateTime;

public class Pengeluaran {
    private int pengeluaran_id;
    private int user_id;
    private int jumlah_pengeluaran;
    private String keterangan_pengeluaran;
    private LocalDateTime pengeluaran_date;

    public Pengeluaran(int pengeluaran_id, int user_id, int jumlah_pengeluaran, String keterangan_pengeluaran, LocalDateTime pengeluaran_date) {
        this.pengeluaran_id = pengeluaran_id;
        this.user_id = user_id;
        this.jumlah_pengeluaran = jumlah_pengeluaran;
        this.keterangan_pengeluaran = keterangan_pengeluaran;
        this.pengeluaran_date = pengeluaran_date;
    }

    public Pengeluaran(int userId, int jumlah_pengeluaran, String keterangan_pengeluaran, Object o) {
        this.user_id = userId;
        this.jumlah_pengeluaran = jumlah_pengeluaran;
        this.keterangan_pengeluaran = keterangan_pengeluaran;
    }

    public Pengeluaran() {

    }

    @Override
    public String toString() {
        return "Pengeluaran{" +
                "pengeluaran_id=" + pengeluaran_id +
                ", user_id=" + user_id +
                ", jumlah_pengeluaran=" + jumlah_pengeluaran +
                ", keterangan_pengeluaran='" + keterangan_pengeluaran + '\'' +
                ", pengeluaran_date=" + pengeluaran_date +
                '}';
    }

    public int getPengeluaran_id() {
        return pengeluaran_id;
    }

    public void setPengeluaran_id(int pengeluaran_id) {
        this.pengeluaran_id = pengeluaran_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getJumlah_pengeluaran() {
        return jumlah_pengeluaran;
    }

    public void setJumlah_pengeluaran(int jumlah_pengeluaran) {
        this.jumlah_pengeluaran = jumlah_pengeluaran;
    }

    public String getKeterangan_pengeluaran() {
        return keterangan_pengeluaran;
    }

    public void setKeterangan_pengeluaran(String keterangan_pengeluaran) {
        this.keterangan_pengeluaran = keterangan_pengeluaran;
    }

    public LocalDateTime getPengeluaran_date() {
        return pengeluaran_date;
    }

    public void setPengeluaran_date(LocalDateTime pengeluaran_date) {
        this.pengeluaran_date = pengeluaran_date;
    }
}
