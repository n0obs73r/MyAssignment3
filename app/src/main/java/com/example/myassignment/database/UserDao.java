package com.example.myassignment.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {

    @Insert
    void registerUser(UserEntity userEntity);

    @Query("SELECT * FROM users WHERE email=(:email) and password=(:password)")
    UserEntity login(String email, String password);


}