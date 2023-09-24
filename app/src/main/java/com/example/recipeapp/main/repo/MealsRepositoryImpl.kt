package com.example.recipeapp.main.repo

import com.example.recipeapp.main.local.LocalSource
import com.example.recipeapp.main.model.Meal
import com.example.recipeapp.main.model.MealResponse
import com.example.recipeapp.main.network.MealRemoteDataSource
import javax.inject.Inject

class MealsRepositoryImpl @Inject constructor(private val mealRemoteDataSource: MealRemoteDataSource, private val localSource: LocalSource): MealsRepository {
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
        localSource.insertMeal(meal)
    }

    override suspend fun deleteMeal(meal: Meal) {
        localSource.deleteMeal(meal)
    }

    override suspend fun getLocalMealById(mealID: String): Meal {
        return localSource.getMealById(mealID)
    }

    override suspend fun getRemoteMealById(mealId: String): MealResponse {
        return mealRemoteDataSource.getMealById(mealId)
    }

    override suspend fun getFavoriteMeals(list: List<String>): List<Meal> {
        return localSource.getFavoriteMeals(list)
    }

    override suspend fun checkIfFavorite(mealId: String): Boolean {
        return localSource.checkIfFavorite(mealId)
    }

    override suspend fun deleteMealById(mealID: String) {
        localSource.deleteMealById(mealID)
    }
}