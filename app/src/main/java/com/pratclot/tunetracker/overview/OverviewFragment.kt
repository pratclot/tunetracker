package com.pratclot.tunetracker.overview

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pratclot.tunetracker.R
import com.pratclot.tunetracker.databinding.FragmentOverviewBinding
import com.pratclot.tunetracker.domain.Tune
import com.pratclot.tunetracker.overview.OverviewViewModel.Companion.downloadCounter
import com.pratclot.tunetracker.overview.OverviewViewModel.Companion.tuneProgresses
import com.pratclot.tunetracker.overview.OverviewViewModel.Companion.updateTuneOnDownloadFinish
import com.pratclot.tunetracker.ui.MainActivity
import javax.inject.Inject
import kotlinx.android.synthetic.main.add_tune_view.*

class OverviewFragment : Fragment() {

    @Inject
    lateinit var overviewViewModelFactory: OverviewViewModelFactory

    private val overviewViewModel by viewModels<OverviewViewModel> { overviewViewModelFactory }

    private lateinit var adapter: OverviewViewModelAdapter

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
        val manager = LinearLayoutManager(application)
        binding.tuneView.layoutManager = manager
        adapter = OverviewViewModelAdapter(
            TuneListener {
                overviewViewModel.onTuneClicked(it)
            },
            TuneListener {
                overviewViewModel.reloadTune(it!!)
            }
        )
        binding.tuneView.adapter = adapter
        binding.lifecycleOwner = this
        overviewViewModel.tunes.observe(viewLifecycleOwner, Observer {
//            Each time the tunes are updated in the db we need to have most recent list of them
//            in progresses collection
            it?.let {
                overviewViewModel.updateTuneProgresses(it).also {
                    submitList(it)
                }
            }
        })

        overviewViewModel.navigateToTuneDetails.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController()
                    .navigate(OverviewFragmentDirections.actionOverviewFragmentToDetailsFragment(it))
                overviewViewModel.onDetailsNavigationEnded()
            }
        })

        overviewViewModel.addTuneDialogOpened.observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it) {
                    true -> showAddTuneDialog()
                }
            }
        })

        downloadCounter.observe(viewLifecycleOwner, Observer {
            if (downloadCounter.value != 0) {
                overviewViewModel.tunes.value?.map {
                    it.progress = tuneProgresses[it.id]!!
                    it.copy()
                }?.let {
                    submitList(it)
                }
            }
        })

        updateTuneOnDownloadFinish.observe(viewLifecycleOwner, Observer {
            if (it != -1L) {
                overviewViewModel.downloadFinishedFor(it)
            }
        })

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity is MainActivity) {
            (activity as MainActivity).overviewComponent.inject(this)
        }
    }

    private fun submitList(list: List<Tune>) {
        adapter.submitList(list)
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
            }
            R.id.add_tune -> {
                overviewViewModel.markAddTuneDialogAsOpened()
                true
            }
            else -> false
        }

    private fun showAddTuneDialog() {
        MaterialAlertDialogBuilder(requireContext()).apply {
            setTitle(R.string.add_tune_dialog_title)
            setView(R.layout.add_tune_view)
            setPositiveButton(
                resources.getString(R.string.add_tune_button_text)
            ) { dialog, _ ->
                val name: String
                val url: String
                ((dialog as AlertDialog).add_tune_tunename.text.toString()).let {
                    when (it.length) {
                        0 -> name = resources.getString(R.string.sample_tune_name)
                        else -> name = it
                    }
                }
                (dialog.add_tune_pdf_url.text.toString()).let {
                    when (it.length) {
                        0 -> url = resources.getString(R.string.sample_tune_link_to_pdf)
                        else -> url = it
                    }
                }
                overviewViewModel.onAddTuneThruDialog(name, url)
                overviewViewModel.markAddTuneDialogAsClosed()
                dialog.dismiss()
            }
            setOnDismissListener() { _ ->
                overviewViewModel.markAddTuneDialogAsClosed()
            }
            show()
        }
    }
}
