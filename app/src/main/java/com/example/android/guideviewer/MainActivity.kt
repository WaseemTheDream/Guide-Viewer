package com.example.android.guideviewer

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.android.guideviewer.core.network.GuideApi
import com.example.android.guideviewer.data.model.ApiResult
import com.example.android.guideviewer.data.repository.GuideRepository
import com.example.android.guideviewer.ui.theme.GuideViewerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var guideRepository: GuideRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalScope.launch {
            guideRepository.getGuides().collect { result
                ->
                if (result is ApiResult.Failure) {
                    result.message?.let { it1 -> Log.d("MainActivity", it1) }
                } else if (result is ApiResult.Success) {
                    result.value.guides.let { Log.d("MainActivity", it[0].name) }
                }
            }
        }

        setContent {
            GuideViewerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GuideViewerTheme {
        Greeting("Android")
    }
}