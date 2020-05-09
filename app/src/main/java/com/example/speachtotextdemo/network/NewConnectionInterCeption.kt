package com.example.speachtotextdemo.network

import android.content.Context
import android.net.ConnectivityManager
import com.example.speachtotextdemo.R
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterCeption(context: Context) : Interceptor {

    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!inNetworkAvailable())
            throw NoInternetException(applicationContext.getString(R.string.internet_alert))
        return chain.proceed(chain.request())
    }


    //check for internet connection
    private fun inNetworkAvailable(): Boolean {
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.activeNetworkInfo.also {
            return it != null && it.isConnected
        }

    }
}