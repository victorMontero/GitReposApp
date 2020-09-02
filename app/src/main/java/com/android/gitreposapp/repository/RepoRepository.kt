package com.android.gitreposapp.repository

import com.android.gitreposapp.api.RetrofitInstance
import com.android.gitreposapp.db.RepositoryDatabase
import com.android.gitreposapp.models.RepositoryResponseItem

class RepoRepository(val db: RepositoryDatabase) {

    suspend fun getRepository() = RetrofitInstance.api.getRepositories()

    suspend fun insert(repository: RepositoryResponseItem) =
        db.getRepositoryDao().insert(repository)

    fun getSavedRepository() = db.getRepositoryDao().getAllRepositories()

    suspend fun deleteRepository(repository: RepositoryResponseItem) =
        db.getRepositoryDao().deleteRepository(repository)
}