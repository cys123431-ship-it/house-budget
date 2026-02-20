package com.housebudget.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.housebudget.app.data.BudgetTransaction
import com.housebudget.app.data.TransactionType
import com.housebudget.app.ui.theme.BrightBlue
import com.housebudget.app.ui.theme.NegativeRed
import com.housebudget.app.ui.theme.PositiveGreen
import com.housebudget.app.ui.theme.SlateText
import com.housebudget.app.ui.theme.SoftBlue
import java.text.DecimalFormat
import java.util.Locale

private val amountFormatter = DecimalFormat("#,###")

@Composable
fun BudgetRoute(viewModel: MainViewModel = viewModel()) {
    val transactions by viewModel.transactions.collectAsStateWithLifecycle()
    val monthLabel by viewModel.monthLabel.collectAsStateWithLifecycle()

    val income = transactions
        .filter { it.type == TransactionType.INCOME }
        .sumOf { it.amount }
    val expense = transactions
        .filter { it.type == TransactionType.EXPENSE }
        .sumOf { it.amount }
    val balance = income - expense
    var showAddDialog by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("가계부") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = BrightBlue
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "거래 추가",
                    tint = Color.White
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MonthSection(
                label = monthLabel,
                onPrev = { viewModel.moveMonth(-1) },
                onNext = { viewModel.moveMonth(1) }
            )
            SummaryRow(income = income, expense = expense, balance = balance)

            Text(
                text = "이번 달 내역",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 4.dp, bottom = 2.dp)
            )

            if (transactions.isEmpty()) {
                Text(
                    text = "아직 내역이 없습니다. + 버튼으로 거래를 추가하세요.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SlateText.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 50.dp)
                )
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(transactions) { item ->
                        TransactionItem(
                            transaction = item,
                            onDelete = { viewModel.deleteTransaction(item) }
                        )
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddTransactionDialog(
            categories = viewModel.categories,
            onDismiss = { showAddDialog = false },
            onSubmit = { title, type, amount, category, note ->
                val ok = viewModel.addTransaction(title, type, amount, category, note)
                if (ok) {
                    showAddDialog = false
                }
                ok
            }
        )
    }
}

@Composable
private fun MonthSection(label: String, onPrev: () -> Unit, onNext: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedButton(onClick = onPrev) { Text("이전") }
        Text(text = label, style = MaterialTheme.typography.titleLarge)
        OutlinedButton(onClick = onNext) { Text("다음") }
    }
}

@Composable
private fun SummaryRow(income: Double, expense: Double, balance: Double) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SummaryCard(title = "수입", value = income, valueColor = PositiveGreen, modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.width(8.dp))
        SummaryCard(title = "지출", value = expense, valueColor = NegativeRed, modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.width(8.dp))
        SummaryCard(title = "잔액", value = balance, valueColor = if (balance >= 0) PositiveGreen else NegativeRed, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun SummaryCard(title: String, value: Double, valueColor: Color, modifier: Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = SlateText
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${amountFormatter.format(value)}원",
                style = MaterialTheme.typography.titleSmall,
                color = valueColor
            )
        }
    }
}

@Composable
private fun TransactionItem(
    transaction: BudgetTransaction,
    onDelete: () -> Unit
) {
    val sign = if (transaction.type == TransactionType.INCOME) "+" else "-"
    val color = if (transaction.type == TransactionType.INCOME) PositiveGreen else NegativeRed

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = transaction.title,
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${transaction.category} · ${if (transaction.type == TransactionType.INCOME) "수입" else "지출"}",
                    style = MaterialTheme.typography.labelSmall,
                    color = SlateText.copy(alpha = 0.7f)
                )
                if (transaction.note.isNotBlank()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = transaction.note,
                        style = MaterialTheme.typography.bodySmall,
                        color = SlateText.copy(alpha = 0.7f)
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "$sign${amountFormatter.format(transaction.amount)}원",
                    style = MaterialTheme.typography.titleSmall,
                    color = color
                )
                Spacer(modifier = Modifier.width(6.dp))
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "삭제",
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
private fun AddTransactionDialog(
    categories: List<String>,
    onDismiss: () -> Unit,
    onSubmit: (String, TransactionType, String, String, String) -> Boolean
) {
    var title by rememberSaveable { mutableStateOf("") }
    var amount by rememberSaveable { mutableStateOf("") }
    var note by rememberSaveable { mutableStateOf("") }
    var type by rememberSaveable { mutableStateOf(TransactionType.EXPENSE) }
    var category by rememberSaveable { mutableStateOf(categories.firstOrNull() ?: "기타") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("거래 추가") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilledTonalButton(
                        onClick = { type = TransactionType.EXPENSE },
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = if (type == TransactionType.EXPENSE) SoftBlue else Color.Transparent
                        )
                    ) {
                        Text("지출")
                    }
                    FilledTonalButton(
                        onClick = { type = TransactionType.INCOME },
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = if (type == TransactionType.INCOME) SoftBlue else Color.Transparent
                        )
                    ) {
                        Text("수입")
                    }
                }

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("항목명") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("금액") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text("메모(선택)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Text("카테고리", style = MaterialTheme.typography.labelMedium)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(categories) { c ->
                        FilterChip(
                            selected = category == c,
                            onClick = { category = c },
                            label = { Text(c) }
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onSubmit(title, type, amount, category, note)
                }
            ) { Text("추가") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("취소") }
        }
    )
}
