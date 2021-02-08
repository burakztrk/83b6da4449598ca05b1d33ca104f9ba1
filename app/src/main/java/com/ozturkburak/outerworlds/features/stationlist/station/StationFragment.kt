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
import com.ozturkburak.outerworlds.base.Task
import com.ozturkburak.outerworlds.base.observeLiveData
import com.ozturkburak.outerworlds.databinding.StationFragmentBinding
import com.ozturkburak.outerworlds.features.stationlist.StationListViewModel
import com.ozturkburak.outerworlds.features.stationlist.station.list.*
import com.ozturkburak.outerworlds.features.stationlist.station.list.StationAdapter.Type
import com.ozturkburak.outerworlds.repo.Resource
import com.yarolegovich.discretescrollview.transform.ScaleTransformer


class StationFragment : Fragment(), AdapterClickHandler {

    companion object {
        fun newInstance() = StationFragment()
    }

    private val viewModel: StationListViewModel by activityViewModels()

    private lateinit var binding: StationFragmentBinding

    private lateinit var infoBottomSheet: InfoBottomSheet

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

        initUI()
        observeViewModel()
    }

    private fun initUI() {
        infoBottomSheet = InfoBottomSheet(requireContext(), Task {
            binding.discreteScroll.smoothScrollToPosition(0)
            viewModel.startTimer()
        })
    }

    private fun observeViewModel() {
        observeLiveData(viewModel.sliderAdapterLiveData) {
            if (it is Resource.Success) {
                initStationsPicker(it.data, it.selectedPosition)
                initSearchView(it.data)
            }
        }
        observeLiveData(viewModel.finishGameLiveData) {
            infoBottomSheet.show(it)
        }
    }

    private fun initStationsPicker(list: List<StationItemData>, selectedStationPos: Int?) {
        binding.discreteScroll.apply {
            adapter = StationAdapter(Type.SLIDER, list, this@StationFragment)

            selectedStationPos?.let {
                scrollToPosition(selectedStationPos)
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