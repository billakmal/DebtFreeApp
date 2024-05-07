package org.bilal.debtfree.Model;

import java.time.LocalDateTime;

public class Pendapatan {
    private int pendapatan_id;
    private int user_id;
    private int jumlah_pendapatan;
    private String sumber_pendapatan;
    private LocalDateTime pendapatan_date;


    public Pendapatan(int pendapatan_id, int user_id, int jumlah_pendapatan, String sumber_pendapatan, LocalDateTime pendapatan_date) {
        this.pendapatan_id = pendapatan_id;
        this.user_id = user_id;
        this.jumlah_pendapatan = jumlah_pendapatan;
        this.sumber_pendapatan = sumber_pendapatan;
        this.pendapatan_date = pendapatan_date;
    }

    public Pendapatan(int user_id, int jumlah_pendapatan, String sumber_pendapatan, Object o) {
        this.user_id = user_id;
        this.jumlah_pendapatan = jumlah_pendapatan;
        this.sumber_pendapatan = sumber_pendapatan;
    }

    public Pendapatan() {

    }


    @Override
    public String toString() {
        return "Pendapatan{" +
                "pendapatan_id=" + pendapatan_id +
                ", user_id=" + user_id +
                ", jumlah_pendapatan=" + jumlah_pendapatan +
                ", sumber_pendapatan='" + sumber_pendapatan + '\'' +
                ", pendapatan_date=" + pendapatan_date +
                '}';
    }

    public int getPendapatan_id() {
        return pendapatan_id;
    }

    public void setPendapatan_id(int pendapatan_id) {
        this.pendapatan_id = pendapatan_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getJumlah_pendapatan() {
        return jumlah_pendapatan;
    }

    public void setJumlah_pendapatan(int jumlah_pendapatan) {
        this.jumlah_pendapatan = jumlah_pendapatan;
    }

    public String getSumber_pendapatan() {
        return sumber_pendapatan;
    }

    public void setSumber_pendapatan(String sumber_pendapatan) {
        this.sumber_pendapatan = sumber_pendapatan;
    }

    public LocalDateTime getPendapatan_date() {
        return pendapatan_date;
    }

    public void setPendapatan_date(LocalDateTime pendapatan_date) {
        this.pendapatan_date = pendapatan_date;
    }
}
