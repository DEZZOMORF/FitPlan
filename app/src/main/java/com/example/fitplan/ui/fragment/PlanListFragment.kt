package com.example.fitplan.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitplan.R
import com.example.fitplan.adapter.PlanRecyclerViewAdapter
import com.example.fitplan.databinding.PlanListFragmentBinding
import com.example.fitplan.model.Plan
import com.example.fitplan.util.DataState
import com.example.fitplan.viewmodel.PlanListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlanListFragment : Fragment() {

    private val viewModel: PlanListViewModel by viewModels()

    @Inject
    lateinit var planRecyclerViewAdapter: PlanRecyclerViewAdapter
    private lateinit var binding: PlanListFragmentBinding

    companion object {
        private val ID: String = "id"
        private val TITLE: String = "Plan list"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = PlanListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObserver()
        initRecyclerView()
    }

    private fun subscribeObserver() {
        viewModel.dataState.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Success<List<Plan>> -> {
                    displayProgressBar(false)
                    setView()
                    planRecyclerViewAdapter.list = ArrayList(it.data)
                    planRecyclerViewAdapter.notifyItemRangeInserted(0, it.data.size)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(it.exception.message)
                }
                is DataState.UserExceptionState -> {
                    displayProgressBar(false)
                    displayError(it.exception.message)
                    if (it.exception.code == 401) {
                        findNavController().navigate(R.id.loginFragment)
                    }
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        }
    }

    private fun setView() {
        binding.title = TITLE
        with(binding.toolbar) {
            btnSetting.visibility = View.VISIBLE
            btnBack.visibility = View.GONE
            btnSetting.setOnClickListener { findNavController().navigate(R.id.action_planListFragment_to_settingsFragment) }
        }
    }

    private fun initRecyclerView() {
        binding.planRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.planRecyclerView.adapter = planRecyclerViewAdapter
        planRecyclerViewAdapter.onItemClickListener = {
            val bundle = Bundle()
            bundle.putInt(ID, it)
            findNavController().navigate(R.id.action_planListFragment_to_planDetailsFragment, bundle)
        }
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