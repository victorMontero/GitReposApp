package com.android.gitreposapp.api

import com.android.gitreposapp.models.RepositoryResponse
import retrofit2.Response
import retrofit2.http.GET

interface RepositoryAPI {

    @GET("repositories")
    suspend fun getRepositories(): Response<RepositoryResponse>

}