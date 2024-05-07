package org.bilal.debtfree.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Hutang {
    private int hutang_id;
    private int user_id;
    private int jumlah_hutang;
    private String pemberi_hutang;
    private LocalDateTime hutang_date;

    public Hutang(int hutang_id, int user_id, int jumlah_hutang, String pemberi_hutang, LocalDateTime hutang_date) {
        this.hutang_id = hutang_id;
        this.user_id = user_id;
        this.jumlah_hutang = jumlah_hutang;
        this.pemberi_hutang = pemberi_hutang;
        this.hutang_date = hutang_date;
    }

    public Hutang(int user_id, int jumlah_hutang, String pemberi_hutang, Object o) {
        this.user_id = user_id;
        this.jumlah_hutang = jumlah_hutang;
        this.pemberi_hutang = pemberi_hutang;
    }

    public Hutang() {
    }

    @Override
    public String toString() {
        return "Hutang{" +
                "hutang_id=" + hutang_id +
                ", user_id=" + user_id +
                ", jumlah_hutang=" + jumlah_hutang +
                ", pemberi_hutang='" + pemberi_hutang + '\'' +
                ", hutang_date=" + hutang_date +
                '}';
    }

    public int getHutang_id() {
        return hutang_id;
    }

    public void setHutang_id(int hutang_id) {
        this.hutang_id = hutang_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getJumlah_hutang() {
        return jumlah_hutang;
    }

    public void setJumlah_hutang(int jumlah_hutang) {
        this.jumlah_hutang = jumlah_hutang;
    }

    public String getPemberi_hutang() {
        return pemberi_hutang;
    }

    public void setPemberi_hutang(String pemberi_hutang) {
        this.pemberi_hutang = pemberi_hutang;
    }

    public LocalDateTime getHutang_date() {
        return hutang_date;
    }

    public void setHutang_date(LocalDateTime hutang_date) {
        this.hutang_date = hutang_date;
    }
}
