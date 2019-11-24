package com.ex.microblog.core.exception

import java.lang.Exception

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-23
 */

/**
 * custom Exception for posts related Errors
 */
data class PostException(var msg: String?): Exception(msg)