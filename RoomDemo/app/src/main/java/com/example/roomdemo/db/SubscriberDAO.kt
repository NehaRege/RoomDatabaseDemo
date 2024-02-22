package com.example.roomdemo.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface SubscriberDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSubscriber(subscriber: Subscriber): Long // <-- Return the inserted row ID

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber): Int

    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber): Int

    @Query("DELETE FROM subscriber_data_table")
    suspend fun deleteAll(): Int


    @Query("SELECT * FROM subscriber_data_table")
    fun getAllSubscribers(): LiveData<List<Subscriber>>
}

/*

getAllSubscribers() --> No suspend required for this function as we don't have to execute it in background thread using Coroutines.
Since this function returns LiveData, Room will do its work in background thread

Room library uses coroutines for QUERY by itself internally whether we return Flow, LiveData or just a List.
So we don't need to use it from our side (otherwise there will be two coroutines)
But for other database operations(insert, update, delete) we need to use a separate thread or a coroutine.
 */

/*
onConflict = OnConflictStrategy.REPLACE  --> this will try to replace and if there's an existing item with same ID value then it wil delete that and replace it with this one
onConflict = OnConflictStrategy.IGNORE  --> will just ignore
*/

// NOTE: We're using "suspend" modifier as Room doesn't support database access on the main thread as it will pause the UI for a long period of time.
// so we need to execute these functions on background thread and for that we can use Async task, RxJava etc.
// But since we will be using Coroutines, we use the suspend modifier (Coroutines are more efficient and Room provides direct support for it)
// If we don't want to use Coroutines, we can write these functions without suspend modifier.