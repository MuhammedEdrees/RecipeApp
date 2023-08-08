package com.example.recipeapp.main.view

import com.example.recipeapp.main.model.Favorite
import com.example.recipeapp.main.model.Meal

interface MealCallback {
    fun addFavoriteCallback(favorite: Favorite, meal: Meal)
    fun deleteFavoriteCallback(favorite: Favorite)
    fun navigateToDetailsCallback(meal: Meal)
}