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
import java.util.*

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
        deleteFromDb()
        filterChanges()
        getHideProgress()
        hideProgress!!.value =   false
    }

    private fun addData() {
        CoroutineScope(Dispatchers.IO).launch {

            if (rewardRepository.getCount() == 0) {
                rewardRepository.insert(
                    Reward(
                        null,
                        150,
                        1576517014000,
                        getRandomString(),
                        getEarnedFrom(), false
                    )
                )
                rewardRepository.insert(
                    Reward(
                        null,
                        220,
                        1576734051000,
                        getRandomString(),
                        getEarnedFrom(), false
                    )
                )
                rewardRepository.insert(
                    Reward(
                        null,
                        225,
                        1526341914000,
                        getRandomString(),
                        getEarnedFrom(), false
                    )
                )
                rewardRepository.insert(
                    Reward(
                        null,
                        0,
                        1554431984000,
                        getRandomString(),
                        getEarnedFrom(), false
                    )
                )
                rewardRepository.insert(
                    Reward(
                        null,
                        500,
                        1575480551000,
                        getRandomString(),
                        getEarnedFrom(), false
                    )
                )
                rewardRepository.insert(
                    Reward(
                        null,
                        550,
                        1554431984000,
                        getRandomString(),
                        getEarnedFrom(), false
                    )
                )
            }
        }
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
                    true
                )
            )

            filterChanges.postValue(_application.getString(R.string.date_sort_by))
        }
    }

    private fun deleteFromDb() {
        CoroutineScope(Dispatchers.IO).launch {
            rewardRepository.deleteAmountNoneFromDb()
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

    private fun getRandomString(sizeOfRandomString: Int= 14 ): String {
        val random = Random()
        val sb = StringBuilder(sizeOfRandomString)
        for (i in 0 until sizeOfRandomString)
            sb.append(ALLOWED_CHARACTERS[random.nextInt(ALLOWED_CHARACTERS.length)])
        return sb.toString()
    }
    private fun getEarnedFrom():String{
        val array = listOf("Amazon", "Google","Flipkart", "Mom","Dad","Bro")
        return array.random()
    }
    companion object {
        private const val ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm"
    }

}