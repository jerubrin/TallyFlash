package com.jerubrin.tallyflash.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jerubrin.tallyflash.entity.Scene
import com.jerubrin.tallyflash.databinding.InputHolderBinding

class InputsListAdapter(
    private val callback: (Scene) -> Unit
) : ListAdapter<Scene, InputsListAdapter.SceneHolder>(COMPARATOR) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SceneHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = InputHolderBinding.inflate(inflater, parent, false)
        return SceneHolder(binding)
    }
    
    override fun onBindViewHolder(holder: SceneHolder, position: Int) {
        val current = getItem(holder.adapterPosition)
        holder.bind(current, callback)
    }
    
    
    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<Scene>() {
            override fun areItemsTheSame(oldItem: Scene, newItem: Scene): Boolean =
                oldItem.key == newItem.key
        
            override fun areContentsTheSame(oldItem: Scene, newItem: Scene): Boolean =
                oldItem == newItem
        
            override fun getChangePayload(oldItem: Scene, newItem: Scene) = Any()
        }
    }
    
    inner class SceneHolder(
        private val binding: InputHolderBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(scene: Scene, callback: (Scene) -> Unit) {
            binding.name.text = scene.shortTitle
            binding.number.text = scene.number.toString()
            binding.type.text = scene.type
            binding.root.setOnClickListener { callback(scene) }
        }
    }
}