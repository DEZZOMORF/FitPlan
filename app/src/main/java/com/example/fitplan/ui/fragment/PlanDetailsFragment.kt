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
import com.example.fitplan.R
import com.example.fitplan.databinding.PlanDetailsFragmentBinding
import com.example.fitplan.model.Plan
import com.example.fitplan.util.DataState
import com.example.fitplan.viewmodel.PlanDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.plan_list_fragment.*
import kotlinx.android.synthetic.main.plan_toolbar.*

@AndroidEntryPoint
class PlanDetailsFragment : Fragment() {

    private val viewModel: PlanDetailsViewModel by viewModels()
    private lateinit var binding: PlanDetailsFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = PlanDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeObserver()
        viewModel.getPlan(requireArguments().getInt("id"))
    }

    private fun subscribeObserver() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            when (dataState) {
                is DataState.Success<Plan> -> {
                    displayProgressBar(false)
                    setView(dataState.data)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(dataState.exception.message)
                }
                is DataState.UserExceptionState -> {
                    displayProgressBar(false)
                    displayError(dataState.exception.toString())
                    findNavController().navigate(R.id.loginFragment)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }

    private fun setView(plan: Plan) {
        btnSetting.visibility = View.VISIBLE
        btnBack.visibility = View.VISIBLE
        btnBack.setOnClickListener { requireActivity().onBackPressed() }
        btnSetting.setOnClickListener { findNavController().navigate(R.id.action_planDetailsFragment_to_settingsFragment) }
        binding.plan = plan
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