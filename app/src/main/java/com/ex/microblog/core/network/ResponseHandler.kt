package com.ex.microblog.core.network

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-20
 */

/**
 * A spec to define how network responses need to be handled
 */
sealed class ResponseHandler<out T : Any> {
    /**
     * handle successful responses
     */
    class Success<out T : Any>(val response: T) : ResponseHandler<T>()

    /**
     * handle errror related responses
     */
    class Error(val ex: Throwable? = null, val message: String? = ex?.localizedMessage) :
        ResponseHandler<Nothing>()
}