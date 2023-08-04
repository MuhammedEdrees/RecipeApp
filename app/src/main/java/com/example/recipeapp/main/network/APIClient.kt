package com.example.recipeapp.main.network

import com.example.recipeapp.main.model.MealResponse

object APIClient : MealRemoteDataSource {

    override suspend fun getMealsResponseByFirstLetter(char: Char): MealResponse {
        return BaseRetrofitHelper.retrofit.create(APIService::class.java).getMealsResponseByFirstLetter(char)
    }

    override suspend fun getRandomMeal(): MealResponse {
        return BaseRetrofitHelper.retrofit.create(APIService::class.java).getRandomMeal()
    }

    override suspend fun search(name: String): MealResponse {
        return BaseRetrofitHelper.retrofit.create(APIService::class.java).search(name)
    }
}