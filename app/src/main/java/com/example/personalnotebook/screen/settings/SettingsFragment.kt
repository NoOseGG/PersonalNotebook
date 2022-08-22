package com.example.personalnotebook.screen.settings

import android.os.Bundle
import android.view.View
import com.example.personalnotebook.databinding.FragmentSettingsBinding
import com.example.personalnotebook.screen.BaseFragment

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tv.text = "Settings"
    }
}