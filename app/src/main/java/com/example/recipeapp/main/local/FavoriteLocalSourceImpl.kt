package com.example.recipeapp.main.local

import android.content.Context
import android.util.Log
import com.example.recipeapp.db.RecipeDatabase
import com.example.recipeapp.main.model.Favorite

class FavoriteLocalSourceImpl(private val context: Context): FavoriteLocalSource {
    private val dao = RecipeDatabase.getInstance(context).favoriteDao()
    override suspend fun insertFavorite(favorite: Favorite) {
        dao.insertFavorite(favorite)
    }

    override suspend fun checkIfFavorite(userID: Int, mealID: String): Int {
        val res = dao.checkIfFavorite(userID, mealID)
        Log.d("edrees -->", "$res")
        return res
    }

    override suspend fun getUserFavorites(userID: Int): List<Favorite> {
        val res = dao.getUserFavorites(userID)
        Log.d("edrees -->", "$res")
        return res
    }

    override suspend fun deleteFavorite(favorite: Favorite) {
        dao.deleteFavorite(favorite)
    }
}