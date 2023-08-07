package com.example.recipeapp.main.repo

import android.util.Log
import com.example.recipeapp.main.local.MealLocalSource
import com.example.recipeapp.main.model.Meal
import com.example.recipeapp.main.model.MealResponse
import com.example.recipeapp.main.network.MealRemoteDataSource

class MealsRepositoryImpl(val mealRemoteDataSource: MealRemoteDataSource, val mealLocalSource: MealLocalSource): MealsRepository {
    override suspend fun getMealsResponseByFirstLetter(letter: Char): MealResponse {
        return mealRemoteDataSource.getMealsResponseByFirstLetter(letter)
    }

    override suspend fun getRandomMeal(): MealResponse {
        return mealRemoteDataSource.getRandomMeal()
    }

    override suspend fun search(name: String): MealResponse {
        return mealRemoteDataSource.search(name)
    }

    override suspend fun insertMeal(meal: Meal) {
        mealLocalSource.insertMeal(meal)
    }

    override suspend fun deleteMeal(meal: Meal) {
        mealLocalSource.deleteMeal(meal)
    }

    override suspend fun getLocalMealById(mealID: String): Meal {
        return mealLocalSource.getMealById(mealID)
    }

    override suspend fun getRemoteMealById(mealId: String): MealResponse {
        return mealRemoteDataSource.getMealById(mealId)
    }

    override suspend fun getFavoriteMeals(list: List<String>): List<Meal> {
        val res = mealLocalSource.getFavoriteMeals(list)
        return res
    }

    override suspend fun checkIfFavorite(mealId: String): Boolean {
        return mealLocalSource.checkIfFavorite(mealId)
    }

    override suspend fun deleteMealById(mealID: String) {
        mealLocalSource.deleteMealById(mealID)
    }
}