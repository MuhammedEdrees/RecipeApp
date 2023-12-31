package com.example.recipeapp.auth.view

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.recipeapp.R
import com.example.recipeapp.auth.viewmodel.LoginViewmodel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class LoginFragment : Fragment() {
    lateinit var usernameTextInputLayout: TextInputLayout
    lateinit var usernameEditText: TextInputEditText
    lateinit var passwordTextInputLayout: TextInputLayout
    lateinit var passwordEditText: TextInputEditText
    lateinit var loginButton: MaterialButton
    lateinit var registerButton: MaterialButton
    @Inject
    lateinit var viewModel: LoginViewmodel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs = activity?.getSharedPreferences("user_prefs", MODE_PRIVATE)
        val editor = prefs?.edit()
        usernameTextInputLayout = view.findViewById(R.id.login_username_textfield)
        usernameEditText = usernameTextInputLayout.editText as TextInputEditText
        passwordTextInputLayout = view.findViewById(R.id.login_password_textfield)
        passwordEditText = passwordTextInputLayout.editText as TextInputEditText
        loginButton = view.findViewById(R.id.login_button)
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            viewModel.verifyUsernameExists(username)
                .observe(viewLifecycleOwner, Observer { result ->
                    if (result) {
                        usernameTextInputLayout.error = null
                        viewModel.getUserByUsername(username)
                            .observe(viewLifecycleOwner, Observer { user ->
                                if (password == user.password) {
                                    passwordTextInputLayout.error = null
                                    editor?.putInt("user_id", user.id)
                                    editor?.apply()
                                    view.findNavController().navigate(R.id.recipeActivity)
                                    requireActivity().finish()
                                } else {
                                    passwordTextInputLayout.error = "Wrong Password!"
                                }
                            })
                    } else {
                        usernameTextInputLayout.error = "Username Doesn't Exist!"
                    }
                })
        }
        registerButton = view.findViewById(R.id.login_register_button)
        registerButton.setOnClickListener {
            view.findNavController().navigate(R.id.registerFragment)
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