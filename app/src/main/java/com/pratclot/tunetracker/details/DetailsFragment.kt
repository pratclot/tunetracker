package com.pratclot.tunetracker.details

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.pratclot.tunetracker.R
import com.pratclot.tunetracker.database.TuneDatabase
import com.pratclot.tunetracker.databinding.FragmentDetailsBinding
import com.pratclot.tunetracker.repository.TuneRepository

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
//        The following is ridiculously ugly, but is exactly what is tought in Android Kotlin Course
        val application = requireNotNull(this.activity).application
        val database = TuneDatabase.getInstance(application)
        val tuneRepository = TuneRepository(database)
        val viewModelFactory = DetailsViewModelFactory(tuneRepository, application)
        val detailsViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(DetailsViewModel::class.java)
        binding.detailsViewModel = detailsViewModel
//        End of the ugliness

        binding.tuneTabView.apply {
            loadUrl(
                "https://docs.google.com/gview?embedded=true&url="
                        + binding.tuneLinkToPdf.text.toString()
            )
            isVerticalScrollBarEnabled = true
        }

        return binding.root
    }

}
