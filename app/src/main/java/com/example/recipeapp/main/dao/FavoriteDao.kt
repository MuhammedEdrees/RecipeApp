package com.example.recipeapp.main.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.recipeapp.main.model.Favorite

@Dao
interface FavoriteDao {
    @Insert
    suspend fun insertFavorite(vararg favorite: Favorite)

    @Query("select mealID from favorites where userID = :userID")
    suspend fun getUserFavorites(userID: Int): List<Favorite>

    @Query("select exists(select * from favorites where userID = :userID and mealID = :mealID)")
    suspend fun checkIfFavorite(userID: Int, mealID: String): Int

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)
}