package com.ex.microblog.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-19
 */


/**
 * check if network is available on the user's device
 *
 * reference: https://developer.android.com/reference/android/net/ConnectivityManager
 */
fun isConnected(context: Context): Boolean {

    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    } else {
        val networkInfo = connectivityManager.activeNetworkInfo
        networkInfo != null && networkInfo.isConnectedOrConnecting
    }
}
