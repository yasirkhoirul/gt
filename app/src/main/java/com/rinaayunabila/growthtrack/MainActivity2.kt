package com.rinaayunabila.growthtrack

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rinaayunabila.growthtrack.data.UserPreference
import com.rinaayunabila.growthtrack.data.UserRepository
import com.rinaayunabila.growthtrack.data.service.ApiService
import com.rinaayunabila.growthtrack.databinding.ActivityMain2Binding
import com.rinaayunabila.growthtrack.di.dataStore
import com.rinaayunabila.growthtrack.ui.SplashScreenViewModel

class MainActivity2 : AppCompatActivity() {



    private lateinit var binding: ActivityMain2Binding
    private lateinit var splashScreenViewModel:SplashScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the action bar
        setSupportActionBar(binding.toolbar) // Assuming you have a Toolbar with id "toolbar" in ActivityMain2Binding

        // Initialize Navigation Controller
        val navController = findNavController(R.id.nav_host_fragment_activity_main2)

        // Connect the BottomNavigationView with NavController
        binding.navView.setupWithNavController(navController)

        // Specify top level destinations for ActionBar configuration
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_rumahsakit, R.id.navigation_diagnose, R.id.historyFragment, R.id.profileFragment
            )
        )


        // Set up the ActionBar with Navigation Controller
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    // Override onSupportNavigateUp to handle navigation button clicks
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main2)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
