package com.housebudget.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [BudgetTransaction::class], version = 1, exportSchema = false)
@TypeConverters(TransactionTypeConverter::class)
abstract class BudgetDatabase : RoomDatabase() {
    abstract fun budgetDao(): BudgetDao

    companion object {
        private const val DB_NAME = "house_budget.db"
        @Volatile
        private var instance: BudgetDatabase? = null

        fun getInstance(context: Context): BudgetDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    BudgetDatabase::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration().build().also { instance = it }
            }
        }
    }
}
