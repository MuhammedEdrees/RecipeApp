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

open class RecipeViewModel(protected val mealRepo: MealsRepository,
                           protected val mealClient: APIClient,
                           protected val favoriteRepo: FavoriteRepository
) : ViewModel() {
    protected val _listOfMeals = MutableLiveData<List<Meal>>()
    val listOfMeals: LiveData<List<Meal>> = _listOfMeals

    fun addFavorite(item: Favorite) {
        viewModelScope.launch {
            favoriteRepo.insertLocalFavorite(item)
        }
    }

    fun deleteFavorite(item: Favorite) {
        viewModelScope.launch {
            favoriteRepo.deleteLocalFavorite(item)
        }
    }

    fun checkIfFavoite(userId: Int, mealId: String): MutableLiveData<Boolean> {
        val res = MutableLiveData<Boolean>()
        viewModelScope.launch {
            res.value = favoriteRepo.checkIfFavorite(userId, mealId) == 1
        }
        return res
    }
}
