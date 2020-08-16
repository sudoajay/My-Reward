package com.sudoajay.myreward.activity.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RewardDao {

    @Query("Select * FROM RewardTable ")
     fun getRewardByRecentOrder(): LiveData<List<Reward>>

    @Query("Select * FROM RewardTable Order By amount Desc ")
     fun getRewardByAmountDescOrder(): LiveData<List<Reward>>

    @Query("Select * FROM RewardTable Order By amount Asc ")
     fun getRewardByAmountAscOrder():LiveData<List<Reward>>

    @Query("Select Count(*) FROM RewardTable ")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(reward: Reward)

    @Query("DELETE FROM RewardTable")
    suspend fun deleteAll()
}