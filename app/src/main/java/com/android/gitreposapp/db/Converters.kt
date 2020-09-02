package com.android.gitreposapp.db

import androidx.room.TypeConverter
import com.android.gitreposapp.models.Owner

class Converters {

    @TypeConverter
    fun fromOwner(owner: Owner): String{
        return owner.login
    }

    @TypeConverter
    fun toOwner(login: String): Owner{
        return Owner(login, login, login)
    }
}