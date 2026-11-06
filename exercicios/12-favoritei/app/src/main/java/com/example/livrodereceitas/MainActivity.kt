package com.example.livrodereceitas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.livrodereceitas.ui.theme.ReceitasTheme

// As chaves: uma para cada tela do app.
data object Livro
data class Receita(val numero: Int)

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
    val pilha = remember { mutableStateListOf<Any>(Livro) }
    // O número da receita favorita. Zero quer dizer que ainda não há favorita.
    var favorita by remember { mutableStateOf(0) }
    // TODO 1: crie o estado dos avisos e entregue-o ao Scaffold

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
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
                            favorita = chave.numero
                            // TODO 2: mostre o aviso, com a ação "Desfazer"
                        },
                        aoVoltar = { pilha.removeLastOrNull() }
                    )
                }
            }
        )
    }
}
