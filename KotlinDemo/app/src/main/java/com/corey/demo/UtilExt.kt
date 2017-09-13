package com.corey.demo

import android.app.Activity
import android.content.Context
import android.widget.Toast

/**
 * Created by sycao on 2017/8/24.
 * 函数拓展
 */

// 函数拓展
fun Activity.toast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

// 包级函数
fun sayHello(context: Context, hello: String) {
    Toast.makeText(context, hello, Toast.LENGTH_LONG).show()
}