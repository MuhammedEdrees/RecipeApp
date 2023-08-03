package com.example.recipeapp.main.network

import com.example.recipeapp.main.model.MealResponse

interface RemoteDataSource {

    suspend fun getMealsResponseByFirstLetter(char: Char): MealResponse
    suspend fun getRandomMeal(): MealResponse
    suspend fun search(name: String): MealResponse
}
