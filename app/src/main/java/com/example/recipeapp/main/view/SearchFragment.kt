package com.example.recipeapp.main.view

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
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
import com.example.recipeapp.main.viewmodel.RecipeViewModelFactory
import com.example.recipeapp.main.viewmodel.SearchViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SearchFragment : Fragment(), SearchMealCallback {
    lateinit var recyclerView: RecyclerView
    lateinit var searchAdapter: SearchMealAdapter
    lateinit var viewModel: SearchViewModel
    lateinit var searchBar: TextInputEditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareViewModel()
        viewModel.resetSearchResult()
        searchBar = view.findViewById<TextInputLayout>(R.id.search_text_input_layout).editText as TextInputEditText
        recyclerView = view.findViewById(R.id.search_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        searchAdapter = SearchMealAdapter(this)
        recyclerView.adapter = searchAdapter
        val prefs = view.context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userId = prefs.getInt("user_id", -1)
        var isLoaded = false
        viewModel.getUserFavorites(userId)
        viewModel.listOfFavorites.observe(viewLifecycleOwner, Observer {
            isLoaded = true
        })
        viewModel.listOfMeals.observe(viewLifecycleOwner, Observer {meals ->
            if (meals.size > 0) {
                recyclerView.visibility = View.VISIBLE
                view.findViewById<TextView>(R.id.no_matches_found_txt).visibility = View.INVISIBLE
                searchAdapter.setData(meals)
            } else if(!searchBar.text.isNullOrEmpty()){
                recyclerView.visibility = View.INVISIBLE
                view.findViewById<TextView>(R.id.no_matches_found_txt).visibility = View.VISIBLE
            }
        })
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (isLoaded){
                    viewModel.searchMeals(s.toString())
                }
            }
        })
    }
    private fun prepareViewModel() {
        val factory = RecipeViewModelFactory(FavoriteRepositoryImpl(FavoriteLocalSourceImpl(requireContext())),
                                            MealsRepositoryImpl(APIClient, MealLocalSourceImpl(requireContext())))
        viewModel = ViewModelProvider(this, factory).get(SearchViewModel::class.java)
    }

    override fun onPause() {
        super.onPause()
        searchAdapter.setData(emptyList())
        Log.d("edrees -->", "OnPause Called")
    }

    override fun isFavoriteCallback(mealId: String): Boolean {
        return viewModel.listOfFavorites.value?.any {it.mealID == mealId} ?: false
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