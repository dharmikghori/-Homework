package com.gama.homework.database


import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_table")
class UserData(
        var userName: String,
        var userID: Int,
        var xACC: String,
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0)
