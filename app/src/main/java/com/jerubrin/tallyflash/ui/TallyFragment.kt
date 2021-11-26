package com.jerubrin.tallyflash.ui

import android.content.Intent
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
import com.jerubrin.tallyflash.flashlight.FlashControlService
import com.jerubrin.tallyflash.ui.vm.TallyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TallyFragment : Fragment() {
    
    private val viewModel: TallyViewModel by activityViewModels()
    
    private val args by navArgs<TallyFragmentArgs>()
    
    private var _binding : FragmentTallyBinding? = null
    private val binding get() = _binding !!
    
    @Inject
    lateinit var serviceIntent: Intent
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.currentScene.value = args.scene
        }
    
        serviceIntent.putExtra(FlashControlService.CURRENT_SCENE_NUMBER, args.scene.number)
        activity?.application?.startService(serviceIntent)
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTallyBinding.inflate(inflater, container, false)
        
        lifecycleScope.launch { viewModel.name.collectLatest { binding.textViewName.text = it } }
        lifecycleScope.launch { viewModel.number.collectLatest { binding.textViewNumber.text = it } }
        
        lifecycleScope.launch {
            viewModel.sceneState.collectLatest {
                when(it) {
                    TallyViewModel.SceneState.ACTIVE -> {
                        binding.root.setBackgroundColor(viewModel.sharedPrefMain.getActiveColor())
                        binding.textState.text = viewModel.sharedPrefMain.getActiveText()
                    }
                    TallyViewModel.SceneState.PREVIEW -> {
                        binding.root.setBackgroundColor(viewModel.sharedPrefMain.getPreviewColor())
                        binding.textState.text = viewModel.sharedPrefMain.getPreviewText()
                    }
                    TallyViewModel.SceneState.OFF -> {
                        binding.root.setBackgroundColor(viewModel.sharedPrefMain.getOffColor())
                        binding.textState.text = viewModel.sharedPrefMain.getOffText()
                    }
                    else -> {
                        binding.root.setBackgroundColor(
                            ContextCompat.getColor(requireContext(), R.color.error_background)
                        )
                        binding.textState.text = it.state
                    }
                }
            }
        }
        
        return binding.root
    }
    
    override fun onDestroyView() {
        serviceIntent.putExtra(FlashControlService.CURRENT_SCENE_NUMBER, -1)
        activity?.application?.startService(serviceIntent)
        super.onDestroyView()
    }
}