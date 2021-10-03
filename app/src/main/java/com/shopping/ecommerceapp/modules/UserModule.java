package com.shopping.ecommerceapp.modules;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_data")
public class UserModule {

    @PrimaryKey(autoGenerate = true)
    int userId;
    String phone, username, email, password, uid;

    @Ignore
    public UserModule(String phone, String username, String email, String password, String uid) {
        this.phone = phone;
        this.username = username;
        this.email = email;
        this.password = password;
        this.uid = uid;
    }

    public UserModule() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
