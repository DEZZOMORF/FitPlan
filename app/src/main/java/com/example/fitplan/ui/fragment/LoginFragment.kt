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
import com.example.fitplan.manager.SharedPreferencesManager
import com.example.fitplan.model.LoginResponse
import com.example.fitplan.util.DataState
import com.example.fitplan.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.login_fragment.*

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.login_fragment, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObserver()
        btnLogin.setOnClickListener {
            viewModel.login(inputUsername.editText?.text.toString(), inputPassword.editText?.text.toString())
        }
    }

    private fun subscribeObserver() {
        viewModel.dataState.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Success<LoginResponse> -> {
                    displayProgressBar(false)
                    successLogin(it.data)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(it.exception.message)
                }
                is DataState.UserExceptionState -> {
                    displayProgressBar(false)
                    displayError(it.exception.message)
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
        findNavController().navigate(R.id.planListFragment)
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        progressBar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    private fun displayError(message: String?) {
        if (message != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Unknown error", Toast.LENGTH_SHORT).show()
        }
    }
}