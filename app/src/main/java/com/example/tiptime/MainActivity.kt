package com.example.tiptime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tiptime.ui.theme.TipTimeTheme
import java.text.NumberFormat
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipTimeTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TipTimeLayout()
                }
            }
        }
    }
}

@Composable
fun TipTimeLayout() {
    var amountInput by remember { mutableStateOf("") }
    var tipPercentInput by remember { mutableStateOf("") }

    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val tipPercent = tipPercentInput.toDoubleOrNull() ?: 10.0
    val tip = calculateTip(amount, tipPercent)

    Column(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.calculate_tip),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        EditNumberField(
            label = stringResource(R.string.bill_amount),
            value = amountInput,
            onValueChange = { amountInput = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        EditNumberField(
            label = stringResource(R.string.tip_percent),
            value = tipPercentInput,
            onValueChange = { tipPercentInput = it }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = stringResource(R.string.tip_amount, tip),
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Composable
fun EditNumberField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth()
    )
}

private fun calculateTip(amount: Double, tipPercent: Double = 10.0): String {
    val tip = tipPercent / 100 * amount

    // Formata para o formato de moeda BR (R$)
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    return currencyFormat.format(tip)
}

@Preview(showBackground = true)
@Composable
fun TipTimeLayoutPreview() {
    TipTimeTheme {
        TipTimeLayout()
    }
}
