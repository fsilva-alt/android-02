package com.example.pratododia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pratododia.ui.theme.ReceitasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReceitasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PratoDoDia(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun PratoDoDia(modifier: Modifier = Modifier) {
    var prato by remember { mutableStateOf(1) }

    val imagem = when (prato) {
        1 -> R.drawable.brigadeiro
        2 -> R.drawable.pao_de_queijo
        3 -> R.drawable.bolo_de_cenoura
        4 -> R.drawable.pudim
        5 -> R.drawable.coxinha
        6 -> R.drawable.acai
        else -> R.drawable.prato_vazio
    }
    val nome = when (prato) {
        1 -> R.string.brigadeiro
        2 -> R.string.pao_de_queijo
        3 -> R.string.bolo_de_cenoura
        4 -> R.string.pudim
        5 -> R.string.coxinha
        6 -> R.string.acai
        else -> R.string.prato_vazio
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.prato_do_dia),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(imagem),
            contentDescription = null,
            modifier = Modifier.size(280.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(nome),
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                val sorteado = (0..6).random()
                prato = sorteado
            }
        ) {
            Text(text = stringResource(R.string.sortear))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PratoDoDiaPreview() {
    ReceitasTheme {
        PratoDoDia()
    }
}
