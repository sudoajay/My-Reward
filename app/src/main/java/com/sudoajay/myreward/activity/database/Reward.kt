package com.sudoajay.myreward.activity.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "RewardTable")
class Reward(
    @PrimaryKey(autoGenerate = true ) var id: Long?,
    @ColumnInfo(name = "amount") val amount: Int,
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "code") val code: String,
    @ColumnInfo(name = "earned") val earned: String



)