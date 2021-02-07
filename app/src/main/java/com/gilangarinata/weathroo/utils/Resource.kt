package com.gilangarinata.weathroo.utils


/**
 * Created by Gilang Arinata on 07/02/21.
 * https://github.com/gilangarinata/
 */
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}