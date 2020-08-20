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
    var totalAmount:MutableLiveData<String> = MutableLiveData()
    private var hideProgress: MutableLiveData<Boolean>? = null

    init {
        //        Creating Object and Initialization
        rewardRepository = RewardRepository(application.applicationContext, dnsDao)


        rewardList = Transformations.switchMap(filterChanges) {
            rewardRepository.listUpdate(it)
        }


        deleteFromDb()
        filterChanges()
        getHideProgress()
        hideProgress!!.value = false
    }


    fun addNewScratch() {
        CoroutineScope(Dispatchers.IO).launch {
            rewardRepository.insert(
                Reward(
                    null,
                    0,
                    0,
                    "",
                    "",
                    true,
                   ""
                )
            )

            filterChanges.postValue(_application.getString(R.string.date_sort_by))
        }
    }

    private fun deleteFromDb() {
        CoroutineScope(Dispatchers.IO).launch {
            rewardRepository.deleteAmountNoneFromDb()
            if(rewardRepository.getCount() == 0 ){
                addNewScratch()
            }
        }
        getTotalSum()
    }

    fun getTotalSum(){
        CoroutineScope(Dispatchers.IO).launch {
            totalAmount.postValue(rewardRepository.getTotalSum())
        }
    }
    fun onRefresh() {
        deleteFromDb()

        filterChanges()
    }

    fun filterChanges(filter: String = _application.getString(R.string.date_sort_by)) {
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