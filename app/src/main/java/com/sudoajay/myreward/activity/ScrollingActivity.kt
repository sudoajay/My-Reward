package com.sudoajay.myreward.activity

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sudoajay.myreward.R
import kotlinx.android.synthetic.main.activity_scrolling.*

class ScrollingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_setting, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

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