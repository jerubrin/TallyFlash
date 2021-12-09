package com.jerubrin.tallyflash.presentation


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.jerubrin.tallyflash.databinding.FragmentConnectionBinding
import com.jerubrin.tallyflash.domain.UiState
import com.jerubrin.tallyflash.entity.ConnectionData
import com.jerubrin.tallyflash.presentation.vm.ConnectionViewModel
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
        
        viewModel.connectionDataState.data.apply {
            binding.editTextIp.setText(ip)
            binding.editTextPort.setText(port)
        }
        
        binding.btnConnect.setOnClickListener {
            val connect = ConnectionData(
                binding.editTextIp.text.toString(),
                binding.editTextPort.text.toString()
            )
            
            viewModel.checkAndSaveConnectionData(UiState.Ready(connect)).also { state ->
                if (state is UiState.Ready<*>) {
                    val action =
                        ConnectionFragmentDirections.actionConnectionFragmentToScenesListFragment()
                    findNavController().navigate(action)
                }
                if (state is UiState.Error) {
                    binding.textViewErrorMessage.text = state.errorMessage
                }
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