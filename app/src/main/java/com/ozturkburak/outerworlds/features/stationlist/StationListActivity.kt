package com.ozturkburak.outerworlds.features.stationlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.google.android.material.snackbar.Snackbar
import com.ozturkburak.outerworlds.R
import com.ozturkburak.outerworlds.base.observeLiveData
import com.ozturkburak.outerworlds.databinding.ActivityStationListBinding
import com.ozturkburak.outerworlds.features.stationlist.favorites.FavoriteFragment
import com.ozturkburak.outerworlds.features.stationlist.station.StationFragment
import com.ozturkburak.outerworlds.repo.Resource
import org.koin.android.viewmodel.ext.android.viewModel

class StationListActivity : AppCompatActivity() {

    private val viewModel: StationListViewModel by viewModel()
    private lateinit var binding: ActivityStationListBinding

    private lateinit var loadingDialog: MaterialDialog

    private val stationFragment = StationFragment.newInstance()
    private val favFragment = FavoriteFragment.newInstance()
    private var activeFragment: Fragment = stationFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityStationListBinding.inflate(layoutInflater).run {
            binding = this
            lifecycleOwner = this@StationListActivity
            viewModel = this@StationListActivity.viewModel
            setContentView(root)
        }

        initUI()
        observeViewModel()
    }

    private fun initUI() {
        loadingDialog = MaterialDialog(this)
            .noAutoDismiss()
            .cornerRadius(literalDp = 16f)

        supportFragmentManager.beginTransaction().apply {
            add(binding.fragment.id, favFragment, favFragment.tag).hide(favFragment)
            add(binding.fragment.id, stationFragment, stationFragment.tag)
        }.commit()

        initBottomNav()
    }

    private fun initBottomNav() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            val newFragment = when (it.itemId) {
                R.id.stationList -> stationFragment
                R.id.favorites -> favFragment
                else -> return@setOnNavigationItemSelectedListener false
            }

            supportFragmentManager.beginTransaction()
                .hide(activeFragment)
                .show(newFragment)
                .commit()
            activeFragment = newFragment

            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun observeViewModel() {
        observeLiveData(viewModel.adapterLiveData) {
            when (it) {
                is Resource.Loading -> showLoading(true)
                is Resource.Error -> {
                    showLoading(false)
                    showError(it.message)
                }
                is Resource.Success -> showLoading(false)
            }
        }
    }

    private fun showLoading(visibility: Boolean) {
        if (visibility) {
            loadingDialog.show {
                customView(R.layout.dialog_loading)
            }
        } else {
            loadingDialog.dismiss()
        }
    }

    private fun showError(message: String?) {
        message?.let {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        }
    }
}