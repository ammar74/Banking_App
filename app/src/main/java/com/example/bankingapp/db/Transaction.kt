package com.example.bankingapp.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "transaction_table")
@Parcelize
data class Transaction (
    val date: Long,
    val transferor: String,
    val transferee: String,
    var amount: Double,
    @PrimaryKey(autoGenerate = true) val transaction_id: Int  = 0,
): Parcelable

