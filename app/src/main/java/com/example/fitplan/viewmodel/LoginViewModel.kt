package com.example.fitplan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitplan.model.LoginResponse
import com.example.fitplan.repository.UserRepository
import com.example.fitplan.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    companion object {
        private const val clientId = "XW9LtUlJfcCHMJbLyLen3lglY4COUgmCQErwjze7"
        private const val clientSecret = "ae55LjKyfVAf9dWaUX9HwoU5tpwHAVn2jKh8Of9zu3TP4zlD7JwguJhDYxXRT9zR2iuOIfHLrNiOAQSyAfRFs6dI7uXE8Yg7l3yyw7NTABnLr94VuPFKUOaaaCZ7xAv3"
        private const val grantType = "password"
    }

    private val _dataState: MutableLiveData<DataState<LoginResponse?>> = MutableLiveData()
    val dataState: LiveData<DataState<LoginResponse?>>
        get() = _dataState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            userRepository.login(username, password, clientId, clientSecret, grantType).let { _dataState.postValue(it) }
        }
    }
}