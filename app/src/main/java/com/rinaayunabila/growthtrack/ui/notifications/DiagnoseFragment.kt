package com.rinaayunabila.growthtrack.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rinaayunabila.growthtrack.MainViewModel
import com.rinaayunabila.growthtrack.R
import com.rinaayunabila.growthtrack.ViewModelFactory
import com.rinaayunabila.growthtrack.data.Postpredict
import com.rinaayunabila.growthtrack.data.PredictRequest
import com.rinaayunabila.growthtrack.data.service.ApiConfig
import com.rinaayunabila.growthtrack.databinding.FragmentNotificationsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class DiagnoseFragment : Fragment() {
    private lateinit var selectedGender: String
    private lateinit var selectedbreed: String
    private lateinit var namabayi:String
    private var token:String = ""
    private var usiabayi by Delegates.notNull<Int>()
    private var tinggibadan by Delegates.notNull<Int>()
    private var berat by Delegates.notNull<Int>()
    private var a by Delegates.notNull<Int>()
    private var b by Delegates.notNull<Int>()
    private var _binding: FragmentNotificationsBinding? = null
    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        a = -1  // Or any default value that makes sense
        b = -1
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //

        val jeniskelamin = binding.radioGroup
        jeniskelamin.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = group.findViewById(checkedId)
            selectedGender = radioButton.text.toString()
            if (selectedGender.isNotEmpty()){
                if (selectedGender == "Boy"){
                    a = 1
                }else{
                    a = 0
                }

            }else{
                a=-1
            }

//            Log.d("pilihanandaJenis",selectedGender.trim())
//            Log.d("pilihanandaJenis","hasil${a}")
        }
        val menyusui = binding.radioGroup2
        menyusui.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = group.findViewById(checkedId)
            selectedbreed = radioButton.text.toString()

            // Check if selectedbreed is not empty
            if (selectedbreed.isNotEmpty()) {
                // Assign value to b based on selectedbreed
                if (selectedbreed == "Yes") {
                    b = 1
                } else {
                    b = 0
                }
            } else {
                // Handle case when nothing is selected
                b = -1 // or any default value that makes sense in your context
            }

//            Log.d("pilihanandaMenyusui", selectedbreed)
//            Log.d("pilihanandaMenyusui", "hasil$b")
        }

        val tombol = binding.button
        tombol.setOnClickListener {
            //namabayi

            namabayi = binding.namabayi.text.toString()
            if (namabayi.isEmpty()){
                Toast.makeText(requireContext(),"nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //usia
            val usia = binding.textinputeditUsia.text.toString()
            usiabayi = if (usia.isNotEmpty()) {
                try {
                    usia.toInt()
                } catch (e: NumberFormatException) {
                    // Penanganan jika input tidak valid
                    Log.e("InputError", "Failed to parse age input: ${e.message}")
                    // Misalnya, memberikan nilai default atau menampilkan pesan kesalahan
                    0 // Nilai default jika gagal mengonversi
                }
            } else {
                // Handle case when input is empty
                Toast.makeText(requireContext(), "Usia tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
                Log.e("InputError", "Age input is empty")
                0 // Nilai default jika input kosong
            }
            //tinggibadan
            val tinggibadanbayi = binding.textinputeditTinggi.text.toString()
            tinggibadan = if (tinggibadanbayi.isNotEmpty()) {
                try {
                    tinggibadanbayi.toInt()
                } catch (e: NumberFormatException) {
                    // Penanganan jika input tidak valid
                    Log.e("InputError", "Failed to parse age input: ${e.message}")
                    // Misalnya, memberikan nilai default atau menampilkan pesan kesalahan
                    0 // Nilai default jika gagal mengonversi
                }
            } else {
                // Handle case when input is empty
                Toast.makeText(requireContext(), "tinggi badan tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
                Log.e("InputError", "Age input is empty")
                0 // Nilai default jika input kosong
            }
            //beratbadan
            val beratbayi = binding.textinputeditBerat.text.toString()
            berat = if (beratbayi.isNotEmpty()) {
                try {
                    beratbayi.toInt()
                } catch (e: NumberFormatException) {
                    // Penanganan jika input tidak valid
                    Log.e("InputError", "Failed to parse age input: ${e.message}")
                    // Misalnya, memberikan nilai default atau menampilkan pesan kesalahan
                    0 // Nilai default jika gagal mengonversi
                }
            } else {
                // Handle case when input is empty
                Toast.makeText(requireContext(), "berat bayi tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
                Log.e("InputError", "Age input is empty")
                0 // Nilai default jika input kosong
            }
            if (a==-1||b==-1){
                Toast.makeText(requireContext(), "silahkan pili salahsatu pada pilihan diatas", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Log.d("pilihan semuanya","namabayi = ${namabayi}, tinggibadan ${tinggibadan}, berat ${berat}, jenis ${a}, menyusui ${b}")
            val predictRequest = PredictRequest(
                babyName = namabayi,
                age = usiabayi,
                weight = berat,
                height = tinggibadan,
                gender = a,
                breastfeeding = b
            )
            mainViewModel.getUserModel().observe(viewLifecycleOwner, Observer { userModel ->
                userModel?.let {
                    this.token=it.token
                }
            })
            val client = ApiConfig.getApiService().postpredict(token,predictRequest)
            client.enqueue(object : Callback<Postpredict> {
                override fun onResponse(call: Call<Postpredict>, response: Response<Postpredict>) {
                    val responseBody = response.body()
                    if (response.isSuccessful && responseBody != null) {
//                        Log.d("responredict", responseBody.msg.toString())
//                        Log.d("responredict", responseBody.predictionsBinary.toString())
                        if (responseBody.predictionsBinary==1){
                            Log.d("responredict", responseBody.msg.toString())
                            Log.d("responredict", responseBody.predictionsBinary.toString())

                            findNavController().navigate(R.id.action_navigation_diagnose_to_stunting)
                        }else{
                            Log.d("responredict", responseBody.msg.toString())
                            Log.d("responredict", responseBody.predictionsBinary.toString())
                            findNavController().navigate(R.id.action_navigation_diagnose_to_tidakStunting)

                        }
                    } else {
                        Log.d("responredict", "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<Postpredict>, t: Throwable) {
                    TODO("Not yet implemented")
                    Log.d("responpredict","${t}")
                }

            })
        }

//        val textView: TextView = binding.textNotifications
//        notificationsViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}