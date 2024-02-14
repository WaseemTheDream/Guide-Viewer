package com.example.android.guideviewer.ui.guidelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.guideviewer.data.model.ApiResult
import com.example.android.guideviewer.data.model.GuideList
import com.example.android.guideviewer.data.repository.GuideRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GuideListViewModel @Inject constructor(
    private val guideRepository: GuideRepository
) : ViewModel() {

    private val _guideListState: MutableStateFlow<ApiResult<GuideList>> =
        MutableStateFlow(ApiResult.Loading)

    val guideListState: StateFlow<ApiResult<GuideList>> get() = _guideListState

    init {
        getGuides()
    }

    fun getGuides() {
        _guideListState.value = ApiResult.Loading
        viewModelScope.launch {
            guideRepository.getGuides()
                .flowOn(Dispatchers.IO)
                .collect {
                    _guideListState.value = it
                }
        }
    }
}