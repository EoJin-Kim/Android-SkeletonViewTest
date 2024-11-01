package com.ej.skeletonviewtest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ej.skeletonviewtest.databinding.Section1Binding
import com.ej.skeletonviewtest.databinding.Section2Binding
import com.ej.skeletonviewtest.databinding.Section3Binding
import com.ej.skeletonviewtest.databinding.Section4Binding

class SectionAdapter() : ListAdapter<SectionInfo, RecyclerView.ViewHolder>(diffUtil){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            SectionType.Section1.type -> {
                Section1ViewHolder(Section1Binding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            SectionType.Section2.type -> {
                Section2ViewHolder(Section2Binding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            SectionType.Section3.type -> {
                Section3ViewHolder(Section3Binding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            SectionType.Section4.type -> {
                Section4ViewHolder(Section4Binding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            else -> {
                Section1ViewHolder(Section1Binding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType){
            SectionType.Section1.type -> (holder as Section1ViewHolder).bind(getItem(position))
            SectionType.Section2.type -> (holder as Section2ViewHolder).bind(getItem(position))
            SectionType.Section3.type -> (holder as Section3ViewHolder).bind(getItem(position))
            SectionType.Section4.type -> (holder as Section4ViewHolder).bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type.type
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<SectionInfo>() {
            override fun areItemsTheSame(oldItem: SectionInfo, newItem: SectionInfo): Boolean {
                return oldItem.type == newItem.type
            }

            override fun areContentsTheSame(oldItem: SectionInfo, newItem: SectionInfo): Boolean {
                return oldItem == newItem
            }
        }
    }


    inner class Section1ViewHolder(private val binding: Section1Binding) : RecyclerView.ViewHolder(binding.root){

        fun bind(data : SectionInfo){
            val info = data as SectionInfo.Section1Info

            binding.skt.setLoading(info.loading)
        }
    }

    inner class Section2ViewHolder(private val binding: Section2Binding) : RecyclerView.ViewHolder(binding.root){

        fun bind(data : SectionInfo){
            val info = data as SectionInfo.Section2Info
        }
    }

    inner class Section3ViewHolder(private val binding: Section3Binding) : RecyclerView.ViewHolder(binding.root){

        fun bind(data : SectionInfo){
            val info = data as SectionInfo.Section3Info
        }
    }

    inner class Section4ViewHolder(private val binding: Section4Binding) : RecyclerView.ViewHolder(binding.root){

        fun bind(data : SectionInfo){
            val info = data as SectionInfo.Section4Info
        }
    }
}

sealed class SectionInfo(
    open val type: SectionType,
    open val loading : Boolean,
){
    data class Section1Info(
        override val type : SectionType = SectionType.Section1,
        override val loading: Boolean = true,

    ) : SectionInfo(type, loading)
    data class Section2Info(override val type : SectionType  = SectionType.Section2, override val loading: Boolean = true) : SectionInfo(type,loading)
    data class Section3Info(override val type : SectionType = SectionType.Section3, override val loading: Boolean = true) : SectionInfo(type, loading)
    data class Section4Info(override val type : SectionType = SectionType.Section4, override val loading: Boolean = true) : SectionInfo(type, loading)
}

enum class SectionType(val type: Int) {
    Unknown(0), Section1(1), Section2(2), Section3(3), Section4(4), Section5(5);
}