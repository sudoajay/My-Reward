package com.sudoajay.myreward.activity.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RewardDao {

    @Query("select * from (SELECT * FROM RewardTable where Scratch = 0 ORDER BY Date Desc) X UNION ALL SELECT * FROM RewardTable where Scratch = 1 ORDER BY Scratch Desc ")
    fun getRewardByRecentDate(): LiveData<List<Reward>>

    @Query("select * from (SELECT * FROM RewardTable where Scratch = 0 ORDER BY Amount Desc) X UNION ALL SELECT * FROM RewardTable where Scratch = 1 ORDER BY Scratch Desc ")
    fun getRewardByAmountDescOrder(): LiveData<List<Reward>>

    @Query("select * from (SELECT * FROM RewardTable where Scratch = 0 ORDER BY Amount Asc) X UNION ALL SELECT * FROM RewardTable where Scratch = 1 ORDER BY Scratch Desc ")
    fun getRewardByAmountAscOrder(): LiveData<List<Reward>>

    @Query("UPDATE  RewardTable set Amount =:amount , Date = :date , Code =:code , Earned = :earned , Scratch = 0 , Greeting=:greeting where id = :id ")
    suspend fun updateInfo(
        id: Long,
        amount: String,
        date: Long,
        code: String,
        earned: String,
        greeting: String
    )



    @Query("Select Count(*) FROM RewardTable ")
    suspend fun getCount(): Int

    @Query("Select sum(Amount) FROM RewardTable")
    suspend fun getTotalSum() :Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(reward: Reward)

    @Query("Select id FROM RewardTable where amount = 0 and Scratch = 0 ")
    suspend fun getIdOfAmountNone(): List<Long>

    @Query("DELETE FROM RewardTable where id = :id ")
    suspend fun deleteRowFromId(id:Long)
}