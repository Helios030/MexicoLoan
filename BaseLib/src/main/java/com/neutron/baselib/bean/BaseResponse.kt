package com.neutron.baselib.bean

data class BaseResponse<out T>(
    val code: String,
    val message: String,
    val result: T?,
    val sign: String
) {
    fun getResponse(): T? {
        if (code == "200") {
           return result
        }
        throw ApiException(code, message)
    }
}

data class ApiException(var code: String, override var message: String): RuntimeException()