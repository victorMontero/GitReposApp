package com.android.gitreposapp.models


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(
    tableName = "repositories"
)
data class RepositoryResponseItem(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @SerializedName("html_url")
    val htmlUrl: String,
    val name: String,
    val owner: Owner
) : Serializable