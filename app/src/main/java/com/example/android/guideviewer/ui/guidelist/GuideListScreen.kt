package com.example.android.guideviewer.ui.guidelist

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android.guideviewer.data.model.ApiResult
import com.example.android.guideviewer.data.model.GuideList


@Composable
fun GuideListScreen(
    viewModel: GuideListViewModel = hiltViewModel()
) {
    val guides by viewModel.guideListState.collectAsState()

    when (guides) {
        is ApiResult.Loading -> Text(text = "Loading")
        is ApiResult.Failure -> Text(text = (guides as ApiResult.Failure).message ?: "Unknown Error")
        is ApiResult.Success -> Text(text = "Success ${(guides as ApiResult.Success<GuideList>).value.guides[0].name}")
    }
}
