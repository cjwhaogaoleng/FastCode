package com.example.fastcode.module.postLogin.home.advancedCalculater

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.fastcode.R
import com.example.fastcode.VolleyCallback
import com.example.fastcode.bean.CountBean
import com.example.fastcode.bean.HistoryBean
import com.example.fastcode.databinding.ActivityAdvancedCalculatorBinding
import com.example.fastcode.module.preLogin.login.BASE_URL
import com.example.fastcode.module.preLogin.login.okHttpClient
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
import java.lang.Exception

class AdvancedCalculatorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdvancedCalculatorBinding
    private lateinit var viewModel: ACViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_advanced_calculator)

        viewModel = ViewModelProvider(this).get(ACViewModel::class.java)

        binding.lifecycleOwner = this
        binding.kbClick = this
        binding.viewModle = viewModel

    }

    //是否可以点击运算符
//    private var canOpe = true

    //记录是否已经计算了结果
    private var calculateFlag = false

    //记录当前切换到哪组按键 初始为sin类
    private var _2ndFlag = false

    //记录当前使用角度还是弧度
    private var isDEG = true
    private var deg = "yes"

    //记录刚刚是否使用了有括号的算法
    private var isBracket = false

    private val TAG = "KeyboardFragment"

        fun click(view: View) {
            toast(this,"dian")

            try {
                //10
                when ((view as Button).text) {
                    binding.bt0.text, binding.bt1.text, binding.bt2.text,
                    binding.bt3.text, binding.bt4.text, binding.bt5.text,
                    binding.bt6.text, binding.bt7.text, binding.bt8.text,
                    binding.bt9.text, ".", "π", "e" -> {
                        if (isBracket) {
                            viewModel.input.value = viewModel.input.value?.substring(0, viewModel.input.value!!.length -1)
                            viewModel.input.value += (view).text
                            viewModel.input.value += ")"
                        } else viewModel.input.value += (view).text

//                        canOpe = true
                    }
                    //4
                    "+", "-", "×", "÷" -> {
                        //说明之前一直在输入数字
//                        if (canOpe) {
                            viewModel.input.value += (view).text
                            isBracket = false
//                            canOpe = false
//                        }
                    }

                    //3
                    binding.btEqual.text -> {
                        //更新计算结果
                        getResult(deg, object : VolleyCallback {
                            override fun onSuccess(result: String?) {
                                viewModel.input.postValue(result)
                            }

                            override fun onSuccess(result: List<String>) {
                                TODO("Not yet implemented")
                            }
                        })
                        calculateFlag = true

                    }

                    binding.btClear.text -> {
                        viewModel.input.value = ""
                    }

                    binding.btDelete.text -> {
                        viewModel.input.value = viewModel.input.value!!.substring(0, viewModel.input.value!!.length - 1)
                    }

                    //7
                    binding.btMod.text -> {
                        viewModel.input.value += "mod"
                    }

                    binding.btFactorial.text -> {
                        viewModel.input.value += "!"
                    }

                    binding.btReciprocal.text -> {
                        viewModel.input.value += "^-1"
                    }

                    "1/x" -> {
                        viewModel.input.value += "^-1"
                    }

                    "|x|" -> {
                        viewModel.input.value += "abs()"
                        isBracket = true
                    }

                    "(" -> {
                        viewModel.input.value += "("
                    }

                    ")" -> {
                        viewModel.input.value += ")"
                    }

                    "+/-" -> {
                        Toast.makeText(view.context, "no function", Toast.LENGTH_SHORT).show()

                    }
                    //2
                    //DEG角度制
                    //RAD弧度制
                    "DEG" -> {
                        binding.btDEG.text = "RAD"
                        deg = "no"
                        isDEG = false
                    }

                    "RAD" -> {
                        binding.btDEG.text = "DEG"
                        deg = "yes"
                        isDEG = true
                    }

                    //9
                    binding.bt2nd.text -> {
                        if (!_2ndFlag) {
                            binding.btSin.text = "sin"
                            binding.btCos.text = "cos"
                            binding.btTan.text = "tan"

                            binding.btSqrt.text = "2√x"
                            binding.btSquare.text = "x^2"
                            binding.btPower.text = "x^y"
                            binding.bt10Power.text = "10^x"
                            binding.btLog.text = "log"
                            binding.btLn.text = "ln"
                            _2ndFlag = true
                        } else {
                            binding.btSin.text = "asin"
                            binding.btCos.text = "acos"
                            binding.btTan.text = "atan"

                            binding.btSqrt.text = "3√x"
                            binding.btSquare.text = "x^3"
                            binding.btPower.text = "y√x"
                            binding.bt10Power.text = "2^x"
                            binding.btLog.text = "logy(x)"
                            binding.btLn.text = "e^x"

                            _2ndFlag = false
                        }
                    }

                    "sin" -> {
                        viewModel.input.value += "sin()"
                        isBracket = true
                    }

                    "cos" -> {
                        viewModel.input.value += "cos()"
                        isBracket = true
                    }

                    "tan" -> {
                        viewModel.input.value += "tan()"
                        isBracket = true

                    }

                    "asin" -> {
                        viewModel.input.value += "asin()"
                        isBracket = true
                    }

                    "acos" -> {
                        viewModel.input.value += "acos()"
                        isBracket = true

                    }

                    "atan" -> {
                        viewModel.input.value += "atan()"
                        isBracket = true

                    }

                    "2√x" -> {
                        viewModel.input.value += "^1/2"
                    }

                    "3√x" -> {
                        viewModel.input.value += "^1/3"
                    }

                    "x^2" -> {
                        viewModel.input.value += "^2"
                    }

                    "x^3" -> {
                        viewModel.input.value += "^3"

                    }

                    "x^y" -> {
                        viewModel.input.value += "^"
                    }

                    "y√x" -> {
                        viewModel.input.value += "√"
                    }

                    "10^x" -> {
                        viewModel.input.value += "10^"
//                        canOpe = false

                    }

                    "2^x" -> {
                        viewModel.input.value += "2^"
//                        canOpe = false

                    }

                    "log" -> {
                        viewModel.input.value += "log"
//                        canOpe = false
                    }

                    "logy(x)" -> {
                        viewModel.input.value += "log()"
//                        canOpe = false
                    }

                    "ln" -> {
                        viewModel.input.value += "ln"
//                        canOpe = false
                    }

                    "e^x" -> {
                        viewModel.input.value += "e^"
//                        canOpe = false
                    }

                    binding.btMove.text -> {

                    }
                }
            } catch (e: java.lang.Exception) {

            } finally {


//                if (calculateFlag) {
//                    viewModel.input.value = viewModel.output.value.toString()
//                    calculateFlag = false
//                }
            }



    }

    private fun getResult(deg: String, callback: VolleyCallback) {


        var body: RequestBody = FormBody.Builder()
            .add("formula", viewModel.input.value.toString())
            .add("deg", deg)
            .build()
        val request =
            Request.Builder().url("${BASE_URL}get_result").post(body)
                .build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val string = response.body()?.string()
                println(string)
                try {
                    val countBean =
                        Gson().fromJson(string, CountBean::class.java)
                    callback.onSuccess(countBean.result.toString())
                } catch (e: Exception) {

                }

            }

        })
    }
}
