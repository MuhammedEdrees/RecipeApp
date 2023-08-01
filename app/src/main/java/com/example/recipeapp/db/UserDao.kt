package com.example.recipeapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.recipeapp.auth.model.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(vararg users: User)

    @Query("SELECT EXISTS(SELECT * FROM users WHERE username = :username)")
    suspend fun hasUsername(username: String): Int

    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getUserByUsername(username: String): User

}