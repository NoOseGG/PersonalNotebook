package com.example.personalnotebook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.personalnotebook.databinding.ActivityMainBinding
import com.example.personalnotebook.manager.NightMode
import com.example.personalnotebook.manager.SharedPrefsManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment)
            .navController
    }
    private var _binding: ActivityMainBinding? = null
    private val binding get() = requireNotNull(_binding)
    @Inject lateinit var sharedPrefsManager: SharedPrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(
            when(sharedPrefsManager.nightMode) {
                NightMode.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
                NightMode.DARK -> AppCompatDelegate.MODE_NIGHT_YES
                NightMode.SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
        )
        setContentView(binding.root)


        /*setupActionBarWithNavController(navController)*/
        binding.bottomNavMenu.setupWithNavController(navController)
    }

    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }*/
}