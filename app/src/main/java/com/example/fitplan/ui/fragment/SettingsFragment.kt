package com.example.fitplan.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitplan.R
import com.example.fitplan.adapter.SettingsRecyclerViewAdapter
import com.example.fitplan.databinding.SettingsFragmentBinding
import com.example.fitplan.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

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
        binding.toolbar.btnSetting.visibility = View.GONE
        binding.toolbar.btnBack.visibility = View.VISIBLE
        binding.toolbar.btnBack.setOnClickListener { requireActivity().onBackPressed() }
        initRecyclerView()
        viewModel.logout = { findNavController().navigate(R.id.action_settingsFragment_to_loginFragment) }
    }

    private fun initRecyclerView() {
        settingsRecyclerViewAdapter = SettingsRecyclerViewAdapter(viewModel::onSettingClicked)
        binding.settingsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.settingsRecyclerView.adapter = settingsRecyclerViewAdapter
    }

}