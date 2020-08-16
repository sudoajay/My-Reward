package com.sudoajay.myreward.activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.sudoajay.myreward.R
import com.sudoajay.myreward.activity.database.Reward
import com.sudoajay.myreward.activity.database.RewardRepository
import com.sudoajay.myreward.activity.database.RewardRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel(application: Application) : AndroidViewModel(application) {


    private var _application = application
    var rewardRepository: RewardRepository

    private var dnsDao = RewardRoomDatabase.getDatabase(application.applicationContext).rewardDao()

    var rewardList: LiveData<List<Reward>>? = null
    val filterChanges: MutableLiveData<String> = MutableLiveData()
    private var hideProgress: MutableLiveData<Boolean>? = null

    init {
        //        Creating Object and Initialization
        rewardRepository = RewardRepository(application.applicationContext, dnsDao)


        rewardList = Transformations.switchMap(filterChanges) {
            rewardRepository.listUpdate(it)
        }

        addData()
        filterChanges()
        getHideProgress()
        hideProgress!!.value =   false
    }

    private fun addData() {
        CoroutineScope(Dispatchers.IO).launch {
            if (rewardRepository.getCount() == 0) {
                rewardRepository.insert(Reward(null, 150))
                rewardRepository.insert(Reward(null, 220))
                rewardRepository.insert(Reward(null, 225))
                rewardRepository.insert(Reward(null, 300))
                rewardRepository.insert(Reward(null, 500))
                rewardRepository.insert(Reward(null, 550))
            }
        }
    }

    fun filterChanges(filter: String = _application.getString(R.string.recent_reward_sort_by)) {
        filterChanges.value = filter
    }

    fun getHideProgress(): LiveData<Boolean> {
        if (hideProgress == null) {
            hideProgress = MutableLiveData()
            loadHideProgress()
        }
        return hideProgress as MutableLiveData<Boolean>
    }

    private fun loadHideProgress() {
        hideProgress!!.value = true
    }

}