package com.example.android.guideviewer.core.network

import com.example.android.guideviewer.data.model.GuideList
import retrofit2.Response
import retrofit2.http.GET

interface GuideApi {

    companion object {
        const val SERVER_URL = "https://guidebook.com/"
        const val API_URL = "service/v2/upcomingGuides/"
    }

    @GET(API_URL)
    suspend fun getGuides(): Response<GuideList>
}