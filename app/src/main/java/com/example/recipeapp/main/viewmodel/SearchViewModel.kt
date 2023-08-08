package com.example.recipeapp.main.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.main.repo.FavoriteRepository
import com.example.recipeapp.main.repo.MealsRepository
import kotlinx.coroutines.launch

class SearchViewModel(mealRepo: MealsRepository,
                      favoriteRepo: FavoriteRepository) : RecipeViewModel(mealRepo, favoriteRepo) {
    fun searchMeals(query: String) {
        viewModelScope.launch {
            val response = mealRepo.search(query)
            _listOfMeals.value = mealRepo.search(query).meals ?: emptyList()
        }
    }
    fun checkIfFavorite(mealId: String){
        viewModelScope.launch{
            _isFavorite.value = mealRepo.checkIfFavorite(mealId)
        }
    }
    fun deletMeal(mealID: String) {
        viewModelScope.launch {
            mealRepo.deleteMealById(mealID)
        }
    }
}