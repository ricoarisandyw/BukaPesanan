package com.example.adiputra.bukapesanan.Model;

import java.util.List;

/**
 * Created by rickReaper on 5/27/2017.
 */

public class ModelProduk {
    private String status;
    private List<Products> products;

    public ModelProduk(String id,String name,String lokasi, int harga){
        this.products.add(new Products(id,name,lokasi,harga));
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }

    public static class Products{
        private String id;
        private String name;
        private String city;
        private int price;

        public Products(String id, String name, String city, int price){
            this.id = id;
            this.city = city;
            this.price = price;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }
}
