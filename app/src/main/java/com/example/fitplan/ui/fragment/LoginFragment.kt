package com.example.fitplan.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.fitplan.R
import com.example.fitplan.databinding.FragmentLoginBinding
import com.example.fitplan.manager.SharedPreferencesManager
import com.example.fitplan.model.LoginResponse
import com.example.fitplan.util.DataState
import com.example.fitplan.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObserver()
        binding.btnLogin.setOnClickListener {
            viewModel.login(binding.inputUsername.editText?.text.toString(), binding.inputPassword.editText?.text.toString())
        }
    }

    private fun subscribeObserver() {
        viewModel.dataState.observe(viewLifecycleOwner) { data ->
            when (data) {
                is DataState.Success<LoginResponse?> -> {
                    displayProgressBar(false)
                    data.data?.let { loginData -> successLogin(loginData) }
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(data.exception.message)
                }
                is DataState.UserExceptionState -> {
                    displayProgressBar(false)
                    displayError(data.exception.message)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        }
    }

    private fun successLogin(response: LoginResponse) {
        Toast.makeText(context, "success login", Toast.LENGTH_LONG).show()
        SharedPreferencesManager(context).userAccessToken = response.accessToken
        findNavController().navigate(R.id.action_loginFragment_to_planListFragment)
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        binding.progressBar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    private fun displayError(message: String?) {
        if (message != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Unknown error", Toast.LENGTH_SHORT).show()
        }
    }
}