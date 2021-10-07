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
import com.example.fitplan.databinding.FragmentSettingsBinding
import com.example.fitplan.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    @Inject
    lateinit var settingsRecyclerViewAdapter: SettingsRecyclerViewAdapter
    private val viewModel: SettingsViewModel by viewModels()
    private lateinit var binding: FragmentSettingsBinding

    companion object {
        private const val TITLE = "Settings"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        viewModel.settings.observe(viewLifecycleOwner, settingsRecyclerViewAdapter::update)
    }

    private fun initViews() {
        binding.title = TITLE
        with(binding.toolbar) {
            btnSetting.visibility = View.GONE
            btnBack.visibility = View.VISIBLE
            btnBack.setOnClickListener { requireActivity().onBackPressed() }
        }
        initRecyclerView()
        viewModel.logout = { findNavController().navigate(R.id.action_settingsFragment_to_loginFragment) }
    }

    private fun initRecyclerView() {
        settingsRecyclerViewAdapter.clickBlock = viewModel::onSettingClicked
        binding.settingsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.settingsRecyclerView.adapter = settingsRecyclerViewAdapter
    }
}