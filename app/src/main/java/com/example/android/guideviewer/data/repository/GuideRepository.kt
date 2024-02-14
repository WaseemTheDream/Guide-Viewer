package com.example.android.guideviewer.data.repository

import com.example.android.guideviewer.data.model.ApiResult
import com.example.android.guideviewer.data.model.GuideList
import kotlinx.coroutines.flow.Flow

interface GuideRepository {

    suspend fun getGuides(): Flow<ApiResult<GuideList>>
}