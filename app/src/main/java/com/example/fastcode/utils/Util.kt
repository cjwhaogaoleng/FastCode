package com.example.fastcode.utils

import android.content.Context
import android.widget.Toast


object Util {
        fun toast(context: Context, s: String?) {
            Toast.makeText(context, s, Toast.LENGTH_LONG).show()
    }
}