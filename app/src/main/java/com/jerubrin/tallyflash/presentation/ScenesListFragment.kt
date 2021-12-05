package com.jerubrin.tallyflash.presentation

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
import com.jerubrin.tallyflash.R
import com.jerubrin.tallyflash.entity.Scene
import com.jerubrin.tallyflash.databinding.FragmentScenesListBinding
import com.jerubrin.tallyflash.domain.State
import com.jerubrin.tallyflash.presentation.adapter.InputsListAdapter
import com.jerubrin.tallyflash.presentation.vm.ScenesListViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.IllegalStateException


class ScenesListFragment : Fragment() {
    
    private val viewModel: ScenesListViewModel by activityViewModels()
    
    private var _binding: FragmentScenesListBinding? = null
    private val binding get() = _binding !!
    
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
        
        readSceneList(inputsListAdapter)
    
        binding.btnRetry.setOnClickListener { readSceneList(inputsListAdapter) }
    
        //Set clear scene in service (when user close Tally fragment)
        viewModel.resetService()
        
        return binding.root
    }
    
    private fun readSceneList(adapter: InputsListAdapter) {
        lifecycleScope.launch {
            viewModel.loadSceneList().collectLatest {
                when(it) {
                    is State.Loading ->
                        showLoading()
                    is State.Error ->
                        showError()
                    is State.Ready<*> ->
                        showSceneList(it, adapter)
                    else ->
                        throw IllegalStateException("Unknown State received!")
                }
            }
        }
    }
    
    private fun showSceneList(it: State.Ready<*>, adapter: InputsListAdapter) {
        binding.progressLoading.isVisible = false
        binding.frameError.isVisible = false
        if (it.data is List<*> &&
            it.data.isNotEmpty() &&
            it.data[0] is Scene
        ) {
            adapter.submitList(it.data as List<Scene>)
        }
    }
    
    private fun showLoading() {
        binding.progressLoading.isVisible = true
        binding.frameError.isVisible = false
    }
    
    private fun showError() {
        val connectionData = viewModel.getConnectionData()
        binding.progressLoading.isVisible = false
        binding.textViewErrorMsg.text =
            getString(
                R.string.connection_error,
                connectionData.ip,
                connectionData.port
            )
        binding.frameError.isVisible = true
    }
    
    private fun onItemClick (scene: Scene) {
        val action =
            ScenesListFragmentDirections.actionScenesListFragmentToTallyFragment(scene)
        findNavController().navigate(action)
    }
}