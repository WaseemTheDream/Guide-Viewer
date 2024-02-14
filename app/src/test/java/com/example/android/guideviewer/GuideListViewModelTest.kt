package com.example.android.guideviewer

import com.example.android.guideviewer.core.network.GuideApi
import com.example.android.guideviewer.data.model.ApiResult
import com.example.android.guideviewer.data.repository.GuideRepositoryImpl
import com.example.android.guideviewer.ui.guidelist.GuideListViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GuideListViewModelTest {

    private lateinit var viewModel: GuideListViewModel
    private lateinit var mockWebServer: MockWebServer

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        mockWebServer = MockWebServer()
        mockWebServer.start()

        val guideApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GuideApi::class.java)

        viewModel = GuideListViewModel(GuideRepositoryImpl(guideApi))
    }

    @Test
    fun `initial state is loading`() = runTest {
        assertEquals(ApiResult.Loading, viewModel.guideListState.value)
    }
}