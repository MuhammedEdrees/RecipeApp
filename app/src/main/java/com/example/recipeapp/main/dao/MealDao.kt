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

    @Query("delete from meals where idMeal = :mealID")
    suspend fun deleteMealById(mealID: String)

    @Query("select * from meals where idMeal = :mealID")
    suspend fun getMealById(mealID: String): Meal

    @Query("select * from meals where idMeal in (:list)")
    suspend fun getFavoriteMeals(list: List<String>): List<Meal>

    @Query("select exists (select * from meals where idMeal = :mealId)")
    suspend fun checkIfFavorite(mealId: String): Int
}