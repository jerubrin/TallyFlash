package com.jerubrin.tallyflash.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jerubrin.tallyflash.databinding.FragmentConnectBinding
import com.jerubrin.tallyflash.prefs.SharedPrefConnectionProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ConnectFragment : Fragment() {
    
    lateinit var ipAddress: String
    lateinit var port: String
    
    private var _binding: FragmentConnectBinding? = null
    private val binding get() = _binding!!
    
    @Inject
    lateinit var sharedPrefConnection: SharedPrefConnectionProvider
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        ipAddress = sharedPrefConnection.getUrl()
        port = sharedPrefConnection.getPort()
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentConnectBinding.inflate(inflater, container, false)
        
        binding.editTextIp.setText(ipAddress)
        binding.editTextPort.setText(port)
        
        binding.btnConnect.setOnClickListener {
            val url = binding.editTextIp.text.toString()
            val port = binding.editTextPort.text.toString()
            
            setSharedPrefs(url, port)
            
            val action =
                ConnectFragmentDirections.actionConnectFragmentToScenesListFragment()
            findNavController().navigate(action)
        }
        
        return binding.root
    }
    
    private fun setSharedPrefs(ip: String, port: String) {
        sharedPrefConnection.setUrl(ip)
        sharedPrefConnection.setPort(port)
    }
}