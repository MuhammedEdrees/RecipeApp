package com.example.recipeapp.main.repo

import com.example.recipeapp.main.model.MealResponse

interface MealsRepository {
    suspend fun getMealsResponseByFirstLetter(letter: Char): MealResponse
    suspend fun getRandomMeal(): MealResponse
    suspend fun search(name: String): MealResponse
}