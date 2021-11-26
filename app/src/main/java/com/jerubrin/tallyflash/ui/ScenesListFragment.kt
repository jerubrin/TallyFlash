package com.jerubrin.tallyflash.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jerubrin.tallyflash.data.Scene
import com.jerubrin.tallyflash.databinding.FragmentScenesListBinding
import com.jerubrin.tallyflash.domain.UiState
import com.jerubrin.tallyflash.ui.adapter.InputsListAdapter
import com.jerubrin.tallyflash.ui.vm.ScenesListViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ScenesListFragment : Fragment() {
    
    private val viewModel: ScenesListViewModel by activityViewModels()
    
    private var _binding: FragmentScenesListBinding? = null
    private val binding get() = _binding !!
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadVMixList()
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentScenesListBinding.inflate(inflater, container, false)
        
        val inputsListAdapter = InputsListAdapter(::onItemClick)
        binding.recyclerViewInputs.apply {
            adapter = inputsListAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        
        lifecycleScope.launch {
            viewModel.uiState.collectLatest {
                when(it) {
                    is UiState.Loading -> {
                        binding.progressLoading.isVisible = true
                        binding.frameError.isVisible = false
                    }
                    is UiState.Error -> {
                        binding.progressLoading.isVisible = false
                        binding.textViewErrorMsg.text = it.errorMessage
                        binding.frameError.isVisible = true
                        binding.btnRetry.setOnClickListener {
                            viewModel.loadVMixList()
                        }
                    }
                    is UiState.Ready<*> -> {
                        binding.progressLoading.isVisible = false
                        binding.frameError.isVisible = false
                        if (it.data is List<*>) {
                            val data = it.data as List<Scene>
                            inputsListAdapter.submitList(data)
                        }
                    }
                }
            }
        }
        
        return binding.root
    }
    
    private fun onItemClick (scene: Scene) {
        val action =
            ScenesListFragmentDirections.actionScenesListFragmentToTallyFragment(scene)
        findNavController().navigate(action)
    }
}