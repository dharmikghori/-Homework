package com.gama.homework.database


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface UserDao {

    @Query("SELECT * FROM user_table")
    fun getAll(): List<UserData>

    /**
     * Function to insert a users in room database
     * @param userData to be inserted in database
     */
    @Insert
    fun insert(userData: UserData)

}
