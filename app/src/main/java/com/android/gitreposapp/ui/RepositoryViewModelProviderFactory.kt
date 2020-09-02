package com.android.gitreposapp.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.gitreposapp.repository.RepoRepository

class RepositoryViewModelProviderFactory(val app: Application, val repoRepository: RepoRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RepositoryViewModel(app, repoRepository) as T
    }
}