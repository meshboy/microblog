package com.ex.microblog.core.network

import com.ex.microblog.core.data.author.dto.AuthorDto
import com.ex.microblog.core.data.post.dto.PostDto
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

    /**
     * fetch paginated list of authors
     */
    @GET(value = "authors")
    fun fetchAuthorsAsync(@QueryMap query: Map<String, Int>): Deferred<List<AuthorDto>>

    /**
     * fetch paginated list of posts
     */
    @GET(value = "posts")
    fun fetchPostsByAuthorIdAsync(@QueryMap query: Map<String, Int>): Deferred<List<PostDto>>
}