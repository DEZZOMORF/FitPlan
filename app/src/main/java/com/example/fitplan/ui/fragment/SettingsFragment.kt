package com.example.fitplan.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitplan.R
import com.example.fitplan.adapter.SettingsRecyclerViewAdapter
import com.example.fitplan.databinding.SettingsFragmentBinding
import com.example.fitplan.model.Plan
import com.example.fitplan.model.SettingsItem
import com.example.fitplan.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.plan_list_fragment.*
import kotlinx.android.synthetic.main.plan_list_fragment.planRecyclerView
import kotlinx.android.synthetic.main.plan_toolbar.*
import kotlinx.android.synthetic.main.settings_fragment.*

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private val viewModel: SettingsViewModel by viewModels()
    private lateinit var binding: SettingsFragmentBinding
    private lateinit var settingsRecyclerViewAdapter: SettingsRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SettingsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setView()
        viewModel.settings.observe(viewLifecycleOwner, settingsRecyclerViewAdapter::update)
    }

    private fun setView() {
        binding.title = "Settings"
        btnSetting.visibility = View.GONE
        btnBack.visibility = View.VISIBLE
        btnBack.setOnClickListener { requireActivity().onBackPressed() }
        initRecyclerView()
        viewModel.logout = { findNavController().navigate(R.id.loginFragment) }
    }

    private fun initRecyclerView() {
        settingsRecyclerViewAdapter = SettingsRecyclerViewAdapter(viewModel::onSettingClicked)
        settingsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        settingsRecyclerView.adapter = settingsRecyclerViewAdapter
    }

}