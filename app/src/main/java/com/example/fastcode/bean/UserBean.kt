package com.example.fastcode.bean

class UserBean {
     var message: String = ""
     var data: Data? = null
     var status: Int = 0

     override fun toString(): String {
          return "UserBean(message='$message', data=$data, status=$status)"
     }


}