package com.example.recipeapp.main.view

interface SearchMealCallback: MealCallback {
    fun isFavoriteCallback(mealId: String): Boolean
}