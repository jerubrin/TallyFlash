package com.jerubrin.tallyflash.presentation


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.jerubrin.tallyflash.databinding.FragmentConnectionBinding
import com.jerubrin.tallyflash.presentation.vm.ConnectionViewModel
import com.jerubrin.tallyflash.presentation.vm.ConnectionViewModel.CorrectInputState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ConnectionFragment : Fragment() {
    
    private var _binding: FragmentConnectionBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: ConnectionViewModel by activityViewModels()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentConnectionBinding.inflate(inflater, container, false)
        
        binding.editTextIp.setText(viewModel.url)
        binding.editTextPort.setText(viewModel.port)
        
        binding.btnConnect.setOnClickListener {
            val url = binding.editTextIp.text.toString()
            val port = binding.editTextPort.text.toString()
            
            viewModel.checkAndSaveConnectionData(url, port).also {
                if (it is CorrectInputState.Correct) {
                    val action =
                        ConnectionFragmentDirections.actionConnectionFragmentToScenesListFragment()
                    findNavController().navigate(action)
                }
                binding.textViewErrorMessage.text = it.errorMsg
            }
        }
        
        binding.btnSettings.setOnClickListener {
            val action =
                ConnectionFragmentDirections.actionConnectionFragmentToMainPreferenceFragment()
            findNavController().navigate(action)
        }
        
        return binding.root
    }
}