package com.example.recipeapp.main.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipeapp.main.model.Meal
import com.google.android.material.circularreveal.CircularRevealHelper.Strategy

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(vararg meal: Meal)

    @Delete
    suspend fun deleteMeal(meal: Meal)

    @Query("select * from meals where idMeal = :mealID")
    suspend fun getMealById(mealID: String): Meal

    @Query("select * from meals where idMeal in (select idMeal from favorites where userID = :userId)")
    suspend fun getFavoriteMeals(userId: Int): List<Meal>
}