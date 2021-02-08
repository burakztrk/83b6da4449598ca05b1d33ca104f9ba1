package com.ozturkburak.outerworlds.features.stationlist.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ozturkburak.outerworlds.R
import com.ozturkburak.outerworlds.base.observeLiveData
import com.ozturkburak.outerworlds.databinding.FavoriteFragmentBinding
import com.ozturkburak.outerworlds.features.stationlist.StationListViewModel
import com.ozturkburak.outerworlds.features.stationlist.station.list.AdapterClickHandler
import com.ozturkburak.outerworlds.features.stationlist.station.list.ClickType
import com.ozturkburak.outerworlds.features.stationlist.station.list.StationAdapter
import com.ozturkburak.outerworlds.features.stationlist.station.list.StationItemData
import com.ozturkburak.outerworlds.repo.Resource
import com.yarolegovich.discretescrollview.transform.ScaleTransformer

class FavoriteFragment : Fragment(), AdapterClickHandler {

    companion object {
        fun newInstance() = FavoriteFragment()
    }

    private val viewModel: StationListViewModel by activityViewModels()

    private lateinit var binding: FavoriteFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = DataBindingUtil.inflate<FavoriteFragmentBinding>(
        inflater,
        R.layout.favorite_fragment,
        container,
        false
    ).apply {
        binding = this
        lifecycleOwner = viewLifecycleOwner
        binding.viewModel = this@FavoriteFragment.viewModel
    }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        observeLiveData(viewModel.favoriteAdapterLiveData) {
            if (it is Resource.Success) {
                binding.list.adapter = StationAdapter(StationAdapter.Type.FAVORITE, it.data, this@FavoriteFragment)

            }
        }
    }

    override fun onClick(type: ClickType, data: StationItemData) {
        if (type == ClickType.FAV)
            viewModel.onStationFavoriteClick(data)
    }
}