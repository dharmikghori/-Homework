package com.gama.homework.ui


import android.app.Activity
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setActivity(this)
    }

    companion object {
        private lateinit var activity: Activity
        fun getActivity(): Activity {
            return activity
        }

        fun setActivity(cntActivity: Activity) {
            activity = cntActivity
        }

    }

}


