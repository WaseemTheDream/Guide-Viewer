package com.example.android.guideviewer.data.model

import com.google.gson.annotations.SerializedName

data class GuideList(
    @SerializedName("total")
    val total: Int,

    @SerializedName("data")
    val guides: List<Guide>
)