package com.example.recipeapp.main.local

import com.example.recipeapp.main.model.Favorite

interface FavoriteLocalSource {
    suspend fun insertFavorite(favorite: Favorite)

    suspend fun getUserFavorites(userID: Int): List<Favorite>

    suspend fun checkIfFavorite(userID: Int, mealID: String): Int

    suspend fun deleteFavorite(favorite: Favorite)
}