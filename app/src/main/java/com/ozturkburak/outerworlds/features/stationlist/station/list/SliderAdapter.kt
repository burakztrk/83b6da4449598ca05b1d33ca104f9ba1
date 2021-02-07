package com.ozturkburak.outerworlds.features.stationlist.station.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ozturkburak.outerworlds.databinding.ItemStationBinding
import java.util.*

enum class ClickType{
    BUTTON,FAV
}

interface AdapterClickHandler {
    fun onClick(type: ClickType, data: StationItemData)
}

class SliderAdapter(
    private val stationList: List<StationItemData>,
    private val clickHandler: AdapterClickHandler
) : RecyclerView.Adapter<SliderAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStationBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(stationList[position])

    override fun getItemCount() = stationList.size

    inner class ViewHolder(val binding: ItemStationBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(viewData: StationItemData) {
            binding.viewData = viewData
            binding.buttonContinue.setOnClickListener {
                clickHandler.onClick(ClickType.BUTTON, viewData)
            }

            binding.favorite.setOnClickListener {
                clickHandler.onClick(ClickType.FAV, viewData)
            }

            binding.executePendingBindings()
        }

    }
}