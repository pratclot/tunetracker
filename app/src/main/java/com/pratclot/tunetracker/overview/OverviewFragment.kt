package com.pratclot.tunetracker.overview

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pratclot.tunetracker.R
import com.pratclot.tunetracker.TuneTracker
import com.pratclot.tunetracker.database.TuneDatabase
import com.pratclot.tunetracker.databinding.FragmentOverviewBinding
import com.pratclot.tunetracker.repository.TuneRepository
import com.pratclot.tunetracker.ui.MainActivity
import javax.inject.Inject

class OverviewFragment : Fragment() {

    @Inject
    lateinit var overviewViewModelFactory: OverviewViewModelFactory

    private val overviewViewModel by viewModels<OverviewViewModel> { overviewViewModelFactory }

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

        setHasOptionsMenu(true)

        val application = requireNotNull(this.activity).application
//        val database = TuneDatabase.getInstance(application)
//        val tuneRepository = TuneRepository(database, application)
//        val viewModelFactory = OverviewViewModelFactory(application, tuneRepository)
//        overviewViewModel =
//            ViewModelProviders.of(
//                this, viewModelFactory
//            ).get(OverviewViewModel::class.java)
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).overviewComponent.inject(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overview_actions, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.clear_database -> {
                overviewViewModel.onClear()
                true
            } else -> false
        }
}
