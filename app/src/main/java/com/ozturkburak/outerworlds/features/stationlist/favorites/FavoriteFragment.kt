package com.ozturkburak.outerworlds.features.stationlist.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ozturkburak.outerworlds.R
import com.ozturkburak.outerworlds.databinding.FavoriteFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {

    companion object {
        fun newInstance() = FavoriteFragment()
    }

    private val viewModel: FavoriteViewModel by viewModel()

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
}