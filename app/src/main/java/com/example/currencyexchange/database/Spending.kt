package com.example.currencyexchange.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "spending")
data class Spending(

    @ColumnInfo(name="description")
    val description: String,

    @ColumnInfo(name="cost")
    val cost: Double = 0.00,

    @ColumnInfo(name="type")
    val type: Int,

    @ColumnInfo(name="currency")
    val currency: String,

    @PrimaryKey(autoGenerate = true)
    val spendingId: Int = 0

) : Parcelable