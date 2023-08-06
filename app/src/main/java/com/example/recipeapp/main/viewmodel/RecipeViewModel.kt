package com.example.recipeapp.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.main.model.Favorite
import com.example.recipeapp.main.model.Meal
import com.example.recipeapp.main.repo.FavoriteRepository
import com.example.recipeapp.main.repo.MealsRepository
import kotlinx.coroutines.launch

open class RecipeViewModel(protected val mealRepo: MealsRepository,
                           protected val favoriteRepo: FavoriteRepository
) : ViewModel() {
    protected val _listOfMeals = MutableLiveData<List<Meal>>()
    protected val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite : LiveData<Boolean> = _isFavorite
    val listOfMeals: LiveData<List<Meal>> = _listOfMeals
    protected val _listOfFavorites = MutableLiveData<List<Favorite>>()
    val listOfFavorites: LiveData<List<Favorite>> = _listOfFavorites

    fun addFavorite(fav: Favorite, meal: Meal) {
        viewModelScope.launch {
            favoriteRepo.insertLocalFavorite(fav)
            mealRepo.insertMeal(meal)
        }
    }

    open fun deleteFavorite(item: Favorite) {
        viewModelScope.launch {
            favoriteRepo.deleteLocalFavorite(item)
        }
    }
    fun getUserFavorites(userId: Int){
        viewModelScope.launch {
            Log.d("edrees -->", "Function Called")
            _listOfFavorites.value = favoriteRepo.getLocalUserFavorites(userId)
            Log.d("edrees -->", "Favorites: ${_listOfFavorites.value}")
        }
    }
    fun checkIfFavorite(userId: Int, mealId: String) {
        viewModelScope.launch {
            _isFavorite.value = favoriteRepo.checkIfFavorite(userId, mealId) == 1
            Log.d("edrees -->", "Favorite: ${_isFavorite.value}")
        }
    }
    fun resetSearchResult(){
        _listOfMeals.value = emptyList()
    }
}
