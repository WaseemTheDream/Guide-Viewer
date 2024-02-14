package com.example.android.guideviewer.data.model

sealed class ApiResult<out T> {

    object Loading : ApiResult<Nothing>()

    data class Success<out R>(val value: R): ApiResult<R>()

    data class Failure(
        val message: String?,
        val throwable: Throwable?): ApiResult<Nothing>()
}
