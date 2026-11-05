package com.example.livrodereceitas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.livrodereceitas.ui.theme.ReceitasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReceitasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LivroDeReceitas(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun LivroDeReceitas(modifier: Modifier = Modifier) {
    // TODO 1: a receita atual precisa ser estado
    val receita = 1

    // TODO 2: escolha a imagem pela receita atual
    val imagem = R.drawable.brigadeiro
    val nome = when (receita) {
        1 -> R.string.brigadeiro
        2 -> R.string.pao_de_queijo
        3 -> R.string.bolo_de_cenoura
        4 -> R.string.pudim
        5 -> R.string.coxinha
        else -> R.string.acai
    }
    val detalhes = when (receita) {
        1 -> R.string.brigadeiro_detalhes
        2 -> R.string.pao_de_queijo_detalhes
        3 -> R.string.bolo_de_cenoura_detalhes
        4 -> R.string.pudim_detalhes
        5 -> R.string.coxinha_detalhes
        else -> R.string.acai_detalhes
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.receita_n, receita),
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
        Text(
            text = stringResource(detalhes),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(32.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            OutlinedButton(
                onClick = { /* TODO 4: volte uma receita; antes da primeira vem a última */ },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = stringResource(R.string.anterior))
            }
            Button(
                onClick = { /* TODO 3: avance uma receita; depois da última vem a primeira */ },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = stringResource(R.string.proxima))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LivroDeReceitasPreview() {
    ReceitasTheme {
        LivroDeReceitas()
    }
}
