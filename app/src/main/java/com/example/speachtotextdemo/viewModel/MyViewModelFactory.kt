package com.example.speachtotextdemo.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.speachtotextdemo.reposotory.Reposotory

class MyViewModelFactory(val context: Context, val reposotory: Reposotory) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return ActivityViewModel(context, reposotory) as T
    }
}