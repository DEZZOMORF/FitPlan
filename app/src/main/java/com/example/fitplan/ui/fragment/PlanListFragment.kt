package com.example.fitplan.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitplan.R
import com.example.fitplan.adapter.PlanRecyclerViewAdapter
import com.example.fitplan.databinding.PlanListFragmentBinding
import com.example.fitplan.model.Plan
import com.example.fitplan.viewmodel.PlanListViewModel
import com.example.test.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.login_fragment.progressBar
import kotlinx.android.synthetic.main.plan_list_fragment.*
import kotlinx.android.synthetic.main.plan_toolbar.*

@AndroidEntryPoint
class PlanListFragment: Fragment() {

    private val viewModel: PlanListViewModel by viewModels()
    private val planRecyclerViewAdapter: PlanRecyclerViewAdapter = PlanRecyclerViewAdapter()
    private lateinit var binding: PlanListFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = PlanListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObserver()
        viewModel.getList()
    }

    private fun subscribeObserver() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            when (dataState) {
                is DataState.Success<List<Plan>> -> {
                    displayProgressBar(false)
                    setView()
                    initRecyclerView(dataState.data)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(dataState.exception.message)
                    findNavController().navigate(R.id.loginFragment)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }

    private fun setView() {
        binding.title = "Plan list"
        btnSetting.visibility = View.VISIBLE
        btnBack.visibility = View.GONE
    }

    private fun initRecyclerView(response: List<Plan>) {
        planRecyclerViewAdapter.list = ArrayList(response)
        planRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        planRecyclerView.adapter = planRecyclerViewAdapter
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