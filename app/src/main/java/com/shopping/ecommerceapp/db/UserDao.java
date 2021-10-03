package com.shopping.ecommerceapp.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.shopping.ecommerceapp.modules.UserModule;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserModule userModule);

    @Query("DELETE FROM user_data WHERE userId= :id")
    void delete(int id);
}
