package com.example.recipeapp.main.local

import android.content.Context
import com.example.recipeapp.db.RecipeDatabase
import com.example.recipeapp.main.model.Favorite

class FavoriteLocalSourceImpl(private val context: Context): FavoriteLocalSource {
    private val dao = RecipeDatabase.getInstance(context).favoriteDao()
    override suspend fun insertFavorite(favorite: Favorite) {
        dao.insertFavorite(favorite)
    }

    override suspend fun checkIfFavorite(userID: Int, mealID: String): Int {
        return dao.checkIfFavorite(userID, mealID)
    }

    override suspend fun getUserFavorites(userID: Int): List<Favorite> {
        return dao.getUserFavorites(userID)
    }

    override suspend fun deleteFavorite(favorite: Favorite) {
        dao.deleteFavorite(favorite)
    }
}