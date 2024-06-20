package com.rinaayunabila.growthtrack.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.rinaayunabila.growthtrack.MainViewModel
import com.rinaayunabila.growthtrack.R
import com.rinaayunabila.growthtrack.ViewModelFactory
import com.rinaayunabila.growthtrack.data.UsersResponse
import com.rinaayunabila.growthtrack.data.service.ApiConfig
import com.rinaayunabila.growthtrack.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {
        mainViewModel.getUserModel().observe(viewLifecycleOwner, Observer { userModel ->
            userModel?.let {
                getUsers(it.token, it.uid)
            }
        })
    }

    private fun setupClickListeners() {
        binding.cardView2.setOnClickListener {
            findNavController().navigate(R.id.navigation_diagnose)
        }
        binding.cardView.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_historyFragment)
        }
        binding.cardView4.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_articleActivity)
        }
        binding.cardView3.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_navigation_rumahsakit)
        }
    }

    private fun getUsers(token: String, id: String) {
        val client = ApiConfig.getApiService().getuser(token, id)
        client.enqueue(object : Callback<UsersResponse> {
            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                val responseData = response.body()
                binding.usernamesHome.text = responseData?.data?.name ?: "username"
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                Log.e("HomeFragment", "Error fetching user data", t)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
