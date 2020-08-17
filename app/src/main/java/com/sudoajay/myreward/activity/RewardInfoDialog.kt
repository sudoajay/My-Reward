package com.sudoajay.myreward.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.sudoajay.myreward.R
import com.sudoajay.myreward.activity.database.Reward
import com.sudoajay.myreward.databinding.LayoutRewardInfoDialogBinding
import java.text.SimpleDateFormat
import java.util.*

class RewardInfoDialog(var reward: Reward) : DialogFragment() {
    private lateinit var binding: LayoutRewardInfoDialogBinding
    var date:String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.layout_reward_info_dialog,
            null,
            false
        )
        binding.dialog = this
        mainFunction()
        return binding.root
    }

    private fun mainFunction() { // Reference Object

        // setup dialog box
//        dialog!!.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        val window = dialog!!.window
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        getDate()

        val colors =
            arrayOf(
                R.drawable.reward_1,
                R.drawable.reward_2,
                R.drawable.reward_3,
                R.drawable.reward_4
            )
        binding.rewardImageView.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                if (reward.amount != 0) colors.random() else R.drawable.reward_empty
            )
        )
    }


    override fun onStart() { // This MUST be called first! Otherwise the view tweaking will not be present in the displayed Dialog (most likely overriden)
        super.onStart()
        forceWrapContent(this.view)
    }

    private fun forceWrapContent(v: View?) { // Start with the provided view
        var current = v
        val dm = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels
        val heigth = dm.heightPixels
        // Travel up the tree until fail, modifying the LayoutParams
        do { // Get the parent
            val parent = current!!.parent
            // Check if the parent exists
            if (parent != null) { // Get the view
                current = try {
                    parent as View
                } catch (e: ClassCastException) { // This will happen when at the top view, it cannot be cast to a View
                    break
                }

            }
        } while (current!!.parent != null)
        // Request a layout to be re-done
        current!!.requestLayout()
    }

    fun shareReward() {
        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        i.putExtra(Intent.EXTRA_SUBJECT, "Link-Share")
        i.putExtra(
            Intent.EXTRA_TEXT,
            getString(R.string.share_to_friend, getString(R.string.rupee_text) + reward.amount)
        )
        startActivity(Intent.createChooser(i, "Share via"))
    }

    fun getMoreReward() {

    }

    private fun getDate() {
        val sdf = SimpleDateFormat(" MMM d", Locale.getDefault())
        date = getString(R.string.paid_on_text, sdf.format(reward.date))
    }

    fun doNothing() {

    }

}