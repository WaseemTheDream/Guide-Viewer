package com.example.android.guideviewer.data.repository

import com.example.android.guideviewer.core.network.GuideApi
import com.example.android.guideviewer.data.model.ApiResult
import com.example.android.guideviewer.data.model.GuideList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GuideRepositoryImpl @Inject constructor(
    private val guideApi: GuideApi
) : GuideRepository {

    override suspend fun getGuides(): Flow<ApiResult<GuideList>> = flow {
        try {
            val response = guideApi.getGuides()

            if (!response.isSuccessful || response.body() == null) {
                emit(ApiResult.Failure(response.errorBody()?.string(), HttpException(response)))
                return@flow
            }

            emit(ApiResult.Success(checkNotNull(response.body())))
        } catch (exception: Exception) {
            emit(ApiResult.Failure(exception.localizedMessage, exception))
        }
    }
}