package com.example.newsapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.app.data.remote.Constants.PrefKeys.LANG
import com.patient.base.MyContextWrapper
import com.patient.data.cashe.PreferencesGateway
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var preferenc: PreferencesGateway

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun attachBaseContext(newBase: Context?) {
        preferenc = newBase?.let { PreferencesGateway(it) }!!
        val localeToSwitchTo = preferenc.load(LANG,"")
        val localeUpdatedContext = localeToSwitchTo?.let { MyContextWrapper.wrap(newBase, it) }
        super.attachBaseContext(localeUpdatedContext)
    }
}