package com.housebudget.app.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class BudgetTransaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val amount: Double,
    val category: String,
    val type: TransactionType,
    val note: String = "",
    @ColumnInfo(name = "date_millis")
    val dateMillis: Long = System.currentTimeMillis()
)
