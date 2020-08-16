package com.sudoajay.myreward.activity

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sudoajay.myreward.R
import com.sudoajay.myreward.databinding.ActivityScrollingBinding
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.android.synthetic.main.content_scrolling.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrollingBinding
    private lateinit var viewModel: ViewModel
    private var TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_scrolling)

        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        setSupportActionBar(toolbar)

    }

    override fun onResume() {
        super.onResume()

        //      Setup Swipe RecyclerView
//
//        swipe_refresh.setColorSchemeResources(
//            R.color.swipeSchemeColor
//        )
//
//        //         Setup BottomAppBar Navigation Setup
//        swipe_refresh.setOnRefreshListener {
//            swipe_refresh.isRefreshing = false
//        }

//        Setup Recycler View
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val recyclerView = recycler_view
        val rewardAdapter = RewardAdapter(this)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = rewardAdapter
        recyclerView.adapter = rewardAdapter

        viewModel.rewardList!!.observe(this, Observer {

            for (x in it) {
                Log.e(TAG, x.amount.toString())
            }
            rewardAdapter.items = it
            rewardAdapter.notifyDataSetChanged()
//
//            if (swipe_refresh.isRefreshing)
//                swipe_refresh.isRefreshing = false

        })


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_setting, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify reward_1 parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_new_reward -> true
            R.id.recent_reward_sort_by -> true
            R.id.amount_dec_sort_by -> true
            R.id.amount_asc_sort_by -> true
            R.id.action_about_app -> {
                openGithubApp()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openGithubApp() {
        val link = "https://github.com/SudoAjay/My-Reward"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(link)
        startActivity(i)
    }

}