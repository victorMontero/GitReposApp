package com.android.gitreposapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.gitreposapp.models.RepositoryResponse
import com.android.gitreposapp.repository.RepoRepository
import com.android.gitreposapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class RepositoryViewModel(val repoRepository: RepoRepository) : ViewModel() {

    val repository: MutableLiveData<Resource<RepositoryResponse>> = MutableLiveData()
    var repositoryPage = 1

    init {
        getRepository()
    }

    fun getRepository() = viewModelScope.launch {
        repository.postValue(Resource.Loading())
        val response = repoRepository.getRepository()
        repository.postValue(handleRepositoryResponse(response))
    }

    private fun handleRepositoryResponse(response: Response<RepositoryResponse>) : Resource<RepositoryResponse>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}