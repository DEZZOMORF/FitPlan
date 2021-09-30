package com.example.fitplan.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitplan.model.Plan
import com.example.fitplan.repository.FitPlanRepository
import com.example.test.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanListViewModel @Inject constructor(
    private val fitPlanRepository: FitPlanRepository,
) : ViewModel() {

    private val _dataState: MutableLiveData<DataState<List<Plan>>> = MutableLiveData()
    val dataState: LiveData<DataState<List<Plan>>>
        get() = _dataState

    fun getList() {
        viewModelScope.launch {
            fitPlanRepository.getList()
                .onEach { dataState ->
                    _dataState.postValue(dataState)
                }
                .launchIn(viewModelScope)
        }
    }

}