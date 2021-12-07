package com.jerubrin.tallyflash.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.jerubrin.tallyflash.R
import com.jerubrin.tallyflash.databinding.FragmentTallyBinding
import com.jerubrin.tallyflash.domain.UiState
import com.jerubrin.tallyflash.entity.SceneState
import com.jerubrin.tallyflash.presentation.vm.TallyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class TallyFragment : Fragment() {
    
    private val viewModel: TallyViewModel by activityViewModels()
    
    private val args by navArgs<TallyFragmentArgs>()
    
    private var _binding : FragmentTallyBinding? = null
    private val binding get() = _binding !!
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setNewScene(
            UiState.Ready(args.scene)
        )
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTallyBinding.inflate(inflater, container, false)
    
        viewModel.currentSceneUiState.data.apply {
            binding.textViewName.text = shortTitle
            binding.textViewNumber.text = number.toString()
        }
        
        lifecycleScope.launch {
            viewModel.sceneState.collectLatest {
                val settingsData = viewModel.getSettingsData.data
                when(it) {
                    SceneState.ACTIVE -> {
                        binding.root.background = settingsData.activeColor.toDrawable()
                        binding.textState.text = settingsData.activeText
                    }
                    SceneState.PREVIEW -> {
                        binding.root.background = settingsData.previewColor.toDrawable()
                        binding.textState.text = settingsData.previewText
                    }
                    SceneState.OFF -> {
                        binding.root.background = settingsData.offColor.toDrawable()
                        binding.textState.text = settingsData.offText
                    }
                    else -> {
                        binding.root.background =
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.error_screen_background
                            ).toDrawable()
                        binding.textState.text = getString(R.string.error)
                    }
                }
            }
        }
        
        return binding.root
    }
    
}