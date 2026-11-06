package com.example.livrodereceitas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.livrodereceitas.ui.theme.ReceitasTheme

// A primeira tela: o livro, com uma receita por página.
// Ao tocar em "Ver receita", o livro avisa qual é o número da receita aberta.
@Composable
fun TelaLivro(
    aoVerReceita: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    // rememberSaveable: o livro lembra da página enquanto a tela da receita está na frente.
    var receita by rememberSaveable { mutableStateOf(1) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
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
            painter = painterResource(imagemDa(receita)),
            contentDescription = null,
            modifier = Modifier.size(280.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(nomeDa(receita)),
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = stringResource(detalhesDa(receita)),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { aoVerReceita(receita) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.ver_receita))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            OutlinedButton(
                onClick = { receita = if (receita == 1) 6 else receita - 1 },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = stringResource(R.string.anterior))
            }
            OutlinedButton(
                onClick = { receita = if (receita == 6) 1 else receita + 1 },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = stringResource(R.string.proxima))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaLivroPreview() {
    ReceitasTheme {
        TelaLivro(aoVerReceita = {})
    }
}
