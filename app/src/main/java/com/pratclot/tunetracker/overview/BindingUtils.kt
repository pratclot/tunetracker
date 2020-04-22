package com.pratclot.tunetracker.overview

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.pratclot.tunetracker.database.Tune

@BindingAdapter("setTuneName")
fun TextView.setTuneName(tune: Tune?) {
    tune?.let {
        text = tune.tuneName
    }
}
