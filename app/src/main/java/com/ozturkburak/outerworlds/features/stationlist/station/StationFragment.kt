package com.ozturkburak.outerworlds.features.stationlist.station

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ozturkburak.outerworlds.R

class StationFragment : Fragment() {

    companion object {
        fun newInstance() = StationFragment()
    }

    private lateinit var viewModel: StationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.station_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StationViewModel::class.java)
        // TODO: Use the ViewModel
    }

}