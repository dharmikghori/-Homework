package com.gama.homework.ui

import android.os.Bundle
import com.gama.homework.R
import com.gama.homework.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private lateinit var baseViewModel: BaseViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        baseViewModel = BaseViewModel(this)
        baseViewModel.getLocalData()
        baseViewModel.userList.observe(this, {
            userCount.text = "${it.size} Users found"
        })
    }

}