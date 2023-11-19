package com.example.fastcode.module.postLogin.home.advancedCalculater

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ACViewModel : ViewModel() {
    @SuppressLint("StaticFieldLeak")
    val act = AdvancedCalculatorActivity()
    var input: MutableLiveData<String> = MutableLiveData("")
    var output = MutableLiveData<String>()

}