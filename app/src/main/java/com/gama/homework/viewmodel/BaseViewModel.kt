package com.gama.homework.viewmodel

import android.content.Context
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gama.homework.R
import com.gama.homework.database.UserData
import com.gama.homework.database.UserRoomDatabase
import com.gama.homework.model.request.LoginRequest
import com.gama.homework.model.response.LoginResponse
import com.gama.homework.retrofit.CustomCB
import com.gama.homework.retrofit.RetrofitAPI
import com.gama.homework.retrofit.RetrofitSingleton
import com.gama.homework.utils.CommonUtils
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import okhttp3.Headers

class BaseViewModel(private val context: Context) : ViewModel() {
    private val userDatabase = UserRoomDatabase.getDatabase(context)
    val loginDataInsert: MutableLiveData<Boolean> =
            MutableLiveData()
    val userList: MutableLiveData<List<UserData>> =
            MutableLiveData()

    fun login(loginRequest: LoginRequest) {
        if (CommonUtils.isConnectionAvailable(context)) {
            val apiServices: RetrofitAPI? = RetrofitSingleton.getInstance(context)?.provideClient()
            apiServices?.login(loginRequest.username, loginRequest.password)?.enqueue(CustomCB(true, object : CustomCB.OnAPIResponse {
                override fun onResponse(response: Any?, headers: Headers) {
                    val loginResponse = response as LoginResponse
                    loginResponse.xAcc = headers.get("X-Acc").toString()
                    manageLoginResponse(loginResponse)
                }
            }))
        }
    }

    private fun manageLoginResponse(loginResponse: LoginResponse?) {
        if (loginResponse != null) {
            if (loginResponse.errorCode == 0) {
                val userData = loginResponse.user
                if (userData != null) {
                    userDatabase.userDao().insert(UserData(userData.userName, userData.userId, loginResponse.xAcc))
                    loginDataInsert.postValue(true)
                } else {
                    loginDataInsert.postValue(false)
                }
            } else {
                loginDataInsert.postValue(false)
            }
        }
    }

    fun getLocalData() {
        val allUsers = userDatabase.userDao().getAll()
        userList.postValue(allUsers)
    }

    fun validation(userNameEditText: TextInputEditText, userNameInputLayout: TextInputLayout, passwordEditText: TextInputEditText, passwordInputLayout: TextInputLayout): Boolean {
        if (TextUtils.isEmpty(userNameEditText.text.toString())) {
            userNameInputLayout.error = context.getString(R.string.usernameEmpty)
            return false
        } else if (TextUtils.isEmpty(passwordEditText.text.toString())) {
            passwordInputLayout.error = context.getString(R.string.password_empty)
            return false
        }
        return true
    }


}