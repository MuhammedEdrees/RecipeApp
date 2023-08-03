package com.example.recipeapp.main.repo

import com.example.recipeapp.main.model.MealResponse
import com.example.recipeapp.main.network.RemoteDataSource

class MealsRepositoryImpl(val remoteDataSource: RemoteDataSource): MealsRepository {
    override suspend fun getMealsResponseByFirstLetter(letter: Char): MealResponse {
        return remoteDataSource.getMealsResponseByFirstLetter(letter)
    }

    override suspend fun getRandomMeal(): MealResponse {
        return remoteDataSource.getRandomMeal()
    }

    override suspend fun search(name: String): MealResponse {
        return remoteDataSource.search(name)
    }
}