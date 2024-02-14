package com.example.android.guideviewer.ui.guidelist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android.guideviewer.R
import com.example.android.guideviewer.data.model.ApiResult
import com.example.android.guideviewer.data.model.Guide
import com.example.android.guideviewer.data.model.GuideList
import com.example.android.guideviewer.ui.common.PageLoader


@Composable
fun GuideListScreen(
    viewModel: GuideListViewModel = hiltViewModel()
) {
    val guides by viewModel.guideListState.collectAsState()

    when (guides) {
        is ApiResult.Loading ->
            PageLoader(
                modifier = Modifier.fillMaxSize(),
                loadingMessage = stringResource(id = R.string.loading_data))
        is ApiResult.Failure -> Text(text = (guides as ApiResult.Failure).message ?: "Unknown Error")
        is ApiResult.Success -> GuideList(guides as ApiResult.Success<GuideList>)
    }
}

@Composable
fun GuideList(
    guides: ApiResult.Success<GuideList>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)) {

        item { Spacer(modifier = Modifier.padding(2.dp)) }

        items(guides.value.guides.size) { index ->
            ItemGuide(guide = guides.value.guides[index])
        }

        item { Spacer(modifier = Modifier.padding(2.dp)) }
    }
}

@Composable
fun ItemGuide(
    guide: Guide
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(0.9f),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)) {
        Text(
            modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
            text = guide.name,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp)
        Text(
            modifier = Modifier.padding(bottom = 8.dp, start = 8.dp, end = 8.dp),
            text = guide.startDate,
            fontSize = 12.sp)
    }
}
