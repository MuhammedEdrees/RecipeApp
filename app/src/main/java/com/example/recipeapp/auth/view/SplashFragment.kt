package com.example.recipeapp.auth.view

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
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
           // lottie.animate().translationZ(2000f).setDuration(2000).setStartDelay(3000)

            Handler(Looper.getMainLooper()).postDelayed({
                 lottie.playAnimation()
                // Waiting for nav graph action
                view.findNavController().navigate(R.id.registerFragment)
            }, 3000)

            return view
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs = activity?.getPreferences(Context.MODE_PRIVATE)
        lottie = view.findViewById(R.id.lottie)
        Handler(Looper.getMainLooper()).postDelayed({
            lottie.playAnimation()
            val userID = prefs?.getInt("user_id", -1)?:-1
            if(userID == -1) {
                view.findNavController().navigate(R.id.loginFragment)
            } else {
                view.findNavController().navigate(R.id.recipeActivity)
            }
        }, 3000)
    }


}