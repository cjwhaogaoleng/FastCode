package com.example.fastcode.module.preLogin.register

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.fastcode.R
import com.example.fastcode.VolleyCallback
import com.example.fastcode.bean.HistoryBean
import com.example.fastcode.bean.UserBean
import com.example.fastcode.databinding.ActivityRegisterBinding
import com.example.fastcode.module.preLogin.login.BASE_URL
import com.example.fastcode.module.preLogin.login.okHttpClient
import com.example.fastcode.utils.Util.toast
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.clickPresenter = this

    }

    fun clickPresenter(view: View) {
        var username = binding.registerUsername.text.toString()
        var password1 = binding.registerPassword.text.toString()
        var password2 = binding.registerPasswordAgain.text.toString()
         register(username,password1,password2, context = this, callback = object : VolleyCallback {
                override fun onSuccess(result: String?) {
                    if (result == "success") {
                        toast(this@RegisterActivity, "注册成功")
                        this@RegisterActivity.finish()
                    } else {
                        toast(this@RegisterActivity, "注册失败")

                    }
                }

            })

    }

    private fun register(username: String, password1: String, password2: String,context: Context,callback:VolleyCallback)  {
        var body: RequestBody = FormBody.Builder()
            .add("username", username)
            .add("password1", password1)
            .add("password2",password2)
            .build()
        val request =
            Request.Builder().url("${BASE_URL}register").post(body)
                .build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val string = response.body()?.string()

                var userBean : UserBean?= null
                try {
                    userBean =
                        Gson().fromJson<UserBean>(string, UserBean::class.java)
                } catch (e: IOException) {

                }
                callback.onSuccess(userBean!!.message)
            }
        })
    }
    }
