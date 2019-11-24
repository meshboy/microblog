package com.ex.microblog.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ex.microblog.R
import java.text.SimpleDateFormat
import java.util.*

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-19
 */


/**
 * check if network is available on the user's device
 *
 * reference: https://developer.android.com/reference/android/net/ConnectivityManager
 */
@Suppress("DEPRECATION")
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


/**
 * load image remote resource into an imageView
 */
fun ImageView.loadImage(url: String?, drawable: Int = R.drawable.ic_default_image_profile) {
    url?.let {
        Glide.with(this.context)
            .load(it)
            .apply(
                RequestOptions()
                    .placeholder(drawable)
                    .error(drawable)
            )
            .into(this)
    }
}

/**
 * make view visible
 */
fun View.show() {
    visibility = View.VISIBLE
}

/**
 * make view visible, if onlyInvisible is true, the view takes up the assigned space and not appear
 * other wise, it is gone totally
 */
fun View.hide(onlyInvisible: Boolean = false) {
    visibility = if (onlyInvisible) {
        View.INVISIBLE
    } else {
        View.GONE
    }
}

fun convertDateToGivenFormat(date: Date, format: String = "dd MMMM, yyyy HH:mm:ss a"): String {
    val fmt = SimpleDateFormat(format, Locale.getDefault())
    return fmt.format(date)
}

fun getDateFromDateConstructor(date: String, format: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"): Date {
    val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
    return simpleDateFormat.parse(date) ?: Date()
}