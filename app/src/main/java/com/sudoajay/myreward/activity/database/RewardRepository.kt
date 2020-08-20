package com.sudoajay.myreward.activity.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.sudoajay.myreward.R


class RewardRepository(private val context: Context, private val rewardDao: RewardDao) {


    fun listUpdate(filter: String): LiveData<List<Reward>> {
        return when (filter) {
            context.getString(R.string.amount_dec_sort_by) -> rewardDao.getRewardByAmountDescOrder()
            context.getString(R.string.amount_asc_sort_by) -> rewardDao.getRewardByAmountAscOrder()
            else -> rewardDao.getRewardByRecentDate()
        }
    }

    suspend fun deleteAmountNoneFromDb() {
        for (x in rewardDao.getIdOfAmountNone()) {
            rewardDao.deleteRowFromId(x)
        }
    }

    suspend fun insert(reward: Reward) {
        rewardDao.insert(reward)
    }

    suspend fun getCount(): Int {
        return rewardDao.getCount()
    }

    suspend fun updateInfo(
        id: Long,
        amount: String,
        date: Long,
        code: String,
        earned: String,
        greeting: String
    ) {
        rewardDao.updateInfo(id, amount, date, code, earned, greeting)
    }

}