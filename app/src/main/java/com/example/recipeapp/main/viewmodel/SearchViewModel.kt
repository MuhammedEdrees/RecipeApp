package com.example.recipeapp.main.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.main.repo.FavoriteRepository
import com.example.recipeapp.main.repo.MealsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(mealRepo: MealsRepository,
                                          favoriteRepo: FavoriteRepository) : RecipeViewModel(mealRepo, favoriteRepo) {
    fun searchMeals(query: String) {
        viewModelScope.launch {
            val response = mealRepo.search(query)
            _listOfMeals.value = mealRepo.search(query).meals?: emptyList()
        }
    }

}
