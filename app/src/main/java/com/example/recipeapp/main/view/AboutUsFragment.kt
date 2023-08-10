package com.example.recipeapp.main.view

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.example.recipeapp.R
import com.google.android.material.divider.MaterialDivider

class AboutUsFragment : Fragment() {

    lateinit var aboutApp:TextView
    lateinit var aboutUs:TextView
    lateinit var appInfo:TextView
    lateinit var authorsInfo:ConstraintLayout
    lateinit var appArrow:ImageView
    lateinit var  authorsArrow:ImageView
    lateinit var icon:ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_us, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        aboutApp=view.findViewById(R.id.app_about)
        aboutUs=view.findViewById(R.id.about_us)
        appInfo=view.findViewById(R.id.app_about_info)
        authorsInfo=view.findViewById(R.id.about_authors_layout)
        appArrow=view.findViewById(R.id.app_Info_arrow)
        authorsArrow=view.findViewById(R.id.us_info_arrow)
        icon=view.findViewById(R.id.imageView)
        icon.setImageResource(R.drawable.culinary_art_icon)

        aboutApp.setOnClickListener {
            if (appInfo.isVisible){
                appInfo.visibility=View.GONE
                appArrow.setImageResource(R.drawable.expand_arrow)
            }else{
                appInfo.visibility=View.VISIBLE
                appArrow.setImageResource(R.drawable.shrink_arrow)
            }
        }

        aboutUs.setOnClickListener {
            if(authorsInfo.isVisible){
                authorsInfo.visibility=View.GONE
                authorsArrow.setImageResource(R.drawable.expand_arrow)
            }else{
                authorsInfo.visibility=View.VISIBLE
                authorsArrow.setImageResource(R.drawable.shrink_arrow)
            }
        }



    }
}