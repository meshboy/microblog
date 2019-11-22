package com.ex.microblog.core.network

import com.ex.microblog.core.data.author.dto.AuthorDto
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.QueryMap


/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-20
 */

/**
 * Defines all calls used by retrofit
 */
interface NetworkService {

    @GET(value = "authors")
    fun fetchAuthors(@QueryMap query: Map<String, Int>): Deferred<List<AuthorDto>>
}