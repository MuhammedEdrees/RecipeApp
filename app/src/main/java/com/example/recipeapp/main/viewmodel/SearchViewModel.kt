package com.example.recipeapp.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.main.model.Favorite
import com.example.recipeapp.main.model.Meal
import com.example.recipeapp.main.network.APIClient
import com.example.recipeapp.main.repo.FavoriteRepository
import com.example.recipeapp.main.repo.MealsRepository
import kotlinx.coroutines.launch

class SearchViewModel(private val mealRepo: MealsRepository,
                      private val mealClient: APIClient,
                      private val favoriteRepo: FavoriteRepository) : ViewModel(), RecipeViewModel {
    fun getListOfMeals() {
        viewModelScope.launch {
            TODO("implement search")
        }
    }
}