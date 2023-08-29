package com.example.tossapeso

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VmFactory(private val application: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TossViewModel::class.java)) {
            return TossViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
