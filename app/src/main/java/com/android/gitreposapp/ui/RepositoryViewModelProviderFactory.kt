package com.android.gitreposapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.gitreposapp.repository.RepoRepository

class RepositoryViewModelProviderFactory(val repoRepository: RepoRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RepositoryViewModel(repoRepository) as T
    }
}