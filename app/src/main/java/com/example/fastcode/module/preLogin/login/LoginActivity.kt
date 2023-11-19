package com.example.fastcode.module.preLogin.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.fastcode.R
import com.example.fastcode.VolleyCallback
import com.example.fastcode.bean.UserBean
import com.example.fastcode.databinding.ActivityLoginBinding
import com.example.fastcode.module.postLogin.home.HomeActivity
import com.example.fastcode.module.preLogin.register.RegisterActivity
import com.example.fastcode.utils.Util.toast
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException

const val BASE_URL = "http://8.140.254.171:443/"
val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.clickPresenter = this


    }


    fun clickPresenter(view: View) {
        toast(this, "进行了点击")

        when (view.id) {
            binding.btLogin.id -> {
                var username = binding.loginUsername.text.toString()
                var password = binding.loginPassword.text.toString()

//                if (register(username,password)) {
//                    Util.toast(this, "登陆成功")
//                    startActivity(Intent(this,HomeActivity::class.java))
//                }
//                else Util.toast(this, "用户名或者密码错误")
                register(username, password, context = this, object : VolleyCallback {
                    override fun onSuccess(result: String?) {
                        if (result == "success") {
//                            toast(this@LoginActivity, "登陆成功")
                            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                        }
                        else {}
//                            toast(this@LoginActivity, "用户名或者密码错误")
                    }

                    override fun onSuccess(result: List<String>) {
                        TODO("Not yet implemented")
                    }

                })
            }
            binding.goToRegister.id -> {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
    }

    fun register(username: String, password: String, context: Context, callback: VolleyCallback) {
        var body: RequestBody = FormBody.Builder()
            .add("username", username)
            .add("password", password)
            .build()
        val request =
            Request.Builder().url("${BASE_URL}login").post(body)
                .build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val string = response.body()?.string()
                var userBean: UserBean? = null
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