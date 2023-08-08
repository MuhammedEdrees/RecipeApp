package com.example.recipeapp.main.view

import android.content.Context
import android.graphics.Color
import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication3.IngredientRow
import com.example.myapplication3.TableAdapter
import com.example.recipeapp.R
import com.example.recipeapp.main.local.FavoriteLocalSourceImpl
import com.example.recipeapp.main.local.MealLocalSourceImpl
import com.example.recipeapp.main.network.APIClient
import com.example.recipeapp.main.repo.FavoriteRepositoryImpl
import com.example.recipeapp.main.repo.MealsRepositoryImpl
import com.example.recipeapp.main.viewmodel.DetailsViewModel
import com.example.recipeapp.main.viewmodel.RecipeViewModelFactory
import com.example.recipeapp.main.viewmodel.SearchViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class DetailsFragment : Fragment() {

    val args: DetailsFragmentArgs by navArgs()
    lateinit var viewModel: DetailsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity()

        val topAppBarLayout = activity.findViewById<AppBarLayout>(R.id.top_app_bar_layout)
        val bottomNavigationView = activity.findViewById<BottomNavigationView>(R.id.bottom_navigation)

        topAppBarLayout.visibility = View.GONE
        bottomNavigationView.visibility = View.GONE

        prepareViewModel()
        args.meal.also { meal ->

            val favoriteBtn: FloatingActionButton = view.findViewById(R.id.favoriteBtn)

            val collapsingToolbarLayout: CollapsingToolbarLayout =
                view.findViewById(R.id.collapsingToolbarLayout)
            collapsingToolbarLayout.title = meal.strMeal

            val thumbnail: ImageView = view.findViewById(R.id.backdrop_image_view)

            context?.let {
                Glide.with(it)
                    .load(meal.strMealThumb)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.food)
                            .error(R.drawable.bg_v)
                    )
                    .into(thumbnail)
            }

