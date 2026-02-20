package com.housebudget.app.data

import androidx.room.TypeConverter

enum class TransactionType {
    INCOME,
    EXPENSE
}

class TransactionTypeConverter {
    @TypeConverter
    fun fromType(type: TransactionType): String = type.name

    @TypeConverter
    fun toType(value: String): TransactionType = TransactionType.valueOf(value)
}
