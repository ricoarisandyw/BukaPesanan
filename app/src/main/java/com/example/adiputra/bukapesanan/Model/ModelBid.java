package com.example.adiputra.bukapesanan.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adiputra on 5/21/2017.
 */

public class ModelBid {
    @SerializedName("id")
    private int id;
    @SerializedName("harga")
    private String harga;
    @SerializedName("deskripsi")
    private String deskripsi;
    @SerializedName("lampiran")
    private String lampiran;
    @SerializedName("lokasi")
    private String lokasi;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("pesanan")
    private String pesanan_id;
    @SerializedName("user_id")
    private String user_id;

    public ModelBid(String harga, String deskripsi, String lampiran, String lokasi, String created_at){
        this.harga = harga;
        this.deskripsi = deskripsi;
        this.lampiran = lampiran;
        this.lokasi = lokasi;
        this.created_at = created_at;
    }

    public ModelBid(int id, String harga){
        this.id = id;
        this.harga = harga;
    }
    public ModelBid(String lokasi, String harga){
        this.lokasi = lokasi;
        this.harga = harga;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getLampiran() {
        return lampiran;
    }

    public void setLampiran(String lampiran) {
        this.lampiran = lampiran;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getPesanan_id() {
        return pesanan_id;
    }

    public void setPesanan_id(String pesanan_id) {
        this.pesanan_id = pesanan_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


}
