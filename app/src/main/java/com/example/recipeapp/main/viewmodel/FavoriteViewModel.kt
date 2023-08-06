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

class FavoriteViewModel(private val mealRepo: MealsRepository,
                        private val favoriteRepo: FavoriteRepository
) : ViewModel() {
    private val _localMeal = MutableLiveData<Meal>()
    val localMeal: LiveData<Meal> = _localMeal
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

    fun getUserFavorites(userId: Int){
        viewModelScope.launch {
            Log.d("edrees -->", "Function Called")
            _listOfFavorites.value = favoriteRepo.getLocalUserFavorites(userId)
            Log.d("edrees -->", "Favorites: ${_listOfFavorites.value}")
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
            val list = _listOfMeals.value?.toMutableList()
            list?.removeIf { it.idMeal == item.mealID }
            _listOfMeals.value = list?:listOf<Meal>()
        }
    }
}