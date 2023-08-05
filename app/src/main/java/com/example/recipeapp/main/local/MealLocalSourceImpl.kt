package com.example.recipeapp.main.local

import android.content.Context
import android.util.Log
import com.example.recipeapp.db.RecipeDatabase
import com.example.recipeapp.main.model.Meal

class MealLocalSourceImpl(private val context: Context): MealLocalSource {
    private val dao = RecipeDatabase.getInstance(context).mealDao()
    override suspend fun insertMeal(meal: Meal) {
        dao.insertMeal(meal)
    }

    override suspend fun getMealById(mealID: String): Meal {
        return dao.getMealById(mealID)
    }

    override suspend fun deleteMeal(meal: Meal) {
        dao.deleteMeal(meal)
    }

    override suspend fun getFavoriteMeals(userId: Int): List<Meal> {
        val res = dao.getFavoriteMeals(userId)
        Log.d("edrees -->", "$res")
        return res
    }
}