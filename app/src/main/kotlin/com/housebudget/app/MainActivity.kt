package com.housebudget.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.housebudget.app.ui.BudgetRoute
import com.housebudget.app.ui.theme.HouseBudgetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HouseBudgetTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    BudgetRoute()
                }
            }
        }
    }
}
