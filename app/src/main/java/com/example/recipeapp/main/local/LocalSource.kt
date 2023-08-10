package com.example.recipeapp.main.local

import com.example.recipeapp.main.model.Favorite
import com.example.recipeapp.main.model.Meal

interface LocalSource {

    suspend fun insertMeal(meal: Meal)

    suspend fun deleteMeal(meal: Meal)

    suspend fun getMealById(mealID: String): Meal

    suspend fun getFavoriteMeals(list: List<String>): List<Meal>

    suspend fun checkIfFavorite(mealId: String): Boolean

    suspend fun deleteMealById(mealID: String)
    suspend fun insertFavorite(favorite: Favorite)

    suspend fun getUserFavorites(userID: Int): List<Favorite>

    suspend fun checkIfFavorite(userID: Int, mealID: String): Int

    suspend fun deleteFavorite(favorite: Favorite)
}