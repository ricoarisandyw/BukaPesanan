package com.example.adiputra.bukapesanan.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adiputra on 5/11/2017.
 */

public class ModelListPesanan {
    @SerializedName("id")
    private int id;
    @SerializedName("nama")
    private String nama;
    @SerializedName("kategori")
    private String kategori;
    @SerializedName("harga")
    private String harga;
    @SerializedName("deskripsi")
    private String deskripsi;
    @SerializedName("gambar")
    private String gambar;
    @SerializedName("lokasi")
    private String lokasi;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("user_id")
    private String user_id;

    public ModelListPesanan(int id, String nama, String kategori, String harga, String deskripsi, String gambar, String lokasi){
        this.id = id;
        this.nama = nama;
        this.kategori = kategori;
        this.harga = harga;
        this.deskripsi = deskripsi;
        this.gambar = gambar;
        this.lokasi = lokasi;
    }

    public ModelListPesanan(String nama, String harga){
        this.nama = nama;
        this.harga = harga;
    }
    public ModelListPesanan(int id, String nama, String harga){
        this.id = id;
        this.nama = nama;
        this.harga = harga;
    }

    public ModelListPesanan(String nama, String kategori, String harga, String deskripsi, String lokasi, String created_at){
        this.nama = nama;
        this.kategori = kategori;
        this.harga = harga;
        this.deskripsi = deskripsi;
        this.lokasi = lokasi;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
