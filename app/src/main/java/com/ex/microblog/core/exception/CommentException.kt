package com.ex.microblog.core.exception

import java.lang.Exception

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-24
 */
data class CommentException(val msg: String?): Exception(msg)