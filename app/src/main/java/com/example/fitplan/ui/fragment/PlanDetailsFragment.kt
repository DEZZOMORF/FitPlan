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
import com.example.fitplan.databinding.FragmentPlanDetailsBinding
import com.example.fitplan.manager.SharedPreferencesManager
import com.example.fitplan.model.Plan
import com.example.fitplan.util.DataState
import com.example.fitplan.viewmodel.PlanDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlanDetailsFragment : Fragment() {

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager
    private val viewModel: PlanDetailsViewModel by viewModels()
    private lateinit var binding: FragmentPlanDetailsBinding

    companion object {
        private const val ID: String = "id"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPlanDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObserver()
        initViews()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getPlan(requireArguments().getInt(ID))
    }

    private fun subscribeObserver() {
        viewModel.dataState.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Success<Plan> -> {
                    displayProgressBar(false)
                    binding.plan = it.data
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
        with(binding.toolbar) {
            btnSetting.visibility = View.VISIBLE
            btnBack.visibility = View.VISIBLE
            btnBack.setOnClickListener { requireActivity().onBackPressed() }
            btnSetting.setOnClickListener { findNavController().navigate(R.id.action_planDetailsFragment_to_settingsFragment) }
        }
        binding.imageVisibility = sharedPreferencesManager.showingImageState
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