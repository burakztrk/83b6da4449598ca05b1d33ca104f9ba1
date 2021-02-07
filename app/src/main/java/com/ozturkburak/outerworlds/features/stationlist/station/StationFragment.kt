package com.ozturkburak.outerworlds.features.stationlist.station

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ozturkburak.outerworlds.R
import com.ozturkburak.outerworlds.base.observeLiveData
import com.ozturkburak.outerworlds.databinding.StationFragmentBinding
import com.ozturkburak.outerworlds.features.stationlist.StationListViewModel
import com.ozturkburak.outerworlds.features.stationlist.station.list.AdapterClickHandler
import com.ozturkburak.outerworlds.features.stationlist.station.list.ClickType
import com.ozturkburak.outerworlds.features.stationlist.station.list.SliderAdapter
import com.ozturkburak.outerworlds.features.stationlist.station.list.StationItemData
import com.ozturkburak.outerworlds.repo.Resource
import com.yarolegovich.discretescrollview.transform.ScaleTransformer


class StationFragment : Fragment(), AdapterClickHandler {

    companion object {
        fun newInstance() = StationFragment()
    }

    private val viewModel: StationListViewModel by activityViewModels()

    private lateinit var binding: StationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = DataBindingUtil.inflate<StationFragmentBinding>(
        inflater, R.layout.station_fragment, container, false
    ).apply {
        binding = this
        lifecycleOwner = viewLifecycleOwner
        binding.viewModel = this@StationFragment.viewModel
    }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
    }

    private fun observeViewModel() {
        observeLiveData(viewModel.adapterLiveData) {
            if (it is Resource.Success) {
                initStationsPicker(it.data)
                initSearchView(it.data)
            }
        }
    }

    private fun initStationsPicker(list: List<StationItemData>) {
        binding.discreteScroll.apply {
            adapter = SliderAdapter(list, this@StationFragment)

            list.indexOfFirst { it.data.currentStation }?.let {
                scrollToPosition(it)
            }


            setItemTransformer(
                ScaleTransformer.Builder()
                    .setMinScale(0.94f)
                    .build()
            )
        }
    }

    private fun initSearchView(list: List<StationItemData>) {
        binding.autoCompleteSearch.apply {
            setAdapter(
                ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    list.map { it.name }
                )
            )

            setOnItemClickListener { _, _, position, _ ->
                binding.discreteScroll.smoothScrollToPosition(position)
            }
        }
    }

    override fun onClick(type: ClickType, data: StationItemData) {
        when (type) {
            ClickType.BUTTON -> viewModel.onStationButtonClick(data)
            ClickType.FAV -> viewModel.onStationFavoriteClick(data)
        }
    }

}