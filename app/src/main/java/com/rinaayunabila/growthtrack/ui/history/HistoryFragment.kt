package com.rinaayunabila.growthtrack.ui.history

import android.content.Intent
import android.os.Bundle
import android.util.JsonToken
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.rinaayunabila.growthtrack.MainViewModel
import com.rinaayunabila.growthtrack.R
import com.rinaayunabila.growthtrack.ViewModelFactory
import com.rinaayunabila.growthtrack.data.DataaItem
import com.rinaayunabila.growthtrack.data.service.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HistoryFragment : Fragment(), OnItemClickListener {
    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    override fun onItemClick(dataItem: DataaItem) {
        val intent = Intent(requireContext(), DetailHistory::class.java).apply {
            putExtra("babyName", dataItem.babyName)
            putExtra("gender", dataItem.gender)
            putExtra("berat", dataItem.weight)
            putExtra("dibuat", dataItem.createdAt)
            putExtra("prediksi", dataItem.predictionsBinary)
            putExtra("umur",dataItem.age)
            putExtra("tinggi",dataItem.height)
            putExtra("menyusui",dataItem.breastfeeding)
        }
        startActivity(intent)
    }

    private fun setupObservers() {
        mainViewModel.getUserModel().observe(viewLifecycleOwner, Observer { userModel ->
            userModel?.let {
                getHistory(it.token)
            }
        })
    }

    private fun loding(b:Boolean) {
        val loding : ProgressBar = view?.findViewById(R.id.progressBar)!!

        if (b){
            loding.visibility = View.VISIBLE
        }else{
            loding.visibility = View.INVISIBLE
        }
    }

    private fun getHistory(token: String) {
        loding(true)
        val client = ApiConfig.getApiService().getHistory(token)
        client.enqueue(object : Callback<com.rinaayunabila.growthtrack.data.Response> {
            override fun onResponse(
                call: Call<com.rinaayunabila.growthtrack.data.Response>,
                response: Response<com.rinaayunabila.growthtrack.data.Response>
            ) {
                loding(false)
                if (response.isSuccessful){
                    val responseData = response.body()
                    if (responseData != null && responseData.data != null) {
                        val historyList = responseData.data
                        val rcv: RecyclerView? = view?.findViewById(R.id.rvhistory)
                        val adapter = AdapterHistory(historyList, this@HistoryFragment)
                        rcv?.adapter = adapter
                    } else {
                        // Handle empty data case
                    }
                } else {
                    Log.d("responn", "Gagal: ${response.raw()}")
                }
            }

            override fun onFailure(call: Call<com.rinaayunabila.growthtrack.data.Response>, t: Throwable) {
                loding(false)
                Log.d("responn", "Gagal: ${t}")
            }
        })
    }
}

