package com.example.fastcode.module.postLogin.home.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fastcode.R
import com.example.fastcode.adapter.HistoryRecycleViewAdapter
import com.example.fastcode.bean.HistoryBean
import com.example.fastcode.databinding.ActivityHistoryBinding
import com.example.fastcode.module.preLogin.login.BASE_URL
import com.example.fastcode.module.preLogin.login.okHttpClient
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_history)
        initPager()


    }

    private fun initPager() {
        binding.rvHistory.layoutManager = LinearLayoutManager(this)
        var data = getDataList()
        binding.rvHistory.adapter = HistoryRecycleViewAdapter(data, this)

    }

    private fun getDataList(): List<String> {

        var body:RequestBody = FormBody.Builder().build()
        val request =
            Request.Builder().url("${BASE_URL}read_all_history").post(body)
                .build()

        var stringList: List<String> = listOf()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call, response: Response) {
                val historyBean =
                    Gson().fromJson<HistoryBean>(response.body()?.string(), HistoryBean::class.java)
                stringList = historyBean.data.split(",")
            }

        })
        return stringList
    }
}