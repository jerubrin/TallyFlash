package com.jerubrin.tallyflash.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.jerubrin.tallyflash.R
import com.jerubrin.tallyflash.databinding.FragmentTallyBinding
import com.jerubrin.tallyflash.entity.Scene
import com.jerubrin.tallyflash.entity.SceneState
import com.jerubrin.tallyflash.entity.SettingsData
import com.jerubrin.tallyflash.ui.vm.TallyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TallyFragment : Fragment() {
    
    private val viewModel: TallyViewModel by activityViewModels()
    
    private val args by navArgs<TallyFragmentArgs>()
    
    private var _binding : FragmentTallyBinding? = null
    private val binding get() = _binding !!
    
    private var settingsData = SettingsData()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setNewScene(args.scene)
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTallyBinding.inflate(inflater, container, false)
    
        binding.textViewName.text = viewModel.name
        binding.textViewNumber.text = viewModel.number.toString()
        
        lifecycleScope.launch {
            viewModel.sceneState?.collectLatest {
                settingsData = viewModel.readSharedPrefMainUseCase.execute(Unit)
                when(it) {
                    SceneState.ACTIVE -> {
                        binding.root.setBackgroundColor(settingsData.activeColor)
                        binding.textState.text = settingsData.activeText
                    }
                    SceneState.PREVIEW -> {
                        binding.root.setBackgroundColor(settingsData.previewColor)
                        binding.textState.text = settingsData.previewText
                    }
                    SceneState.OFF -> {
                        binding.root.setBackgroundColor(settingsData.offColor)
                        binding.textState.text = settingsData.offText
                    }
                    else -> {
                        binding.root.setBackgroundColor(
                            ContextCompat.getColor(requireContext(), R.color.error_text)
                        )
                        binding.textState.text = getString(R.string.error)
                    }
                }
            }
        }
        
        return binding.root
    }
    
    override fun onDestroy() {
        super.onDestroy()
        viewModel.setNewScene(Scene())
    }
    
}