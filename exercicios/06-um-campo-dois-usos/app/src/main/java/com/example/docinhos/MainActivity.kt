package com.example.docinhos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.example.docinhos.ui.theme.ReceitasTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReceitasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Docinhos(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Docinhos(modifier: Modifier = Modifier) {
    // TODO: o texto dos dois campos precisa morar aqui, como estado
    val convidados = ""
    val porConvidado = ""

    val brigadeiros = (convidados.toIntOrNull() ?: 0) * (porConvidado.toIntOrNull() ?: 0)
    val latas = calcularLatas(brigadeiros)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.brigadeiro),
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )
        Text(
            text = brigadeiros.toString(),
            style = MaterialTheme.typography.displayLarge
        )
        Text(
            text = stringResource(R.string.brigadeiros),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.latas, formatar(latas)),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(24.dp))
        CampoNumero(rotulo = R.string.convidados)
        Spacer(modifier = Modifier.height(16.dp))
        CampoNumero(rotulo = R.string.por_convidado)
    }
}

@Composable
fun CampoNumero(
    @StringRes rotulo: Int,
    modifier: Modifier = Modifier
) {
    // TODO: este estado fica preso aqui dentro; a tela não consegue ler
    var texto by remember { mutableStateOf("") }

    TextField(
        value = texto,
        onValueChange = { texto = it },
        label = { Text(text = stringResource(rotulo)) },
        singleLine = true,
        modifier = modifier.fillMaxWidth()
    )
}

fun calcularLatas(brigadeiros: Int): Double {
    return brigadeiros / 30.0
}

fun formatar(numero: Double): String {
    val formato = NumberFormat.getNumberInstance()
    formato.maximumFractionDigits = 1
    return formato.format(numero)
}

@Preview(showBackground = true)
@Composable
fun DocinhosPreview() {
    ReceitasTheme {
        Docinhos()
    }
}
