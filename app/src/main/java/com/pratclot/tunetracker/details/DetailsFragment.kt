package com.pratclot.tunetracker.details

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pratclot.tunetracker.R
import com.pratclot.tunetracker.database.TuneDatabase
import com.pratclot.tunetracker.databinding.FragmentDetailsBinding
import com.pratclot.tunetracker.repository.TuneRepository
import java.io.File

class DetailsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentDetailsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_details,
            container,
            false
        )
        val application = requireNotNull(this.activity).application
        val arguments = DetailsFragmentArgs.fromBundle(requireArguments())
        val database = TuneDatabase.getInstance(application)
        val tuneRepository = TuneRepository(database, application)
        val viewModelFactory = DetailsViewModelFactory(arguments.id, tuneRepository, application)
        val detailsViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(DetailsViewModel::class.java)
        binding.detailsViewModel = detailsViewModel

        detailsViewModel.tune.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.tuneTabView.fromFile(File(it.tabLocalUrl)).load()
            }
        })

        return binding.root
    }

}
