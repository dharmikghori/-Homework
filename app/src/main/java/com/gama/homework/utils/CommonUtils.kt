package com.gama.homework.utils

import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import com.gama.homework.R
import com.gama.homework.ui.BaseActivity

class CommonUtils {
    companion object {
        fun isConnectionAvailable(context: Context): Boolean {
            val connMgr =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connMgr.activeNetworkInfo
            val isConnected = networkInfo != null && networkInfo.isConnected
            if (!isConnected) {
                showToast(context, context.getString(R.string.internet_message))
            }
            return isConnected
        }

        private fun showToast(context: Context?, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }

        fun showToast(message: String) {
            Toast.makeText(BaseActivity.getActivity(), message, Toast.LENGTH_LONG).show()
        }

        var progressDialog: ProgressDialog? = null
        fun displayProgress() {
            progressDialog = ProgressDialog(BaseActivity.getActivity())
            progressDialog!!.setTitle("Login")
            progressDialog!!.setMessage("Please wait")
            progressDialog!!.show()
        }

        fun dismissProgress() {
            progressDialog?.dismiss()
        }

    }
}