package com.example.recipeapp.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.main.network.APIClient
import com.example.recipeapp.main.repo.FavoriteRepository
import com.example.recipeapp.main.repo.MealsRepository

class RecipeViewModelFactory (val favRepo: FavoriteRepository, val mealRepo: MealsRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            SearchViewModel(mealRepo, favRepo) as T
        } else if(modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            FavoriteViewModel(mealRepo, favRepo) as T
        } else if(modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            DetailsViewModel(mealRepo, favRepo) as T
        } else {
            throw IllegalArgumentException("SearchViewModel is not found!")
        }
    }
}