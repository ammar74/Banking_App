package com.example.bankingapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_table")
data class Transaction (
    val date: Long,
    val transferor: String,
    val transferee: String,
    var amount: Double,
    @PrimaryKey(autoGenerate = true) val transaction_id: Int  = 0,
)
