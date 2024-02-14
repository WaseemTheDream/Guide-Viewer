package com.example.android.guideviewer

import app.cash.turbine.test
import com.example.android.guideviewer.core.network.GuideApi
import com.example.android.guideviewer.data.model.ApiResult
import com.example.android.guideviewer.data.repository.GuideRepositoryImpl
import com.example.android.guideviewer.ui.guidelist.GuideListViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

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

    @Test
    fun `bad request returns failure state`() = runTest {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
            .setBody("")

        mockWebServer.enqueue(response)

        viewModel.getGuides()

        viewModel.guideListState.test {
            assertEquals(ApiResult.Loading, awaitItem())  // Initial state is loading

            assertTrue(awaitItem() is ApiResult.Failure)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `successful response returns valid guides`() = runTest {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(TestFileReader("sample_guides.json").content)

        mockWebServer.enqueue(response)

        viewModel.getGuides()

        viewModel.guideListState.test {
            assertEquals(ApiResult.Loading, awaitItem())

            val result = awaitItem()
            assertTrue(result is ApiResult.Success)

            val successResult = result as ApiResult.Success
            val expected = "2024 QA & Dosimetry Symposium"
            assertEquals(expected, successResult.value.guides[0].name)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mockWebServer.shutdown()
    }
}