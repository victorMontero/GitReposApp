package com.android.gitreposapp.models


import com.google.gson.annotations.SerializedName

data class RepositoryResponseItem(
    @SerializedName("html_url")
    val htmlUrl: String,
    val name: String,
)