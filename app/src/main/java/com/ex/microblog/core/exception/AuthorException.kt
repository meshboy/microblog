package com.ex.microblog.core.exception

import java.lang.Exception

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-20
 */

/**
 * custom Exception for Author's related Errors
 */
data class AuthorException(var msg: String?): Exception(msg)