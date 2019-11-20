package com.ex.microblog.core.network

import android.content.Context
import com.ex.microblog.core.isConnected
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-20
 */

/**
 * Intercept the request and check the availability of the device's network
 */
class ConnectionInterceptor(private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected(context)) {
            throw IOException("No internet")
        }
        return chain.proceed(chain.request())
    }
}