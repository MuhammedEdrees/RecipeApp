package com.example.recipeapp.main.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.main.local.FavoriteLocalSourceImpl
import com.example.recipeapp.main.local.MealLocalSourceImpl
import com.example.recipeapp.main.model.Favorite
import com.example.recipeapp.main.model.Meal
import com.example.recipeapp.main.network.APIClient
import com.example.recipeapp.main.repo.FavoriteRepositoryImpl
import com.example.recipeapp.main.repo.MealsRepositoryImpl
import com.example.recipeapp.main.viewmodel.FavoriteViewModel
import com.example.recipeapp.main.viewmodel.RecipeViewModelFactory

class FavoriteFragment : Fragment(), MealCallback {
    private lateinit var recyclerView : RecyclerView
    private lateinit var viewModel: FavoriteViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareViewModel()
        recyclerView = view.findViewById(R.id.favorite_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = FavoriteMealAdapter(this)
        recyclerView.adapter = adapter
        val prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userID = prefs.getInt("user_id", -1)
        viewModel.getUserFavorites(userID)
        viewModel.listOfFavorites.observe(viewLifecycleOwner){favorites ->
            viewModel.getLocalFavoriteMeals(favorites.map{it.mealID})
        }
        viewModel.listOfMeals.observe(viewLifecycleOwner) { favoriteMeals ->
            adapter.setData(favoriteMeals)
        }
    }
    private fun prepareViewModel() {
        val factory = RecipeViewModelFactory(FavoriteRepositoryImpl(FavoriteLocalSourceImpl(requireContext())),
                                        MealsRepositoryImpl(APIClient, MealLocalSourceImpl(requireContext())))
        viewModel = ViewModelProvider(this, factory).get(FavoriteViewModel::class.java)
    }

    override fun addFavoriteCallback(favorite: Favorite, meal: Meal) {
        viewModel.addFavorite(favorite, meal)
    }

    override fun deleteFavoriteCallback(favorite: Favorite) {
        viewModel.deleteFavorite(favorite)
        viewModel.checkIfFavorite(favorite.mealID)
        viewModel.isFavorite.observe(viewLifecycleOwner) {isFavorite ->
            if(isFavorite){
                viewModel.deletMeal(favorite.mealID)
            }
        }
        viewModel.getUserFavorites(favorite.userID)
    }
}