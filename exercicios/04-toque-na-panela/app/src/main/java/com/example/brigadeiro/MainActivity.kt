package com.example.brigadeiro

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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.brigadeiro.ui.theme.ReceitasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReceitasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Brigadeiro(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Brigadeiro(modifier: Modifier = Modifier) {
    var passo by remember { mutableStateOf(1) }
    // TODO 2: crie aqui o estado que conta as mexidas que faltam

    val imagem = when (passo) {
        1 -> R.drawable.panela_ingredientes
        2 -> R.drawable.panela_mexendo
        3 -> R.drawable.bolinhas
        else -> R.drawable.brigadeiro
    }
    val instrucao = when (passo) {
        1 -> R.string.passo_1
        2 -> R.string.passo_2
        3 -> R.string.passo_3
        else -> R.string.passo_4
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.passo_n, passo),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(imagem),
            contentDescription = null,
            modifier = Modifier
                .size(280.dp)
                .clip(CircleShape)
                // TODO 1: faça a imagem responder ao toque
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(instrucao),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            minLines = 2
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            // TODO 3: no passo 2, mostre quantas mexidas faltam
            text = stringResource(R.string.toque),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BrigadeiroPreview() {
    ReceitasTheme {
        Brigadeiro()
    }
}
