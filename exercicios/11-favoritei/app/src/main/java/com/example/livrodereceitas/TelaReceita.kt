package com.example.livrodereceitas

import android.content.Intent
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.livrodereceitas.ui.theme.ReceitasTheme

// A segunda tela: os ingredientes e o modo de preparo de uma receita.
// "favorita" é o número da receita favorita do livro; zero quer dizer que não há.
@Composable
fun TelaReceita(
    numero: Int,
    favorita: Int,
    aoFavoritar: () -> Unit,
    aoVoltar: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val nome = stringResource(nomeDa(numero))
    val ingredientes = stringResource(ingredientesDa(numero))
    val preparo = stringResource(preparoDa(numero))
    // TODO 3: crie o estado que diz se a pergunta está na tela

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(imagemDa(numero)),
                contentDescription = null,
                modifier = Modifier.size(96.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = nome,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = stringResource(detalhesDa(numero)),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (favorita == numero) {
                    Text(
                        text = stringResource(R.string.sua_favorita),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.ingredientes),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = ingredientes,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.modo_de_preparo),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = preparo,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            // TODO 4: se o livro já tem favorita, abra a pergunta em vez de trocar direto
            onClick = aoFavoritar,
            enabled = favorita != numero,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.favoritar))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            OutlinedButton(
                onClick = aoVoltar,
                modifier = Modifier.weight(1f)
            ) {
                Text(text = stringResource(R.string.voltar))
            }
            OutlinedButton(
                onClick = {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    intent.putExtra(Intent.EXTRA_TEXT, "$nome\n\n$ingredientes\n\n$preparo")
                    context.startActivity(Intent.createChooser(intent, null))
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = stringResource(R.string.compartilhar))
            }
        }
    }

    // TODO 5: enquanto a pergunta estiver aberta, mostre o AlertDialog
}

@Preview(showBackground = true)
@Composable
fun TelaReceitaPreview() {
    ReceitasTheme {
        TelaReceita(numero = 1, favorita = 0, aoFavoritar = {}, aoVoltar = {})
    }
}
