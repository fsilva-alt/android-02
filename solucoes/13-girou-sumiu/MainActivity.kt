package com.example.livrodereceitas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.livrodereceitas.ui.theme.ReceitasTheme
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

// As chaves: uma para cada tela do app.
@Serializable
data object Livro : NavKey

@Serializable
data class Receita(val numero: Int) : NavKey

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReceitasTheme {
                LivroApp()
            }
        }
    }
}

@Composable
fun LivroApp() {
    // A pilha de telas: a última chave da lista é a tela que aparece.
    val pilha = rememberNavBackStack(Livro)
    // O número da receita favorita. Zero quer dizer que ainda não há favorita.
    var favorita by rememberSaveable { mutableStateOf(0) }
    val avisos = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val mensagem = stringResource(R.string.favoritada)
    val desfazer = stringResource(R.string.desfazer)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(avisos) }
    ) { innerPadding ->
        NavDisplay(
            backStack = pilha,
            modifier = Modifier.padding(innerPadding),
            onBack = { pilha.removeLastOrNull() },
            entryProvider = entryProvider {
                entry<Livro> {
                    TelaLivro(
                        favorita = favorita,
                        aoVerReceita = { numero -> pilha.add(Receita(numero)) }
                    )
                }
                entry<Receita> { chave ->
                    TelaReceita(
                        numero = chave.numero,
                        favorita = favorita,
                        aoFavoritar = {
                            val anterior = favorita
                            favorita = chave.numero
                            scope.launch {
                                val resultado = avisos.showSnackbar(
                                    message = mensagem,
                                    actionLabel = desfazer,
                                    duration = SnackbarDuration.Long
                                )
                                if (resultado == SnackbarResult.ActionPerformed) {
                                    favorita = anterior
                                }
                            }
                        },
                        aoVoltar = { pilha.removeLastOrNull() }
                    )
                }
            }
        )
    }
}
