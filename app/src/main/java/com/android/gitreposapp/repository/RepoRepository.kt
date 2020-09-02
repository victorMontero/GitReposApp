package com.android.gitreposapp.repository

import com.android.gitreposapp.api.RetrofitInstance
import com.android.gitreposapp.db.RepositoryDatabase

class RepoRepository(val db: RepositoryDatabase) {

    suspend fun getRepository() = RetrofitInstance.api.getRepositories()
}