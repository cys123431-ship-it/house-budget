package com.housebudget.app.data

import kotlinx.coroutines.flow.Flow

class TransactionRepository(private val dao: BudgetDao) {
    fun getTransactions(startMillis: Long, endMillis: Long): Flow<List<BudgetTransaction>> =
        dao.getByMonth(startMillis, endMillis)

    suspend fun add(transaction: BudgetTransaction) = dao.insert(transaction)
    suspend fun delete(transaction: BudgetTransaction) = dao.delete(transaction)
}
