package com.example.fastcode.module.postLogin.home.history

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fastcode.R
import com.example.fastcode.VolleyCallback
import com.example.fastcode.adapter.HistoryRecycleViewAdapter
import com.example.fastcode.adapter.MyListViewAdapter
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
import java.lang.Exception

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_history)
        initPager()


    }

    override fun onResume() {
        super.onResume()
        println("onresume")
        initPager()

    }

    @SuppressLint("SuspiciousIndentation")
    private fun initPager() {
            getDataList(context = applicationContext,object : VolleyCallback {
            override fun onSuccess(result: String?) {
//                println(result)
            }

            override fun onSuccess(result: List<String>) {
                var data: MutableList<String> = mutableListOf()
                for (i in result) {
                    data.add(i)
                }
                data.reverse()
                data.removeAt(0)
                applicationContext.run { runOnUiThread {
                    binding.lvHistory.adapter= MyListViewAdapter(this@HistoryActivity, data)
                } }

            }


        })

//        binding.lvHistory.adapter = MyListViewAdapter(applicationContext, listOf("c"))

    }

    private fun getDataList(context: Context,callback: VolleyCallback) {

        var body:RequestBody = FormBody.Builder().build()
        val request =
            Request.Builder().url("${BASE_URL}read_all_history").post(body)
                .build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call, response: Response) {
                val string = response.body()?.string()
                println(string)
                try {
                    val historyBean =
                        Gson().fromJson<HistoryBean>(string, HistoryBean::class.java)
                    callback.onSuccess(historyBean.result.split(","))
                } catch (e: Exception) {

                }

            }

        })

    }
}