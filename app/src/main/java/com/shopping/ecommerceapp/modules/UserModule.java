package com.shopping.ecommerceapp.modules;

public class UserModule {

    int userId;
    String phone, username, email, password;

    public UserModule(String phone, String username, String email, String password) {
        this.phone = phone;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserModule() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
