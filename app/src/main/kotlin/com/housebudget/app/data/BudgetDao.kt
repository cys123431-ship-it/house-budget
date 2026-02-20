package com.housebudget.app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {
    @Insert
    suspend fun insert(transaction: BudgetTransaction)

    @Update
    suspend fun update(transaction: BudgetTransaction)

    @Delete
    suspend fun delete(transaction: BudgetTransaction)

    @Query("SELECT * FROM transactions ORDER BY date_millis DESC")
    fun getAll(): Flow<List<BudgetTransaction>>

    @Query(
        """
        SELECT * FROM transactions
        WHERE date_millis >= :startMillis AND date_millis < :endMillis
        ORDER BY date_millis DESC
        """
    )
    fun getByMonth(startMillis: Long, endMillis: Long): Flow<List<BudgetTransaction>>
}
