package com.corey.demo

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast

/**
 * Created by sycao on 2017/8/22.
 * Kotlin main activity
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btn_main_hello).setOnClickListener {
            Toast.makeText(this, "makeText...", Toast.LENGTH_LONG).show()
            sayHello(this, "sayHello...")
            toast(this, "toast...")
        }
    }
}

// 包级函数
fun sayHello(context: Context, hello: String) {
    Toast.makeText(context, hello, Toast.LENGTH_LONG).show()
}

// 函数拓展
fun MainActivity.toast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}