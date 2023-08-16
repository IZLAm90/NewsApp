package com.patient.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.app.data.remote.Constants
import com.example.base.R
import com.example.base.databinding.ActivityBaseBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
abstract class BaseActivity(private val layoutResource: Int) : AppCompatActivity() {
    private var viewBase: ActivityBaseBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBase = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(viewBase?.root)
        val activityView = LayoutInflater.from(this)
            .inflate(layoutResource, viewBase?.flContent, false) as ViewGroup
        viewBase?.flContent?.addView(activityView)

        setActions()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)


    }

    open fun setActions() {}


    fun showProgressFullScreen() {
        inflateLayout(R.layout.progress_dialog)
    }

    fun hideProgressFullScreen(res: Int) {
        inflateMain(res)
    }

    private fun inflateLayout(progressRes: Int) {
        val progressViewRes = progressRes
        val progress = LayoutInflater.from(this)
            .inflate(progressViewRes, viewBase?.flContent, false) as ViewGroup
        viewBase?.flContent?.addView(progress)
    }

    private fun inflateMain(layoutResource: Int) {
        viewBase?.flContent?.removeAllViewsInLayout()

        val progress = LayoutInflater.from(this)
            .inflate(layoutResource, viewBase?.flContent, false) as ViewGroup
        viewBase?.flContent?.addView(progress)
    }

    fun startActivity(activityClass: Class<*>) {
        startActivity(Intent(this, activityClass))
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBase = null
    }
    fun handleErrorGeneral(th: Throwable, func: (() -> Unit)? = null): CustomErrorThrow? {
        th.printStackTrace()

        when (th.message) {
            Constants.ERROR_API.BAD_REQUEST -> {
                Toast.makeText(this, "bad request", Toast.LENGTH_SHORT).show()
            }
            Constants.ERROR_API.UNAUTHRIZED -> {
                // no need
                Toast.makeText(this, "unauth", Toast.LENGTH_SHORT).show()
            }

            else -> {
                if (th.cause is CustomErrorThrow) {
                    val cause = th.cause as CustomErrorThrow
                    Toast.makeText(this, cause.value, Toast.LENGTH_SHORT).show()
                    return cause

                } else {
                    Toast.makeText(this, "${th.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return null
    }


    fun hideKeyboard() {
        lifecycleScope.launch(Dispatchers.Main) {
            delay(300)
            currentFocus?.let {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.windowToken, 0)
            }
        }
    }
}

data class CustomErrorThrow(val key:String,val value:String):Throwable()
