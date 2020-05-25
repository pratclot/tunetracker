package com.pratclot.tunetracker.overview

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.pratclot.tunetracker.domain.Tune

@BindingAdapter("setTuneName")
fun TextView.setTuneName(tune: Tune) {
    tune.let {
        text = tune.name
    }
}

@BindingAdapter("setProgress")
fun ProgressBar.setProgress(tune: Tune) {
    tune.let {
        progress = it.progress
        if (progress == 100) {
            visibility = View.GONE
        } else {
            visibility = View.VISIBLE
        }
    }
}
