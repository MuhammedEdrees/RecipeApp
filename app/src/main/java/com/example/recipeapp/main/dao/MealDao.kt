package com.example.recipeapp.main.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.recipeapp.main.model.Meal

@Dao
interface MealDao {
    @Insert
    suspend fun insertMeal(vararg meal: Meal)

    @Delete
    suspend fun deleteMeal(meal: Meal)

    @Query("select * from meals where idMeal = :mealID")
    suspend fun getMealById(mealID: String): Meal
}