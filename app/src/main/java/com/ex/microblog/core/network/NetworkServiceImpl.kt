package com.ex.microblog.core.network

import android.content.Context
import com.ex.microblog.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-20
 */
class NetworkServiceImpl(context: Context) {

    /**
     * log interceptor mostly for debugging purpose when making calls
     */
    private fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val logLevel =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = logLevel
        return loggingInterceptor
    }

    /**
     * moshi adapter factor to handle data btw JSON and objects
     */
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()


    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor())
        .addInterceptor(ConnectionInterceptor(context))
        .connectTimeout(120, TimeUnit.SECONDS)
        .build()

    /**
     * create a retrofit instance
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    /**
     * reflects calls in NetworkService served via retrofit
     */
    fun api(): NetworkService = retrofit.create(NetworkService::class.java)
}