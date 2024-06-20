package com.rinaayunabila.growthtrack

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rinaayunabila.growthtrack.data.UserPreference
import com.rinaayunabila.growthtrack.data.UserRepository
import com.rinaayunabila.growthtrack.data.UsersResponse
import com.rinaayunabila.growthtrack.data.service.ApiConfig
import com.rinaayunabila.growthtrack.data.service.ApiService
import com.rinaayunabila.growthtrack.databinding.ActivityMain2Binding
import com.rinaayunabila.growthtrack.di.dataStore
import com.rinaayunabila.growthtrack.ui.SplashScreenActivity
import com.rinaayunabila.growthtrack.ui.SplashScreenViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding
    private lateinit var splashScreenViewModel: SplashScreenViewModel
    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setupObservers()

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

    private fun setupObservers() {
        Log.d("MainActivity2", "Setting up observers")
        mainViewModel.getUserModel().observe(this, Observer { userModel ->
            userModel?.let {
                // Use the token or perform any actions with the user model here
                val token = it.token
                val id = it.uid
                Log.d("MainActivity2", "User ID: $id")
                getUsers(token, id)
            }
        })
    }

    private fun getUsers(token: String, id: String) {
        val client = ApiConfig.getApiService().getuser(token, id)
        client.enqueue(object : Callback<UsersResponse> {
            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                val responseData = response.body()
                if (responseData?.data?.name == null) {
                    Log.d("MainActivity2", "User name is null")
                    showDialog()
                } else {
                    Log.d("MainActivity2", "User name: ${responseData.data.name}")
                }
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                Log.e("MainActivity2", "Error fetching user data", t)
            }
        })
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.logout))
        builder.setMessage("Session expired, please log out")
        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            mainViewModel.logout()
            startActivity(Intent(this, SplashScreenActivity::class.java))
            finish()
        }
        builder.show()
    }
}
