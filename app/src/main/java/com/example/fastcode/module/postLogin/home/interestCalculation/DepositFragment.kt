package com.example.fastcode.module.postLogin.home.interestCalculation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fastcode.R

class DepositFragment : Fragment() {

    companion object {
        fun newInstance() = DepositFragment()
    }

//    private lateinit var viewModel: sViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_deposit, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(sViewModel::class.java)
        // TODO: Use the ViewModel
    }

}