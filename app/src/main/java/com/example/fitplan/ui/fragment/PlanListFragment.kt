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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.fitplan.R
import com.example.fitplan.adapter.PlanRecyclerViewAdapter
import com.example.fitplan.databinding.FragmentPlanListBinding
import com.example.fitplan.model.Plan
import com.example.fitplan.util.DataState
import com.example.fitplan.viewmodel.PlanListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlanListFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var planRecyclerViewAdapter: PlanRecyclerViewAdapter
    private val viewModel: PlanListViewModel by viewModels()
    private lateinit var binding: FragmentPlanListBinding

    companion object {
        private const val ID: String = "id"
        private const val TITLE: String = "Plan list"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPlanListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visibility = View.VISIBLE
        subscribeObserver()
        initRecyclerView()
    }

    override fun onRefresh() {
        planRecyclerViewAdapter.list.clear()
        planRecyclerViewAdapter.notifyDataSetChanged()
        viewModel.getList()
    }

    private fun subscribeObserver() {
        viewModel.dataState.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Success<List<Plan>> -> {
                    displayProgressBar(false)
                    initViews()
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

    private fun initViews() {
        with(binding) {
            title = TITLE
            swiperefresh.setOnRefreshListener(this@PlanListFragment)
            progressBar.visibility = View.GONE
        }
        with(binding.toolbar) {
            btnSetting.visibility = View.VISIBLE
            btnBack.visibility = View.GONE
            toolbarText.setOnClickListener { binding.planRecyclerView.smoothScrollToPosition(0) }
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
        binding.swiperefresh.isRefreshing = isDisplayed
    }

    private fun displayError(message: String?) {
        if (message != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Unknown error", Toast.LENGTH_SHORT).show()
        }
    }
}