//            var isChecked = false
//            val prefs = view.context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
//            val userId = prefs.getInt("user_id", -1)
//            viewModel.getUserFavorites(userId)
//            for(el in viewModel.listOfFavorites.value!!)
//            {
//                if(el.mealID == meal.idMeal)
//                {
//                    isChecked = true
//                    break
//                }
//            }
//
//            Log.d("bruh->", "$isChecked")
//
//            if(isChecked)
//            {
//                favoriteBtn.setBackgroundColor(Color.BLACK)
//            }


            view.findViewById<TextView>(R.id.categoryContent).text =
                if (meal.strCategory.isNullOrEmpty()) "N/A" else meal.strCategory
            view.findViewById<TextView>(R.id.areaContent).text =
                if (meal.strArea.isNullOrEmpty()) "N/A" else meal.strArea
            view.findViewById<TextView>(R.id.tagsContent).text =
                if (meal.strTags.isNullOrEmpty()) "N/A" else meal.strTags



            view.findViewById<TextView>(R.id.instructionsTextContent).text = meal.strInstructions

            val recyclerView: RecyclerView = view.findViewById(R.id.ingredientsTable)
            recyclerView.layoutManager = LinearLayoutManager(context) // it was this
            // maps each ingredient to its corresponding measure and eliminates any nulls

            val mealsMap: MutableMap<String?, String?> = mutableMapOf<String?, String?>()
            mealsMap[meal.strIngredient1] = meal.strMeasure1
            mealsMap[meal.strIngredient2] = meal.strMeasure2
            mealsMap[meal.strIngredient3] = meal.strMeasure3
            mealsMap[meal.strIngredient4] = meal.strMeasure4
            mealsMap[meal.strIngredient5] = meal.strMeasure5
            mealsMap[meal.strIngredient6] = meal.strMeasure6
            mealsMap[meal.strIngredient7] = meal.strMeasure7
            mealsMap[meal.strIngredient8] = meal.strMeasure8
            mealsMap[meal.strIngredient9] = meal.strMeasure9
            mealsMap[meal.strIngredient10] = meal.strMeasure10
            mealsMap[meal.strIngredient11] = meal.strMeasure11
            mealsMap[meal.strIngredient12] = meal.strMeasure12
            mealsMap[meal.strIngredient13] = meal.strMeasure13
            mealsMap[meal.strIngredient14] = meal.strMeasure14
            mealsMap[meal.strIngredient15] = meal.strMeasure15
            mealsMap[meal.strIngredient16] = meal.strMeasure16
            mealsMap[meal.strIngredient17] = meal.strMeasure17
            mealsMap[meal.strIngredient18] = meal.strMeasure18
            mealsMap[meal.strIngredient19] = meal.strMeasure19
            mealsMap[meal.strIngredient20] = meal.strMeasure20

            val tableData = ArrayList<IngredientRow>()

            for (pair in mealsMap) {
                if (pair.key != null && pair.key != "") {
                    tableData.add(IngredientRow(pair.key, pair.value))
                } else break
            }

            val adapter = TableAdapter(tableData)
            recyclerView.adapter = adapter

            setOnClickListener(
                view,
                R.id.generalInfo,
                R.id.generalInfoContent,
                R.id.generalInfoArrow
            )
            setOnClickListener(
                view,
                R.id.instructions,
                R.id.instructionsContent,
                R.id.instructionsArrow
            )
            setOnClickListener(
                view,
                R.id.ingredients,
                R.id.ingredientsContent,
                R.id.ingredientsArrow
            )
            setOnClickListener(view, R.id.youtube, R.id.youtubeContent, R.id.youtubeArrow)


            val startVideoBtn: MaterialButton = view.findViewById(R.id.startVideoBtn)
            startVideoBtn.setTextColor(Color.BLACK)
            startVideoBtn.setBackgroundColor(Color.RED)

            val youTubePlayerView: YouTubePlayerView = view.findViewById(R.id.youtube_player_view)
            lifecycle.addObserver(youTubePlayerView)
            val videoLayout: ConstraintLayout = view.findViewById(R.id.youtubeVideoLayout)

            youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    val videoId = meal.strYoutube?.lastIndexOf('=')
                        ?.let { meal.strYoutube.substring(it + 1) }
                    Log.d("remo->", "$videoId")
                    youTubePlayer.loadVideo(videoId!!, 0F)
                    youTubePlayer.pause()
                    view.findViewById<Button>(R.id.startVideoBtn).setOnClickListener {
                        videoLayout.visibility = View.VISIBLE
                        youTubePlayer.play()
                    }

                    view.findViewById<ImageView>(R.id.closeVideoBtn).setOnClickListener {
                        videoLayout.visibility = View.GONE
                        youTubePlayer.pause()
                    }

                    requireActivity().onBackPressedDispatcher.addCallback(
                        viewLifecycleOwner,
                        object : OnBackPressedCallback(true) {
                            override fun handleOnBackPressed() {
                                if (videoLayout.visibility == View.VISIBLE) {
                                    videoLayout.visibility = View.GONE
                                    youTubePlayer.pause()
                                } else {
                                    topAppBarLayout.visibility = View.VISIBLE
                                    bottomNavigationView.visibility = View.VISIBLE
                                    findNavController().popBackStack()
                                }
                            }
                        })
                }
            })
        }
    }

    private fun prepareViewModel() {
        val factory = RecipeViewModelFactory(
            FavoriteRepositoryImpl(FavoriteLocalSourceImpl(requireContext())),
            MealsRepositoryImpl(APIClient, MealLocalSourceImpl(requireContext()))
        )
        viewModel = ViewModelProvider(this, factory).get(DetailsViewModel::class.java)
    }

    fun setOnClickListener(view: View, labelID: Int, contentID: Int, arrowID: Int) {
        view.findViewById<TextView>(labelID).setOnClickListener {
            val ingredientsContent = view.findViewById<ConstraintLayout>(contentID)
            val ingredientsArrow = view.findViewById<ImageView>(arrowID)
            if (ingredientsContent.isVisible) {
                ingredientsArrow.setImageResource(R.drawable.expand_arrow)
                ingredientsContent.visibility = View.GONE
            } else {
                ingredientsArrow.setImageResource(R.drawable.shrink_arrow)
                ingredientsContent.visibility = View.VISIBLE
            }
        }
    }
}