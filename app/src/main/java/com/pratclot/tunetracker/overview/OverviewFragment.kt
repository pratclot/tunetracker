package com.pratclot.tunetracker.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.pratclot.tunetracker.R
import com.pratclot.tunetracker.database.TuneDatabase
import com.pratclot.tunetracker.databinding.FragmentOverviewBinding

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

//        The following is ridiculously ugly, but is exactly what is tought in Android Kotlin Course
        val application = requireNotNull(this.activity).application
        val dataSource = TuneDatabase.getInstance(application).tuneDatabaseDao
        val viewModelFactory = OverviewViewModelFactory(dataSource, application)
        val overviewViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(OverviewViewModel::class.java)
        binding.overviewViewModel = overviewViewModel
        val manager = LinearLayoutManager(application)
        binding.tuneView.layoutManager = manager
        val adapter = OverviewViewModelAdapter()
        binding.tuneView.adapter = adapter
        binding.lifecycleOwner = this
        overviewViewModel.tunes.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
//        End of the ugliness

        return binding.root
    }
}
