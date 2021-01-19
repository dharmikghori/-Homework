package com.gama.homework.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.gama.homework.R
import com.gama.homework.model.request.LoginRequest
import com.gama.homework.utils.CommonUtils
import com.gama.homework.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), View.OnClickListener {
    private lateinit var baseViewModel: BaseViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        onLogin.setOnClickListener(this)
        baseViewModel = BaseViewModel(this)

    }

    override fun onClick(v: View?) {
        if (v != null) {
            if (v.id == R.id.onLogin) {
                onLoginClick()
            }
        }
    }

    private fun onLoginClick() {
        if (baseViewModel.validation(userNameEditText, userNameInputLayout, passwordEditText, passwordInputLayout)) {
            val loginRequest = LoginRequest("username", "1111111")
            baseViewModel.login(loginRequest)
            baseViewModel.loginDataInsert.observe(this, {
                if (it) {
                    CommonUtils.showToast(getString(R.string.login_data_inserted))
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                } else {
                    CommonUtils.showToast(getString(R.string.unable_insert))
                }
            })
        }
    }


}