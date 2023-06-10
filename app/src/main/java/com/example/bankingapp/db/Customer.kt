package com.example.bankingapp.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
@Entity(tableName = "customer_table")
@Parcelize
data class Customer(
    val name: String,
    val email: String,
    val balance: Double,
    @PrimaryKey(autoGenerate = true) val customerId: Int=0,
): Parcelable
