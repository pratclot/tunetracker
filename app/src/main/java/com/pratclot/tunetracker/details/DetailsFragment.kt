package com.pratclot.tunetracker.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.pratclot.tunetracker.R
import com.pratclot.tunetracker.databinding.FragmentDetailsBinding
import com.pratclot.tunetracker.ui.MainActivity
import java.io.File
import javax.inject.Inject

class DetailsFragment : Fragment() {

    @Inject
    lateinit var factory: DetailsViewModel.Factory

    lateinit var arguments: DetailsFragmentArgs

    private lateinit var detailsViewModel: DetailsViewModel

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
        arguments = DetailsFragmentArgs.fromBundle(requireArguments())
        detailsViewModel = factory.create(arguments.id)
        binding.detailsViewModel = detailsViewModel
        binding.lifecycleOwner = this

        detailsViewModel.tune.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.tuneTabView.fromFile(File(it.tabLocalUrl)).load()
            }
        })

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity is MainActivity) {
            (activity as MainActivity).detailsComponent.inject(this)
        }
    }
}
