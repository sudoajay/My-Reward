package com.sudoajay.myreward.activity

import android.content.Context
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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.cooltechworks.views.ScratchImageView
import com.sudoajay.myreward.R
import com.sudoajay.myreward.activity.database.Reward
import com.sudoajay.myreward.databinding.LayoutRewardInfoDialogBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class RewardInfoDialog(var reward: Reward, var mainActivity: MainActivity) : DialogFragment() {
    private lateinit var binding: LayoutRewardInfoDialogBinding

    var date: String = ""
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
       dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val scratchImageView = binding.ScratchImageView
        getDate()

        val colors =
            arrayOf(
                R.drawable.reward_large_1, R.drawable.reward_large_2, R.drawable.reward_large_3
                , R.drawable.reward_large_4
            )
        binding.rewardImageView.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                if (reward.amount != 0) colors.random() else R.drawable.reward_large_empty
            )
        )


//
//        if (reward.isScratch) {
//            scratchImageView.visibility = View.VISIBLE
//            binding.bottomButton.visibility = View.GONE
//
//            scratchImageView.setRevealListener(object : ScratchImageView.IRevealListener {
//                override fun onRevealed(scr: ScratchImageView) {
//                    // on reveal
//                }
//
//                override fun onRevealPercentChangedListener(
//                    scr: ScratchImageView,
//                    percent: Float
//                ) {
//
//                    if (percent >= 0.50f) {
//                        scr.visibility = View.GONE
//                        CoroutineScope(Dispatchers.IO).launch {
//                            val amount = generateAmount(requireContext())
//                            reward =
//                                if (amount == requireContext().getString(R.string.better_luck_next_time_text)) {
//                                    Reward(
//                                        reward.id,
//                                        0,
//                                        0,
//                                        "",
//                                        "",
//                                        false,
//                                        ""
//                                    )
//                                } else {
//                                    Reward(
//                                        reward.id,
//                                        amount.toInt(), getTodayDate(), getRandomCode(),
//                                        getEarned(),
//                                        false,
//                                        getGreeting(requireContext())
//
//                                    )
//                                }
//                            mainActivity.viewModel.rewardRepository.updateInfo(
//                                reward.id!!, reward.amount.toString(), reward.date, reward.code,
//                                reward.earned, reward.greeting)
//                            mainActivity.viewModel.filterChanges.postValue(
//                                requireContext().getString(
//                                    R.string.date_sort_by
//                                )
//                            )
//                        }
//
//                    }
//                }
//            })
//
//        } else {
//            scratchImageView.visibility = View.GONE
//            binding.bottomButton.visibility = View.VISIBLE
//        }


    }


    override fun onStart() { // This MUST be called first! Otherwise the view tweaking will not be present in the displayed Dialog (most likely overriden)
        super.onStart()
        forceWrapContent(this.view)
    }

    private fun forceWrapContent(v: View?) { // Start with the provided view
        var current = v
        val dm = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(dm)
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

    companion object {
        private const val ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm"
        private fun generateAmount(context: Context): String {
            return listOf(rand().toString()).random()
        }

        private fun rand(): Int {
            return 5 * (((Random().nextInt(5000 - 5) + 5) / 5) + 1)
        }

        private fun getRandomCode(sizeOfRandomString: Int = 14): String {
            val random = Random()
            val sb = StringBuilder(sizeOfRandomString)
            for (i in 0 until sizeOfRandomString)
                sb.append(ALLOWED_CHARACTERS[random.nextInt(ALLOWED_CHARACTERS.length)])
            return sb.toString()
        }

        private fun getEarned(): String {
            val array = listOf("Amazon", "Google", "Flipkart", "Mom", "Dad", "Bro")
            return array.random()
        }

        private fun getTodayDate(): Long {
            return System.currentTimeMillis()
        }

        private fun getGreeting(context: Context): String {
            return listOf(
                context.getString(R.string.hooray_text),
                context.getString(R.string.woohoo_text),
                context.getString(R.string.fantastic_text),
                context.getString(R.string.congratulations_text),
                context.getString(R.string.awesome_text)
            ).random()
        }
    }

}