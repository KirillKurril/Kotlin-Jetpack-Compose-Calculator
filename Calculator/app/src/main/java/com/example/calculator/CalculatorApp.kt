package com.example.calculator

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CalculatorApp : Application(){
    override fun onCreate() {
        super.onCreate()
    }
}
