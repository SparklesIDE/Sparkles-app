package com.sparkleside.ui.base

import android.os.Bundle

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity(){

        override fun onCreate(saved: Bundle?) {
                super.onCreate(saved)
                enableEdgeToEdge()
        }
}
