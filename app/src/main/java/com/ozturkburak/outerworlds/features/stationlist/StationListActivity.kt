package com.ozturkburak.outerworlds.features.stationlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ozturkburak.outerworlds.databinding.ActivityStationListBinding
import org.koin.android.viewmodel.ext.android.viewModel

class StationListActivity : AppCompatActivity() {

    private val viewModel: StationListViewModel by viewModel()
    private lateinit var binding: ActivityStationListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityStationListBinding.inflate(layoutInflater).run {
            binding = this
            lifecycleOwner = this@StationListActivity
            viewModel = this@StationListActivity.viewModel
            setContentView(root)
        }
    }
}