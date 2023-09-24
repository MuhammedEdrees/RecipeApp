package com.example.recipeapp.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.main.model.Meal
import com.example.recipeapp.main.repo.FavoriteRepository
import com.example.recipeapp.main.repo.MealsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel@Inject constructor(mealRepo: MealsRepository,
                       favoriteRepo: FavoriteRepository) : RecipeViewModel(mealRepo, favoriteRepo) {
    private val _meal = MutableLiveData<Meal>()
    val meal: LiveData<Meal> = _meal
    fun getRemoteMeal(mealID: String){
        viewModelScope.launch{
            _meal.value = mealRepo.getRemoteMealById(mealID).meals.first()
        }
    }
}