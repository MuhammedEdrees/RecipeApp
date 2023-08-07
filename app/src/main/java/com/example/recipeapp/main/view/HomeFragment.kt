package com.example.recipeapp.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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


class HomeFragment : Fragment() {

    lateinit var mealVModel:RecipeViewModel
    lateinit var rv :RecyclerView
    lateinit var img:ImageView
    lateinit var meal:TextView
    lateinit var catg:TextView
    lateinit var area:TextView


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

        val factory=RecipeViewModelFactory(FavoriteRepositoryImpl(FavoriteLocalSourceImpl(requireContext())),MealsRepositoryImpl(APIClient,MealLocalSourceImpl(requireContext())))
        mealVModel = ViewModelProvider(this,factory).get(RecipeViewModel::class.java)
        mealVModel.getListOfMeals()
        val adapter = MealAdapter(mealVModel,viewLifecycleOwner)
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
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })
    }




}