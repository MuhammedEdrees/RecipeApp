package com.example.recipeapp.main.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.recipeapp.main.model.Favorite
import com.example.recipeapp.main.model.Meal
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.recipeapp.R
import com.example.recipeapp.main.local.FavoriteLocalSourceImpl
import com.example.recipeapp.main.local.MealLocalSourceImpl
import com.example.recipeapp.main.network.APIClient
import com.example.recipeapp.main.repo.FavoriteRepositoryImpl
import com.example.recipeapp.main.repo.MealsRepositoryImpl
import com.example.recipeapp.main.viewmodel.RecipeViewModel
import com.example.recipeapp.main.viewmodel.RecipeViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class HomeFragment : Fragment() , SearchMealCallback{

    lateinit var mealVModel:RecipeViewModel
    lateinit var rv :RecyclerView
    lateinit var randomMealCardview: CardView
    lateinit var img:ImageView
    lateinit var meal:TextView
    lateinit var catg:TextView
    lateinit var area:TextView
    lateinit var favBtn:CheckBox

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rv =view.findViewById<RecyclerView>(R.id.recyclerView)
        img=view.findViewById(R.id.meal_thumbnail)
        meal=view.findViewById(R.id.meal_title)
        catg=view.findViewById(R.id.meal_category)
        area=view.findViewById(R.id.meal_area)
        randomMealCardview = view.findViewById(R.id.random_meal_cardview)
        favBtn=view.findViewById(R.id.fav_btn)

        val factory=RecipeViewModelFactory(FavoriteRepositoryImpl(FavoriteLocalSourceImpl(requireContext())),MealsRepositoryImpl(APIClient,MealLocalSourceImpl(requireContext())))
        mealVModel = ViewModelProvider(this,factory).get(RecipeViewModel::class.java)
        mealVModel.getListOfMeals()
        val adapter = HomeMealAdapter(this,viewLifecycleOwner)
        rv.adapter = adapter
        rv.layoutManager =
            LinearLayoutManager(this.requireContext(), RecyclerView.HORIZONTAL, false)
        mealVModel.listOfMeals.observe(viewLifecycleOwner) { meals
            ->
            adapter.setData(meals)
        }
        mealVModel.getRandomMeal()
        mealVModel.RandomMeal.observe(viewLifecycleOwner){ meal ->
            this.meal.text=meal.strMeal
            catg.text=String.format(getString(R.string.category_str), meal.strCategory)
            area.text=String.format(getString(R.string.area_str), meal.strArea)
            Glide.with(this)
                .load(meal.strMealThumb)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading2)
                )
                .into(img)
            randomMealCardview.setOnClickListener{
                navigateToDetailsCallback(meal)
            }

            val prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            val userId = prefs.getInt("user_id", -1)
            favBtn.setOnCheckedChangeListener(null)
            favBtn.isChecked = mealVModel.listOfFavorites.value?.any { it.mealID == meal.idMeal } ?: false
            favBtn.setOnCheckedChangeListener{ buttonView, isChecked ->
                if (isChecked) {
                    mealVModel.addFavorite(Favorite(userId, meal.idMeal), meal)
                    mealVModel.getUserFavorites(userId)
                } else {
                    MaterialAlertDialogBuilder(requireContext()).setTitle("Confirm")
                        .setMessage("Are you sure you want to remove this item from your favorites?")
                        .setPositiveButton("Yes") { dialog, which ->
                            mealVModel.deleteFavorite(Favorite(userId, meal.idMeal))
                        }
                        .setNegativeButton("No") { dialog, which ->
                            buttonView.isChecked = true
                        }.show()
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })
    }

    override fun isFavoriteCallback(mealId: String): Boolean {
        return mealVModel.listOfFavorites.value?.any {it.mealID == mealId} ?: false
    }

    override fun addFavoriteCallback(favorite: Favorite, meal: Meal) {
        mealVModel.addFavorite(favorite, meal)
    }

    override fun deleteFavoriteCallback(favorite: Favorite) {
        val prefs = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userId = prefs.getInt("user_id", -1)
        mealVModel.deleteFavorite(favorite)
        mealVModel.checkIfFavorite(userId,favorite.mealID)
        mealVModel.isFavorite.observe(viewLifecycleOwner) {isFavorite ->
            if(isFavorite){
                mealVModel.deleteMeal(favorite.mealID)
            }
        }
        mealVModel.getUserFavorites(favorite.userID)
    }
    override fun navigateToDetailsCallback(meal: Meal) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(meal)
        findNavController().navigate(action)
    }
}