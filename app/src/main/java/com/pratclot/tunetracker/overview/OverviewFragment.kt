package com.pratclot.tunetracker.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pratclot.tunetracker.R
import com.pratclot.tunetracker.database.TuneDatabase
import com.pratclot.tunetracker.databinding.FragmentOverviewBinding
import com.pratclot.tunetracker.repository.TuneRepository

class OverviewFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentOverviewBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_overview,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val database = TuneDatabase.getInstance(application)
        val tuneRepository = TuneRepository(database, application)
        val viewModelFactory = OverviewViewModelFactory(application, tuneRepository)
        val overviewViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(OverviewViewModel::class.java)
        binding.overviewViewModel = overviewViewModel
        val manager = LinearLayoutManager(application)
        binding.tuneView.layoutManager = manager
        val adapter = OverviewViewModelAdapter(TuneListener {
            overviewViewModel.onTuneClicked(it)
        })
        binding.tuneView.adapter = adapter
        binding.lifecycleOwner = this
        overviewViewModel.tunes.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        overviewViewModel.navigateToTuneDetails.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController()
                    .navigate(OverviewFragmentDirections.actionOverviewFragmentToDetailsFragment(it))
                overviewViewModel.onDetailsNavigationEnded()
            }
        })

        return binding.root
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.clear_database -> {
//                overviewViewModel.onClear()
//                true
//            }
//        }
//    }
}
