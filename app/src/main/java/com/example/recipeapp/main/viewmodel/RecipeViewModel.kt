package com.example.recipeapp.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.main.model.Favorite
import com.example.recipeapp.main.model.Meal
import com.example.recipeapp.main.model.MealResponse
import com.example.recipeapp.main.network.APIClient
import com.example.recipeapp.main.repo.FavoriteRepository
import com.example.recipeapp.main.repo.MealsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class RecipeViewModel(protected val mealRepo: MealsRepository,
                           protected val favoriteRepo: FavoriteRepository
) : ViewModel() {
    protected val _listOfMeals = MutableLiveData<List<Meal>>()
    protected val _RandomMeal = MutableLiveData<Meal>()
    protected val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite : LiveData<Boolean> = _isFavorite
    val listOfMeals: LiveData<List<Meal>> = _listOfMeals
    val RandomMeal: LiveData<Meal> = _RandomMeal
    protected val _listOfVaorites = MutableLiveData<List<Favorite>>()
    val listOfFavorites: LiveData<List<Favorite>> = _listOfVaorites

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
    fun getUserFavorites(userId: Int){
        viewModelScope.launch {
            _listOfVaorites.value = favoriteRepo.getLocalUserFavorites(userId)
            Log.d("edrees -->", "Favorites: ${_listOfVaorites.value}")
        }
    }


    fun getListOfMeals() {
        viewModelScope.launch {

                val response: MealResponse = APIClient.getMealsResponseByFirstLetter('s')
                _listOfMeals.value = response.meals

        }
    }

    fun getRandomMeal(){
        viewModelScope.launch {

                val response: MealResponse = APIClient.getRandomMeal()
                _RandomMeal.value = response.meals.first()

        }
    }
    fun checkIfFavorite(userId: Int, mealId: String) {
        viewModelScope.launch {
            _isFavorite.value = favoriteRepo.checkIfFavorite(userId, mealId) == 1
            Log.d("edrees -->", "Favorite: ${_isFavorite.value}")
        }
    }
}
