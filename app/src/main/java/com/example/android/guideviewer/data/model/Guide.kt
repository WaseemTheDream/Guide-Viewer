package com.example.android.guideviewer.data.model

import com.google.gson.annotations.SerializedName

data class Guide(
    @SerializedName("url")
    val url: String,

    @SerializedName("startDate")
    val startDate: String,

    @SerializedName("endDate")
    val endDate: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("icon")
    val icon: String
)