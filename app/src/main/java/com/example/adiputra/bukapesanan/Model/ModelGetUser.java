package com.example.adiputra.bukapesanan.Model;

/**
 * Created by adiputra on 5/12/2017.
 */

public class ModelGetUser {
    private String status;
    private String message;
    private int user_id;
    private String user_name;
    private String token;
    private String email;
    private String omnikey;



    public int getUser_id() { return user_id; }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOmnikey() {
        return omnikey;
    }

    public void setOmnikey(String omnikey) {
        this.omnikey = omnikey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
