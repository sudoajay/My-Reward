package com.sudoajay.myreward.activity.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "RewardTable")
class Reward(
    @PrimaryKey(autoGenerate = true ) var id: Long?,
    @ColumnInfo(name = "Amount") val amount: Int,
    @ColumnInfo(name = "Date") val date: Long,
    @ColumnInfo(name = "Code") val code: String,
    @ColumnInfo(name = "Earned") val earned: String,
    @ColumnInfo(name = "Scratch") val isScratch: Boolean,
    @ColumnInfo(name = "Greeting") val greeting: String


    )