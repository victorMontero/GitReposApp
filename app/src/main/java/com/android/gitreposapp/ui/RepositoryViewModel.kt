package com.android.gitreposapp.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.gitreposapp.RepositoryApplication
import com.android.gitreposapp.models.RepositoryResponse
import com.android.gitreposapp.models.RepositoryResponseItem
import com.android.gitreposapp.repository.RepoRepository
import com.android.gitreposapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class RepositoryViewModel(app: Application, val repoRepository: RepoRepository) :
    AndroidViewModel(app) {

    val repository: MutableLiveData<Resource<RepositoryResponse>> = MutableLiveData()


    init {
        getRepository()
    }

    fun getRepository() = viewModelScope.launch {
        safeRepositoryCall()
    }

    private fun handleRepositoryResponse(response: Response<RepositoryResponse>): Resource<RepositoryResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveRepository(repository: RepositoryResponseItem) = viewModelScope.launch {
        repoRepository.insert(repository)
    }

    fun getSavedRepository() = repoRepository.getSavedRepository()

    fun deleteRepository(repository: RepositoryResponseItem) = viewModelScope.launch {
        repoRepository.deleteRepository(repository)
    }

    private suspend fun safeRepositoryCall() {
        repository.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = repoRepository.getRepository()
                repository.postValue(handleRepositoryResponse(response))
            } else {
                repository.postValue(Resource.Error("no internet"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> repository.postValue(Resource.Error("network failure"))
                else -> repository.postValue(Resource.Error("conversion error"))
            }
        }
    }


    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<RepositoryApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

}