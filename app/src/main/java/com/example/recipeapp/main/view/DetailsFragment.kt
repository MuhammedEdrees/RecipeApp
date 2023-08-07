package com.example.recipeapp.main.view

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.button.MaterialButton
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
        prepareViewModel()
        viewModel.getRemoteMeal(args.mealId)
        viewModel.meal.observe(viewLifecycleOwner) {meal ->
            TODO("m3ak el meal 3ee4 7yatak hena gwa el observer")
        }
        val collapsingToolbarLayout: CollapsingToolbarLayout = view.findViewById(R.id.collapsingToolbarLayout)
        collapsingToolbarLayout.title = "remo meal"
        collapsingToolbarLayout.setBackgroundResource(R.drawable.food)

        view.findViewById<TextView>(R.id.categoryContent).text = "food"
        view.findViewById<TextView>(R.id.areaContent).text = "egypt"
        view.findViewById<TextView>(R.id.tagsContent).text = "omg,sooooooo gooooood"

        view.findViewById<TextView>(R.id.instructionsTextContent).text = "this meal man is soooooooooooooooooo\n" +
                "goooooooooooooooooooooooooooooooooooooooooooooooooooooooood\n" +
                "you should try it dude"

        val recyclerView: RecyclerView = view.findViewById(R.id.ingredientsTable)
        recyclerView.layoutManager = LinearLayoutManager(context) // it was this
        val tableData = ArrayList<IngredientRow>()
        tableData.add(IngredientRow("Data 1", "Data A"))
        tableData.add(IngredientRow("Data 2", "Data B"))
        tableData.add(IngredientRow("Data 2", "Data B"))
        tableData.add(IngredientRow("Data 2", "Data B"))
        tableData.add(IngredientRow("Data 2", "Data B"))
        tableData.add(IngredientRow("Data 2", "Data B"))
        tableData.add(IngredientRow("Data 2", "Data B"))
        tableData.add(IngredientRow("Data 2", "Data B"))
        tableData.add(IngredientRow("Data 2", "Data B"))
        val adapter = TableAdapter(tableData)
        recyclerView.adapter = adapter

        setOnClickListener(view,R.id.generalInfo, R.id.generalInfoContent, R.id.generalInfoArrow)
        setOnClickListener(view,R.id.instructions, R.id.instructionsContent, R.id.instructionsArrow)
        setOnClickListener(view,R.id.ingredients, R.id.ingredientsContent, R.id.ingredientsArrow)
        setOnClickListener(view,R.id.youtube, R.id.youtubeContent, R.id.youtubeArrow)


        val startVideoBtn: MaterialButton = view.findViewById(R.id.startVideoBtn)
        startVideoBtn.setTextColor(Color.BLACK)
        startVideoBtn.setBackgroundColor(Color.RED)

        val youTubePlayerView: YouTubePlayerView = view.findViewById(R.id.youtube_player_view)
        lifecycle.addObserver(youTubePlayerView)
        val videoLayout: ConstraintLayout = view.findViewById(R.id.youtubeVideoLayout)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                val videoId = "dQw4w9WgXcQ"
                youTubePlayer.loadVideo(videoId, 0F)
                youTubePlayer.pause()
                view.findViewById<Button>(R.id.startVideoBtn).setOnClickListener {
                    videoLayout.visibility = View.VISIBLE
                    youTubePlayer.play()
                }

                view.findViewById<ImageView>(R.id.closeVideoBtn).setOnClickListener {
                    videoLayout.visibility = View.GONE
                    youTubePlayer.pause()
                }

                requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true){
                    override fun handleOnBackPressed() {
                        if(videoLayout.visibility == View.VISIBLE)
                        {
                            videoLayout.visibility = View.GONE
                            youTubePlayer.pause()
                        }
                        else
                        {
                            requireActivity().finish()
                        }
                    }
                })
            }
        })
    }

    private fun prepareViewModel() {
        val factory = RecipeViewModelFactory(
            FavoriteRepositoryImpl(FavoriteLocalSourceImpl(requireContext())),
            MealsRepositoryImpl(APIClient, MealLocalSourceImpl(requireContext()))
        )
        viewModel = ViewModelProvider(this, factory).get(DetailsViewModel::class.java)
    }

    fun setOnClickListener(view: View,labelID: Int, contentID: Int, arrowID: Int)
    {
        view.findViewById<TextView>(labelID).setOnClickListener {
            val ingredientsContent = view.findViewById<ConstraintLayout>(contentID)
            val ingredientsArrow = view.findViewById<ImageView>(arrowID)
            if(ingredientsContent.isVisible)
            {
                ingredientsArrow.setImageResource(R.drawable.expand_arrow)
                ingredientsContent.visibility = View.GONE
            }
            else
            {
                ingredientsArrow.setImageResource(R.drawable.shrink_arrow)
                ingredientsContent.visibility = View.VISIBLE
            }
        }
    }
}