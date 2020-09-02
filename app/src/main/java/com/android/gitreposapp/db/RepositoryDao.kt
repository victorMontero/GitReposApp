package com.android.gitreposapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.gitreposapp.models.RepositoryResponseItem

@Dao
interface RepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repository: RepositoryResponseItem): Long

    @Query("SELECT * FROM repositories")
    fun getAllRepositories(): LiveData<List<RepositoryResponseItem>>

    @Delete
    suspend fun deleteRepository(repository: RepositoryResponseItem)
}