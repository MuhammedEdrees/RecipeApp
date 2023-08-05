package com.example.recipeapp.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.main.model.Meal
import com.example.recipeapp.main.repo.FavoriteRepository
import com.example.recipeapp.main.repo.MealsRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(mealRepo: MealsRepository,
                        favoriteRepo: FavoriteRepository
) : RecipeViewModel(mealRepo, favoriteRepo) {
    private val _localMeal = MutableLiveData<Meal>()
    val localMeal: LiveData<Meal> = _localMeal
    fun getLocalFavoriteMeals(userId: Int) {
        viewModelScope.launch {
            Log.d("edrees -->", "Function Called")
            _listOfMeals.value = mealRepo.getFavoriteMeals(userId)
            Log.d("edrees -->", "${_listOfMeals.value}")
        }
    }
    fun getLocalMealById(mealId: String) {
        viewModelScope.launch {
            Log.d("edrees -->", "Function Called")
            _localMeal.postValue(mealRepo.getMealById(mealId))
            Log.d("edrees -->", "${_localMeal.value}")
        }
    }
}