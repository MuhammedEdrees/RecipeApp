package com.example.recipeapp.auth.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.lottie.LottieAnimationView
import com.example.recipeapp.R


class SplashFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lateinit var lottie : LottieAnimationView

            val view = inflater.inflate(R.layout.fragment_splash, container, false)
            lottie = view.findViewById(R.id.lottie)
           // lottie.animate().translationZ(2000f).setDuration(2000).setStartDelay(3000)

            Handler(Looper.getMainLooper()).postDelayed({
                 lottie.playAnimation()
                // Waiting for nav graph action
                //view.findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            }, 3000)

            return view
        }


}