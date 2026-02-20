package com.housebudget.app.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.housebudget.app.data.BudgetDatabase
import com.housebudget.app.data.BudgetTransaction
import com.housebudget.app.data.TransactionRepository
import com.housebudget.app.data.TransactionType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = TransactionRepository(
        BudgetDatabase.getInstance(application).budgetDao()
    )

    private val _monthOffset = MutableStateFlow(0)
    val monthLabel: StateFlow<String> = _monthOffset
        .map { offset -> monthLabel(offset) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), monthLabel(0))

    @OptIn(ExperimentalCoroutinesApi::class)
    val transactions = _monthOffset
        .flatMapLatest { offset ->
            val (start, end) = monthRange(offset)
            repository.getTransactions(start, end)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val categories = listOf(
        "식비",
        "교통",
        "문화",
        "생활",
        "쇼핑",
        "급여",
        "건강",
        "기타"
    )

    fun moveMonth(delta: Int) {
        _monthOffset.value += delta
    }

    fun addTransaction(
        title: String,
        type: TransactionType,
        amountText: String,
        category: String,
        note: String
    ): Boolean {
        val parsedAmount = amountText.trim().replace(",", "").toDoubleOrNull() ?: return false
        if (parsedAmount <= 0.0 || title.isBlank() || category.isBlank()) return false

        viewModelScope.launch {
            repository.add(
                BudgetTransaction(
                    title = title.trim(),
                    amount = parsedAmount,
                    category = category,
                    type = type,
                    note = note.trim()
                )
            )
        }
        return true
    }

    fun deleteTransaction(transaction: BudgetTransaction) {
        viewModelScope.launch { repository.delete(transaction) }
    }

    private fun monthLabel(offset: Int): String {
        val calendar = Calendar.getInstance().apply {
            add(Calendar.MONTH, offset)
            set(Calendar.DAY_OF_MONTH, 1)
        }
        val format = SimpleDateFormat("yyyy년 M월", Locale.getDefault())
        return format.format(calendar.time)
    }

    private fun monthRange(offset: Int): Pair<Long, Long> {
        val start = Calendar.getInstance().apply {
            add(Calendar.MONTH, offset)
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val end = Calendar.getInstance().apply {
            timeInMillis = start.timeInMillis
            add(Calendar.MONTH, 1)
        }
        return Pair(start.timeInMillis, end.timeInMillis)
    }
}
