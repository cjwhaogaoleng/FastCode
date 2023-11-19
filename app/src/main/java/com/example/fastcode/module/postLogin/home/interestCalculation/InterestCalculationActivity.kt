package com.example.fastcode.module.postLogin.home.interestCalculation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.fastcode.R
import com.example.fastcode.adapter.MyViewPagerAdapter
import com.example.fastcode.databinding.ActivityInterestCalculationBinding
import com.example.fastcode.databinding.ActivityRegisterBinding

class InterestCalculationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInterestCalculationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interest_calculation)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_interest_calculation)
        binding.lifecycleOwner = this

        initPager()
    }

    private fun initPager() {

        var loan = LoanFragment.newInstance()
        var deposit = DepositFragment.newInstance()
        var data = arrayListOf<Fragment>()
        data.add(loan)
        data.add(deposit)
        var myViewPagerAdapter = MyViewPagerAdapter(supportFragmentManager, lifecycle, data)
        binding.vp2Interest.adapter = myViewPagerAdapter

    }
}