package com.example.recipeapp.db

import androidx.room.Dao
import androidx.room.Insert
import com.example.recipeapp.auth.model.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(vararg users: User)

}