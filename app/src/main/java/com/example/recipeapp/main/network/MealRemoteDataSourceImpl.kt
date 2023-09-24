package com.example.recipeapp.main.network

import com.example.recipeapp.main.model.MealResponse
import retrofit2.Retrofit
import javax.inject.Inject

class MealRemoteDataSourceImpl @Inject constructor(private val retrofit: Retrofit) : MealRemoteDataSource {

    override suspend fun getMealsResponseByFirstLetter(char: Char): MealResponse {
        return retrofit.create(APIService::class.java)
            .getMealsResponseByFirstLetter(char)
    }

    override suspend fun getRandomMeal(): MealResponse {
        return retrofit.create(APIService::class.java).getRandomMeal()
    }

    override suspend fun search(name: String): MealResponse {
        return retrofit.create(APIService::class.java).search(name)
    }

    override suspend fun getMealById(mealId: String): MealResponse {
        return retrofit.create(APIService::class.java).getMealById(mealId)
    }
}