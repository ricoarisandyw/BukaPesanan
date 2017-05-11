package com.example.adiputra.bukapesanan;

import java.util.List;

/**
 * Created by adiputra on 5/11/2017.
 */

public class ModelGetCategories {
    public String status;
    public List<Categories> categories;

    public static class Categories{
        private int id;
        private String name;

        @Override
        public String toString() {
            return name;
        }
    }
}
