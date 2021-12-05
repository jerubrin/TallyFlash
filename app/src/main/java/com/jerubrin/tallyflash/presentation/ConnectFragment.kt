package com.jerubrin.tallyflash.presentation


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jerubrin.tallyflash.R
import com.jerubrin.tallyflash.databinding.FragmentConnectBinding
import com.jerubrin.tallyflash.domain.usecase.BaseUseCase
import com.jerubrin.tallyflash.domain.usecase.prefs.BasePrefsUseCase
import com.jerubrin.tallyflash.domain.usecase.prefs.ReadSharedPrefConnectionUseCase
import com.jerubrin.tallyflash.domain.usecase.prefs.WriteSharedPrefConnectionUseCase
import com.jerubrin.tallyflash.entity.ConnectionData
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ConnectFragment : Fragment() {
    
    lateinit var url: String
    lateinit var port: String
    
    private var _binding: FragmentConnectBinding? = null
    private val binding get() = _binding!!
    
    @Inject
    lateinit var readSharedPrefConnectionUseCase: BasePrefsUseCase<ConnectionData, Unit>
    
    @Inject
    lateinit var writeSharedPrefConnectionUseCase: BasePrefsUseCase<Boolean, ConnectionData>
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    
        val connectionData = readSharedPrefConnectionUseCase.execute(Unit)
        url = connectionData.ip
        port = connectionData.port
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentConnectBinding.inflate(inflater, container, false)
        
        binding.editTextIp.setText(url)
        binding.editTextPort.setText(port)
        
        binding.btnConnect.setOnClickListener {
            url = binding.editTextIp.text.toString()
            port = binding.editTextPort.text.toString()
            
            when (checkIpAndPort(url, port)) {
                InputCheck.CORRECT -> {
                    val connectionData = ConnectionData(ip = url, port = port)
                    writeSharedPrefConnectionUseCase.execute(connectionData)
        
                    val action =
                        ConnectFragmentDirections.actionConnectFragmentToScenesListFragment()
                    findNavController().navigate(action)
                }
                InputCheck.WRONG_IP ->
                    binding.textViewErrorMessage.text = getString(R.string.wrong_ip)
                InputCheck.WRONG_PORT  ->
                    binding.textViewErrorMessage.text = getString(R.string.wrong_port)
            }
        }
        
        binding.btnSettings.setOnClickListener {
            val action =
                ConnectFragmentDirections.actionConnectFragmentToMainPreferenceFragment()
            findNavController().navigate(action)
        }
        
        return binding.root
    }
    
    private fun checkIpAndPort(url: String, port: String): InputCheck {
        if (url.count{ ".".contains(it) } != 3)
            return InputCheck.WRONG_IP
        if (url.replace(".", "").toLongOrNull() == null)
            return InputCheck.WRONG_IP
        if (port.toIntOrNull() == null)
            return InputCheck.WRONG_PORT
        return InputCheck.CORRECT
    }
    
    enum class InputCheck(val code: Int) {
        CORRECT(0),
        WRONG_IP(1),
        WRONG_PORT(2)
    }
}