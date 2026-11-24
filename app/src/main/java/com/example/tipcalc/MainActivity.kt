package com.example.tipcalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import kotlin.math.ceil

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                TipCalcScreen()
            }
        }
    }
}

@Composable
fun TipCalcScreen() {

    // Your personalized values
    val n = 2014
    val defaultPeople = 2 + (n % 8)
    val roundingStep = 0.05 * (1 + (n % 4))
    val defaultTip = (5 + (n % 16)) / 100f

    var billText by rememberSaveable {
        mutableStateOf("")
    }

    var people by rememberSaveable {
        mutableIntStateOf(defaultPeople)
    }

    var tipPercent by rememberSaveable {
        mutableFloatStateOf(defaultTip)
    }

    val bill = billText.toDoubleOrNull() ?: 0.0

    val tip = bill * tipPercent

    val total = bill + tip

    val perPerson = ceil(
        (total / people) / roundingStep
    ) * roundingStep

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),

        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            "Tip Calculator",
            style = MaterialTheme.typography.headlineMedium
        )

        Text("Name: Nailah Bukola Abdulrasheed")

        Text("Matric Number: U24CP2014")

        OutlinedTextField(
            value = billText,
            onValueChange = { billText = it },
            label = { Text("Bill amount") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Text("Tip: ${(tipPercent * 100).toInt()}%")

        Slider(
            value = tipPercent,
            onValueChange = { tipPercent = it },
            valueRange = 0f..0.30f,
            steps = 5
        )

        Text("Number of People: $people")

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Button(
                onClick = {
                    if (people > 1) people--
                }
            ) {
                Text("-")
            }

            Button(
                onClick = {
                    people++
                }
            ) {
                Text("+")
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Text("Tip: $${"%.2f".format(tip)}")

                Text("Total: $${"%.2f".format(total)}")

                Text(
                    "Per Person: $${"%.2f".format(perPerson)}"
                )
            }
        }

        Button(
            onClick = {
                billText = ""
                tipPercent = defaultTip
                people = defaultPeople
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Clear")
        }
    }
}