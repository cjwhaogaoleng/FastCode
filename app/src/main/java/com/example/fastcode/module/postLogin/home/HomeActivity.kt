package com.example.fastcode.module.postLogin.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.fastcode.R
import com.example.fastcode.databinding.ActivityHomeBinding
import com.example.fastcode.databinding.ActivityRegisterBinding
import com.example.fastcode.module.postLogin.home.advancedCalculater.AdvancedCalculatorActivity
import com.example.fastcode.module.postLogin.home.history.HistoryActivity
import com.example.fastcode.module.postLogin.home.interestCalculation.InterestCalculationActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.clickPresenter = this
    }

    fun clickPresenter(view: View) {
        when (view.id) {
            R.id.iv_home_ac -> {
                startActivity(Intent(this,AdvancedCalculatorActivity::class.java))
            }

            R.id.iv_home_ic -> {
                startActivity(Intent(this,InterestCalculationActivity::class.java))

            }

            R.id.iv_home_his -> {
                startActivity(Intent(this,HistoryActivity::class.java))
            }

            R.id.iv_home_quit -> {
                this.finish()
            }
        }
    }
}