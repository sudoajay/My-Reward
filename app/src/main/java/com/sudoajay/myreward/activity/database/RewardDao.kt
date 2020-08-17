package com.sudoajay.myreward.activity.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RewardDao {

    @Query("Select * FROM RewardTable  Order By Date Desc ")
     fun getRewardByRecentDate(): LiveData<List<Reward>>

    @Query("Select * FROM RewardTable Order By amount Desc ")
     fun getRewardByAmountDescOrder(): LiveData<List<Reward>>

    @Query("Select * FROM RewardTable Order By amount Asc ")
     fun getRewardByAmountAscOrder():LiveData<List<Reward>>



    @Query("Select Count(*) FROM RewardTable ")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(reward: Reward)

    @Query("Select id FROM RewardTable where amount = 0 ")
    suspend fun getIdOfAmountNone(): List<Long>

    @Query("DELETE FROM RewardTable where id = :id ")
    suspend fun deleteRowFromId(id:Long)
}