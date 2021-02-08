package com.ozturkburak.outerworlds.ui.stationlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ozturkburak.outerworlds.R
import com.ozturkburak.outerworlds.data.entity.StationItemData
import com.ozturkburak.outerworlds.databinding.ItemFavoriteBinding
import com.ozturkburak.outerworlds.databinding.ItemStationBinding

enum class ClickType {
    BUTTON, FAV
}

interface AdapterClickHandler {
    fun onClick(type: ClickType, data: StationItemData)
}

class StationAdapter(
    private val type: Type,
    private val stationList: List<StationItemData>,
    private val clickHandler: AdapterClickHandler
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class Type {
        SLIDER, FAVORITE
    }

    override fun getItemCount() = stationList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (type) {
            Type.SLIDER -> SliderViewHolder(ItemStationBinding.inflate(inflater))
            Type.FAVORITE -> FavoriteViewHolder(ItemFavoriteBinding.inflate(inflater))
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (type) {
            Type.SLIDER -> (holder as? SliderViewHolder)?.bind(stationList[position])
            Type.FAVORITE -> (holder as? FavoriteViewHolder)?.bind(stationList[position])
        }
    }


    inner class SliderViewHolder(private val binding: ItemStationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewData: StationItemData) {
            binding.viewData = viewData
            binding.buttonContinue.setOnClickListener {
                clickHandler.onClick(ClickType.BUTTON, viewData)
            }

            binding.favorite.setOnClickListener {
                clickHandler.onClick(ClickType.FAV, viewData)
            }

            if (viewData.data.isFavorite) {
                binding.favorite.setColorFilter(
                    ContextCompat.getColor(
                        binding.favorite.context,
                        R.color.app_color
                    ), android.graphics.PorterDuff.Mode.SRC_IN
                );
            }

            binding.executePendingBindings()
        }

    }

    inner class FavoriteViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewData: StationItemData) {
            binding.viewData = viewData
            binding.root.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
            binding.favorite.setOnClickListener {
                clickHandler.onClick(ClickType.FAV, viewData)
            }

            if (viewData.data.isFavorite) {
                binding.favorite.setColorFilter(
                    ContextCompat.getColor(
                        binding.favorite.context,
                        R.color.app_color
                    ), android.graphics.PorterDuff.Mode.SRC_IN
                )
            }
            binding.executePendingBindings()
        }
    }
}