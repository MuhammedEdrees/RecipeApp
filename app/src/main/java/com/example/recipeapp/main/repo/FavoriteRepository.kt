package com.example.recipeapp.main.repo

import com.example.recipeapp.main.model.Favorite

interface FavoriteRepository {
    suspend fun insertLocalFavorite(favorite: Favorite)

    suspend fun getLocalUserFavorites(userID: Int): List<Favorite>

    suspend fun checkIfFavorite(userID: Int, mealID: String): Int

    suspend fun deleteLocalFavorite(favorite: Favorite)
}