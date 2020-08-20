package com.sudoajay.myreward.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sudoajay.myreward.R
import com.sudoajay.myreward.activity.database.Reward
import com.sudoajay.myreward.databinding.LayoutRewardAdapterBinding


class RewardAdapter(private var mainActivity: MainActivity) :
    RecyclerView.Adapter<RewardAdapter.MyViewHolder>() {
    var items: List<Reward> = listOf()

    class MyViewHolder(
        layoutRewardAdapterBinding: LayoutRewardAdapterBinding
    ) :
        RecyclerView.ViewHolder(layoutRewardAdapterBinding.root) {

        var rewardImageView = layoutRewardAdapterBinding.rewardImageView
        var moneyTextView = layoutRewardAdapterBinding.moneyTextView
        var youWonTextView = layoutRewardAdapterBinding.youWonTextView
        var rewardCardView = layoutRewardAdapterBinding.rewardCardView
        var rewardScratch = layoutRewardAdapterBinding.rewardScratchImageView
        var betterTextView = layoutRewardAdapterBinding.betterTextView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: LayoutRewardAdapterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_reward_adapter, parent, false
        )

        return MyViewHolder(binding)


    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val reward = items[position]
        if (reward.isScratch) {
            holder.rewardScratch.visibility = View.VISIBLE
        } else {
            holder.rewardScratch.visibility = View.GONE

            val colors =
                arrayOf(
                    R.drawable.reward_1,
                    R.drawable.reward_2,
                    R.drawable.reward_3,
                    R.drawable.reward_4
                )
            holder.rewardImageView.setImageDrawable(
                ContextCompat.getDrawable(
                    mainActivity,
                    if (reward.amount != 0) colors.random() else R.drawable.reward_empty
                )
            )
            holder.moneyTextView.text =
                mainActivity.getString(
                    R.string.money_text,
                    mainActivity.getString(R.string.rupee_text),
                    reward.amount.toString()
                )

            if (reward.amount != 0) {
                holder.youWonTextView.visibility = View.VISIBLE
                holder.moneyTextView.visibility = View.VISIBLE
                holder.betterTextView.visibility = View.GONE
            } else {
                holder.youWonTextView.visibility = View.GONE
                holder.moneyTextView.visibility = View.GONE
                holder.betterTextView.visibility = View.VISIBLE
            }

        }

        holder.rewardCardView.setOnClickListener {
            mainActivity.callRewardInfo(reward)
        }
    }

}


