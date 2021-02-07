package com.ozturkburak.outerworlds.features.stationlist.station

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ozturkburak.outerworlds.R
import com.ozturkburak.outerworlds.databinding.StationFragmentBinding
import com.ozturkburak.outerworlds.features.stationlist.station.list.SliderAdapter
import com.ozturkburak.outerworlds.features.stationlist.station.list.StationItemData
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import org.koin.android.viewmodel.ext.android.viewModel


class StationFragment : Fragment() {

    companion object {
        fun newInstance() = StationFragment()
    }

    private val viewModel: StationViewModel by viewModel()

    private lateinit var binding: StationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = DataBindingUtil.inflate<StationFragmentBinding>(
        inflater,
        R.layout.station_fragment,
        container,
        false
    ).apply {
        binding = this
        lifecycleOwner = viewLifecycleOwner
        binding.viewModel = this@StationFragment.viewModel
    }.root


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.discreteScroll.apply {
            adapter = InfiniteScrollAdapter.wrap(
                SliderAdapter(
                    listOf(
                        StationItemData("1", "A"),
                        StationItemData("2", "A"),
                        StationItemData("3", "A"),
                        StationItemData("4", "A"),
                    )
                )
            )
            setItemTransformer(
                ScaleTransformer.Builder()
                    .setMinScale(0.94f)
                    .build()
            )
        }



    }

}