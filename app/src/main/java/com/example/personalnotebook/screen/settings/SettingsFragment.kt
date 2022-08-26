package com.example.personalnotebook.screen.settings

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.example.personalnotebook.databinding.FragmentSettingsBinding
import com.example.personalnotebook.manager.NightMode
import com.example.personalnotebook.manager.SharedPrefsManager
import com.example.personalnotebook.screen.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    @Inject lateinit var sharedPrefsManager: SharedPrefsManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tv.text = "Settings"

        binding.btnNightTheme.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            sharedPrefsManager.nightMode = NightMode.DARK
        }

        binding.btnLightTheme.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            sharedPrefsManager.nightMode = NightMode.LIGHT
        }
    }
}