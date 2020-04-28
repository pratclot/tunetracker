package com.pratclot.tunetracker.overview

import android.content.Context
import android.os.Bundle
import android.view.*
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
import com.pratclot.tunetracker.ui.MainActivity
import kotlinx.android.synthetic.main.add_tune_view.*
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
            }
            R.id.add_tune -> {
                showAddTuneDialog()
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
                dialog.dismiss()
            }
            show()
        }
    }
}
