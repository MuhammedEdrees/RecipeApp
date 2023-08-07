package com.example.recipeapp.main.network

import com.example.recipeapp.main.model.Meal
import com.example.recipeapp.main.model.MealResponse

interface MealRemoteDataSource {

    suspend fun getMealsResponseByFirstLetter(char: Char): MealResponse
    suspend fun getRandomMeal(): MealResponse
    suspend fun search(name: String): MealResponse
    suspend fun getMealById(mealId: String): MealResponse
}
