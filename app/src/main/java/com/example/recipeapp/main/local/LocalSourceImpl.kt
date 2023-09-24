package com.example.recipeapp.main.local

import android.content.Context
import android.util.Log
import com.example.recipeapp.db.RecipeDatabase
import com.example.recipeapp.main.model.Favorite
import com.example.recipeapp.main.model.Meal
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocalSourceImpl @Inject constructor (@ApplicationContext private val context: Context): LocalSource {
    private val mealDao = RecipeDatabase.getInstance(context).mealDao()
    private val favoriteDao = RecipeDatabase.getInstance(context).favoriteDao()
    override suspend fun insertFavorite(favorite: Favorite) {
        favoriteDao.insertFavorite(favorite)
    }

    override suspend fun checkIfFavorite(userID: Int, mealID: String): Int {
        val res = favoriteDao.checkIfFavorite(userID, mealID)
        return res
    }

    override suspend fun getUserFavorites(userID: Int): List<Favorite> {
        val res = favoriteDao.getUserFavorites(userID)
        return res
    }

    override suspend fun deleteFavorite(favorite: Favorite) {
        favoriteDao.deleteFavorite(favorite)
    }
    override suspend fun insertMeal(meal: Meal) {
        mealDao.insertMeal(meal)
    }

    override suspend fun getMealById(mealID: String): Meal {
        return mealDao.getMealById(mealID)
    }

    override suspend fun deleteMeal(meal: Meal) {
        mealDao.deleteMeal(meal)
    }

    override suspend fun getFavoriteMeals(list: List<String>): List<Meal> {
        val res = mealDao.getFavoriteMeals(list)
        return res
    }

    override suspend fun checkIfFavorite(mealId: String): Boolean {
        return mealDao.checkIfFavorite(mealId) == 1
    }

    override suspend fun deleteMealById(mealID: String) {
        mealDao.deleteMealById(mealID)
    }
}