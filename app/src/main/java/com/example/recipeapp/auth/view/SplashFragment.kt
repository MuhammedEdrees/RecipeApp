package com.example.recipeapp.auth.view

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.example.recipeapp.R


class SplashFragment : Fragment() {
    lateinit var lottie : LottieAnimationView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)
        lottie = view.findViewById(R.id.lottie)
        lottie.playAnimation()
        val prefs = activity?.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        Handler(Looper.getMainLooper()).postDelayed({
            val userID = prefs?.getInt("user_id", -1)?:-1
            if(userID == -1) {
                view.findNavController().navigate(R.id.loginFragment)
            } else {
                view.findNavController().navigate(R.id.recipeActivity)
                requireActivity().finish()
            }
        }, 3000)

            return view
        }
}