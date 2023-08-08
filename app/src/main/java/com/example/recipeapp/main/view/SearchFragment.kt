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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Guideline
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
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
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SearchFragment : Fragment(), SearchMealCallback {
    lateinit var recyclerView: RecyclerView
    lateinit var searchAdapter: SearchMealAdapter
    lateinit var viewModel: SearchViewModel
    lateinit var searchBar: TextInputEditText
    lateinit var shimmer: ShimmerFrameLayout
    lateinit var lottie: LottieAnimationView
    lateinit var lottieLayout: ConstraintLayout
    lateinit var animationText: TextView
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
        shimmer = view.findViewById(R.id.shimmer_search_layout)
        lottie = view.findViewById(R.id.animation_search)
        lottieLayout = view.findViewById(R.id.lottie_layout)
        animationText = view.findViewById(R.id.animation_text_search)
        recyclerView = view.findViewById(R.id.search_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        searchAdapter = SearchMealAdapter(this)
        recyclerView.adapter = searchAdapter
        setStartSearchAnimationVisible()
        val prefs = view.context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userId = prefs.getInt("user_id", -1)
        var isLoaded = false
        viewModel.getUserFavorites(userId)
        viewModel.listOfFavorites.observe(viewLifecycleOwner, Observer {
            isLoaded = true
        })
        viewModel.listOfMeals.observe(viewLifecycleOwner, Observer {meals ->
            if(searchBar.text.isNullOrBlank()){
                setStartSearchAnimationVisible()
            } else if (meals.size > 0) {
                setRecyclerViewVisible()
                searchAdapter.setData(meals)
            } else {
                setNoMatchesFoundAnimationVisible()
            }
        })
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (isLoaded && !s.toString().isBlank()){
                    setShimmerVisible()
                    viewModel.searchMeals(s.toString())
                } else if (s.toString().isBlank()) {
                    setStartSearchAnimationVisible()
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
    override fun navigateToDetailsCallback(meal: Meal) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(meal)
        findNavController().navigate(action)
    }
    fun setStartSearchAnimationVisible(){
        lottieLayout.visibility = View.VISIBLE
        lottie.setAnimation(R.raw.type_to_search_animation)
        lottie.playAnimation()
        animationText.text = "Type to Search"
        view?.findViewById<Guideline>(R.id.guideline)?.setGuidelinePercent(0.6F)
        recyclerView.visibility = View.GONE
        shimmer.stopShimmer()
        shimmer.visibility = View.GONE
    }
    fun setShimmerVisible(){
        lottieLayout.visibility = View.GONE
        recyclerView.visibility = View.GONE
        shimmer.visibility = View.VISIBLE
        shimmer.startShimmer()
    }
    fun setRecyclerViewVisible(){
        lottieLayout.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        shimmer.stopShimmer()
        shimmer.visibility = View.GONE
    }
    fun setNoMatchesFoundAnimationVisible(){
        lottieLayout.visibility = View.VISIBLE
        lottie.setAnimation(R.raw.no_matches_animation)
        lottie.playAnimation()
        animationText.text = "No Matches Found"
        view?.findViewById<Guideline>(R.id.guideline)?.setGuidelinePercent(0.6F)
        recyclerView.visibility = View.GONE
        shimmer.stopShimmer()
        shimmer.visibility = View.GONE
    }
}