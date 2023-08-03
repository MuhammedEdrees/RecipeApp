package com.example.recipeapp.main.local

import com.example.recipeapp.main.model.Meal

interface MealLocalSource {

    suspend fun insertMeal(meal: Meal)

    suspend fun deleteMeal(meal: Meal)

    suspend fun getMealById(mealID: String): Meal
}