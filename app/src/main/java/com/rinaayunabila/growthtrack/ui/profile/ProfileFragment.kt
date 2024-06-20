package com.rinaayunabila.growthtrack.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rinaayunabila.growthtrack.MainViewModel
import com.rinaayunabila.growthtrack.R
import com.rinaayunabila.growthtrack.ViewModelFactory
import com.rinaayunabila.growthtrack.data.ResetRespon
import com.rinaayunabila.growthtrack.data.UsersResponse
import com.rinaayunabila.growthtrack.data.service.ApiConfig
import com.rinaayunabila.growthtrack.databinding.FragmentProfileBinding
import com.rinaayunabila.growthtrack.ui.SplashScreenActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment(), View.OnClickListener {
    private lateinit var email: String
    private var _binding: FragmentProfileBinding? = null
    private lateinit var mainViewModel: MainViewModel

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize ViewModel with Factory
        val factory = ViewModelFactory.getInstance(requireActivity())
        mainViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.reset.setOnClickListener(this)
        binding.logout.setOnClickListener {
            showLogoutDialog()
        }
        mainViewModel.getUserModel().observe(viewLifecycleOwner, Observer { userModel ->
            userModel?.let {
                Log.d("ProfileFragment", "Fetching user with token: ${it.token} and id: ${it.uid}")
                getUsers(it.token, it.uid)
            }
        })
    }

    private fun getUsers(token: String, id: String) {
        Log.d("ProfileFragment", "Fetching user with token: $token and id: $id")
        val client = ApiConfig.getApiService().getuser(token, id)
        client.enqueue(object : Callback<UsersResponse> {
            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                if (_binding == null) return // Check if binding is null before accessing it

                val responseData = response.body()
                responseData?.data?.let { data ->
                    binding.textView2.text = data.name
                    binding.name.text = data.name
                    email = data.email ?: "email"
                    binding.email.text = email
                } ?: run {
                    binding.textView2.text = "username"
                    binding.name.text = "username"
                    binding.email.text = "email"
                }
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                Log.e("ProfileFragment", "Failed to fetch user data", t)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        sendResetEmail()
    }

    private fun sendResetEmail() {
        Log.d("ProfileFragment", "Sending reset email to: $email")
        val client = ApiConfig.getApiService().sendEmail(email)
        client.enqueue(object : Callback<ResetRespon> {
            override fun onResponse(call: Call<ResetRespon>, response: Response<ResetRespon>) {
                if (_binding == null) return // Check if binding is null before accessing it

                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    Log.d("ProfileFragment", "Reset email sent successfully: ${responseBody.msg}")
                    Toast.makeText(requireContext(), responseBody.msg, Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("ProfileFragment", "Failed to send reset email: ${response.message()}")
                    Toast.makeText(requireContext(), response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResetRespon>, t: Throwable) {
                Log.e("ProfileFragment", "Error sending reset email", t)
            }
        })
    }

    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(resources.getString(R.string.logout))
        builder.setMessage("Are you sure you want to log out?")
        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            mainViewModel.logout()
            navigateToSplashScreen()
        }
        builder.setNegativeButton(android.R.string.cancel, null)
        builder.show()
    }

    private fun navigateToSplashScreen() {
        val intent = Intent(requireContext(), SplashScreenActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        requireActivity().finish()
    }
}
