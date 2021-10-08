package com.example.fitplan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitplan.model.Plan
import com.example.fitplan.repository.PlanRepository
import com.example.fitplan.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanDetailsViewModel @Inject constructor(
    private val planRepository: PlanRepository,
) : ViewModel() {

    private val _dataState: MutableLiveData<DataState<Plan>> = MutableLiveData()
    val dataState: LiveData<DataState<Plan>>
        get() = _dataState

    fun getPlan(id: Int) {
        viewModelScope.launch {
            planRepository.getPlan(id).let { _dataState.postValue(it) }
        }
    }
}
