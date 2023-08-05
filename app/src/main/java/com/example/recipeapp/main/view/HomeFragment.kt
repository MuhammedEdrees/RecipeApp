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
import com.example.recipeapp.main.viewmodel.RecipeViewModel


class HomeFragment : Fragment() {

    lateinit var mealVModel:RecipeViewModel
    lateinit var rv :RecyclerView
    lateinit var img:ImageView
    lateinit var meal:TextView
    lateinit var catg:TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })
        return view
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_home, container, false)
        val rv =view.findViewById<RecyclerView>(R.id.recyclerView)
        img=view.findViewById(R.id.imageView2)
        meal=view.findViewById(R.id.txt_meal)
        catg=view.findViewById(R.id.txt_catg)


        mealVModel = ViewModelProvider(this).get(RecipeViewModel::class.java)
        mealVModel.getListOfMeals()
        mealVModel.listOfMeals.observe(viewLifecycleOwner) { meals
            ->

            val adapter = MealAdapter(mealVModel)
            rv.adapter = adapter
            adapter.setData(meals)
            rv.layoutManager =
                LinearLayoutManager(this.requireContext(), RecyclerView.HORIZONTAL, false)
        }
        mealVModel.getRandomMeal()
        mealVModel.listOfMeals.observe(viewLifecycleOwner){ meals ->
            meal.text=meals[1].strMeal
            catg.text=meals[1].strCategory
            Glide.with(this)
                .load(meals[1].strMealThumb)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading2)
                )
                .into(img)
        }
        return view
    }


}