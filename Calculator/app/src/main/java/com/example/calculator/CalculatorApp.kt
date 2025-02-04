package com.example.calculator

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import com.google.firebase.FirebaseApp
//import com.google.firebase.crashlytics.FirebaseCrashlytics

@HiltAndroidApp
class CalculatorApp : Application(){
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this) 
        //FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
    }
}
