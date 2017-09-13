package com.corey.demo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by sycao on 2017/8/22.
 * Kotlin main activity
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_main_read.setOnClickListener({
            toast(this, "read...")
        })

        btn_main_save.setOnClickListener({
            toast(this, "save...")
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        })
    }
}