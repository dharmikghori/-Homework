package com.gama.homework.retrofit

import com.gama.homework.utils.CommonUtils
import okhttp3.Headers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection


class CustomCB<T>(isShowProgressDialog: Boolean, onAPIResponse: OnAPIResponse) : Callback<T> {
    private var onAPIResponse: OnAPIResponse? = null

    interface OnAPIResponse {
        fun onResponse(response: Any?, headers: Headers)
    }

    override fun onResponse(
            call: Call<T>,
            response: Response<T>
    ) {
        dismissProgress()
        if (response.code() == HttpURLConnection.HTTP_OK) {
            val body = response.body()
            if (body != null) {
                onAPIResponse?.onResponse(body, response.headers())
            }
        }
    }

    override fun onFailure(call: Call<T?>, t: Throwable) {
        dismissProgress()
        if (!t.message.equals("java.lang.NumberFormatException: Expected an int but was NaN at line 1 column 24 path \$.data.wallet"))
            CommonUtils.showToast(t.message.toString())
    }

    private fun dismissProgress() {
        CommonUtils.dismissProgress()
    }

    init {
        if (isShowProgressDialog) {
            CommonUtils.displayProgress()
        }
        this.onAPIResponse = onAPIResponse
    }
}