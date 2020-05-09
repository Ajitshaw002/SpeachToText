package com.example.speachtotextdemo.util

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast

fun Context.toast(str: String) {
    Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
}

fun ProgressBar.showLoding() {
    visibility = View.VISIBLE
}

fun ProgressBar.hideLoding() {
    visibility = View.GONE
}