package com.example.recipeapp.main.repo

import com.example.recipeapp.main.local.FavoriteLocalSource
import com.example.recipeapp.main.model.Favorite

class FavoriteRepositoryImpl (private val localSource: FavoriteLocalSource): FavoriteRepository {

    override suspend fun insertLocalFavorite(favorite: Favorite) {
        localSource.insertFavorite(favorite)
    }

    override suspend fun getLocalUserFavorites(userID: Int): List<Favorite> {
        return localSource.getUserFavorites(userID)
    }

    override suspend fun checkIfFavorite(userID: Int, mealID: String): Int {
        return localSource.checkIfFavorite(userID, mealID)
    }

    override suspend fun deleteLocalFavorite(favorite: Favorite) {
        localSource.deleteFavorite(favorite)
    }
}