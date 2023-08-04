package com.example.recipeapp.main.network

import com.example.recipeapp.main.model.Meal
import com.example.recipeapp.main.model.MealResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @GET("search.php?f={letter}")
    suspend fun getMealsResponseByFirstLetter(@Path("letter") letter: Char): MealResponse
    @GET("random.php")
    suspend fun getRandomMeal(): MealResponse
//    @GET("search.php?s={name}")
//    suspend fun search(@Path("name") name: String): MealResponse
    @GET("search.php")
    suspend fun search(@Query("s") search : String ): MealResponse
}