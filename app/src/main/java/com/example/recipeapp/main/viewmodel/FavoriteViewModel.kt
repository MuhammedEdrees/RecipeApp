package com.example.recipeapp.main.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.main.repo.FavoriteRepository
import com.example.recipeapp.main.repo.MealsRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(mealRepo: MealsRepository,
                        favoriteRepo: FavoriteRepository
) : RecipeViewModel(mealRepo, favoriteRepo) {
fun getLocalFavoriteMeals(userId: Int) {
    viewModelScope.launch {
        _listOfMeals.value = mealRepo.getFavoriteMeals(userId)
        Log.d("edrees -->", "${_listOfMeals.value}")
    }
}
}