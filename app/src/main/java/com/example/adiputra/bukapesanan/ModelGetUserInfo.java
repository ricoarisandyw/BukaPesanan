package com.example.adiputra.bukapesanan;

/**
 * Created by adiputra on 5/12/2017.
 */

public class ModelGetUserInfo {
    private String status;
    private User user;

    public class User{
        private String name;
        private String email;
        private String phone;
        private String avatar;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String toString() {
            return getAvatar();
        }
    }
}
