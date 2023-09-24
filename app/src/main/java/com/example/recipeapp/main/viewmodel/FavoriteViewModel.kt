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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(private val mealRepo: MealsRepository,
                                           private val favoriteRepo: FavoriteRepository
) : ViewModel() {
    protected val _listOfMeals = MutableLiveData<List<Meal>>()
    val listOfMeals: LiveData<List<Meal>> = _listOfMeals
    protected val _listOfFavorites = MutableLiveData<List<Favorite>>()
    val listOfFavorites: LiveData<List<Favorite>> = _listOfFavorites
    protected val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite : LiveData<Boolean> = _isFavorite

    fun addFavorite(fav: Favorite, meal: Meal) {
        viewModelScope.launch {
            favoriteRepo.insertLocalFavorite(fav)
            mealRepo.insertMeal(meal)
        }
    }

    fun getUserFavorites(userId: Int){
        viewModelScope.launch {
            _listOfFavorites.value = favoriteRepo.getLocalUserFavorites(userId)
        }
    }
    fun getLocalFavoriteMeals(list: List<String>) {
        viewModelScope.launch {
            _listOfMeals.value = mealRepo.getFavoriteMeals(list)
        }
    }

    fun deleteFavorite(item: Favorite) {
        viewModelScope.launch {
            favoriteRepo.deleteLocalFavorite(item)
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