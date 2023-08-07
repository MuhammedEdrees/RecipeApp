package com.example.recipeapp.main.repo

import com.example.recipeapp.main.model.Meal
import com.example.recipeapp.main.model.MealResponse

interface MealsRepository {
    suspend fun getMealsResponseByFirstLetter(letter: Char): MealResponse
    suspend fun getRandomMeal(): MealResponse
    suspend fun search(name: String): MealResponse
    suspend fun insertMeal(meal: Meal)
    suspend fun deleteMeal(meal: Meal)
    suspend fun getLocalMealById(mealID: String): Meal
    suspend fun getRemoteMealById(mealId: String): Meal
    suspend fun getFavoriteMeals(list: List<String>): List<Meal>
    suspend fun checkIfFavorite(mealId: String): Boolean

    suspend fun deleteMealById(mealID: String)
}