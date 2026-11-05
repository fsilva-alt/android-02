---
duration: 24
---

# Favoritei!

{.lead}
O livro guarda uma receita favorita. O botão **☆ Favoritar** já funciona, mas o app não diz nada quando a favorita muda, e troca a favorita antiga sem perguntar. Dois recursos do Material 3 resolvem: o `Snackbar` avisa **depois** que algo aconteceu; o `AlertDialog` pergunta **antes**.

::::columns
:::card Neste módulo
- `Snackbar`: um aviso curto na base da tela, com uma ação.
- `scope.launch`: mostrar o aviso sem travar a tela.
- `AlertDialog`: uma pergunta que espera a resposta.
:::
:::card Projeto
- Abra `exercicios/11-favoritei`.
- App **Livro de Receitas**, com as duas telas e o botão **☆ Favoritar** na tela da receita.
:::
::::

<div class="phones">
<figure class="figure">
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/11-antes.png" alt="Tela da receita do pudim de leite marcada como Sua favorita, sem nenhum aviso"></div></div>
<figcaption>Como começa: a receita vira favorita, e o app não diz nada.</figcaption>
</figure>
<figure class="figure">
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/11-meio.png" alt="Tela da receita do pudim de leite com o aviso Receita favorita atualizada e a ação Desfazer na base da tela"></div></div>
<figcaption>No meio do caminho: um aviso, com a chance de desfazer.</figcaption>
</figure>
<figure class="figure">
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/11-depois.png" alt="Tela da receita da coxinha com a caixa de diálogo Trocar a favorita, com os botões Cancelar e Trocar"></div></div>
<figcaption>Como fica: antes de trocar a favorita, o app pergunta.</figcaption>
</figure>
</div>

## O que já vem pronto

A favorita é um número: o da receita, ou 0 quando ainda não há favorita. Esse estado mora no `LivroApp`, porque as duas telas precisam dele.

| Quem | Recebe | Faz |
| --- | --- | --- |
| `LivroApp` | | Guarda `favorita` e muda o valor quando a tela da receita avisa. |
| `TelaLivro` | `favorita` | Mostra "★ favorita" na página da receita favorita. |
| `TelaReceita` | `favorita`, `aoFavoritar` | Mostra o botão **☆ Favoritar** e avisa do toque. |

É a elevação de estado do módulo 6: o valor desce para as telas, o aviso sobe. A tela da receita também ganhou o botão **Compartilhar**, a missão extra do módulo 10.

## Avisar ou perguntar

| | `Snackbar` | `AlertDialog` |
| --- | --- | --- |
| **Quando** | Depois: confirma o que acabou de acontecer. | Antes: pede uma decisão. |
| **A tela** | Continua em uso. | Fica bloqueada até a resposta. |
| **Some** | Sozinho, em alguns segundos. | Só com a resposta. |
| **Neste app** | "Receita favorita atualizada", com **Desfazer**. | "Trocar a favorita?" |

Um diálogo interrompe. Use-o quando a ação joga fora algo que a pessoa escolheu; para o resto, um aviso com **Desfazer** incomoda menos.

## Snackbar: três peças

```kotlin
val avisos = remember { SnackbarHostState() }
val scope = rememberCoroutineScope()

Scaffold(
    snackbarHost = { SnackbarHost(avisos) }
) { /* a tela */ }

// em um toque:
scope.launch {
    avisos.showSnackbar("Receita favorita atualizada")
}
```

| Peça | Papel |
| --- | --- |
| `SnackbarHostState` | O estado dos avisos: sabe qual está na tela e guarda a fila dos próximos. |
| `SnackbarHost` | Onde o aviso é desenhado. O `Scaffold` o põe na base da tela. |
| `showSnackbar(…)` | Mostra o aviso e **espera** ele sair. |

## Por que scope.launch

`showSnackbar` só termina quando o aviso sai da tela. Se o toque esperasse por isso, o app ficaria parado. `scope.launch { … }` deixa o bloco rodando por conta própria: o toque termina na hora, e o aviso segue o seu tempo.

- Um bloco desses é uma **corrotina**. `showSnackbar` só pode ser chamada dentro de uma.
- `rememberCoroutineScope()` devolve o lugar onde as corrotinas da tela rodam. Se a tela sair, elas são canceladas.

## Uma ação no aviso

```kotlin
val resultado = avisos.showSnackbar(
    message = "Receita favorita atualizada",
    actionLabel = "Desfazer",
    duration = SnackbarDuration.Long
)
if (resultado == SnackbarResult.ActionPerformed) {
    // tocaram em Desfazer
}
```

- `showSnackbar` devolve o que aconteceu: `ActionPerformed`, se tocaram na ação, ou `Dismissed`, se o aviso saiu sem toque.
- Um aviso sem ação dura uns 4 segundos. Com ação, ele fica até alguém tocar; `duration` põe um limite. `SnackbarDuration.Long` são uns 10 segundos.

:::note E o Toast?
`Toast.makeText(context, "Texto", Toast.LENGTH_SHORT).show()` mostra uma mensagem curta em uma linha de código. É o recurso mais antigo: não tem ação, não faz parte da tela do app e não segue o tema. A documentação do Android recomenda o `Snackbar` quando o app está na frente.
:::

## AlertDialog: aparece enquanto o estado mandar

```kotlin
var perguntando by remember { mutableStateOf(false) }

if (perguntando) {
    AlertDialog(
        onDismissRequest = { perguntando = false },
        title = { Text(text = "Trocar a favorita?") },
        text = { Text(text = "O livro guarda uma favorita só.") },
        confirmButton = {
            TextButton(onClick = { perguntando = false }) {
                Text(text = "Trocar")
            }
        },
        dismissButton = {
            TextButton(onClick = { perguntando = false }) {
                Text(text = "Cancelar")
            }
        }
    )
}
```

O diálogo é um composable como os outros: está na tela enquanto o `if` for verdadeiro. Abrir é `perguntando = true`; fechar é `perguntando = false`.

| Parâmetro | Papel |
| --- | --- |
| `onDismissRequest` | Chamado no toque fora da caixa e no gesto de voltar. |
| `title`, `text` | A pergunta e a explicação. |
| `confirmButton` | O botão que confirma. Escreva a ação: "Trocar", e não "OK". |
| `dismissButton` | O botão que desiste. |

O `AlertDialog` não se fecha sozinho. Todo caminho precisa pôr `perguntando` em `false`.

## Para saber mais

- [Snackbar](https://developer.android.com/develop/ui/compose/components/snackbar?hl=pt-br): o aviso, a ação e o `SnackbarHostState`.
- [Caixas de diálogo](https://developer.android.com/develop/ui/compose/components/dialog?hl=pt-br): `AlertDialog` e `Dialog`.
- [Scaffold](https://developer.android.com/develop/ui/compose/components/scaffold?hl=pt-br): a estrutura da tela e os lugares que ela reserva.
- [Efeitos colaterais no Compose](https://developer.android.com/develop/ui/compose/side-effects?hl=pt-br#remembercoroutinescope): `rememberCoroutineScope` e corrotinas disparadas por um toque.
- [Visão geral dos Toasts](https://developer.android.com/guide/topics/ui/notifiers/toasts?hl=pt-br): o que são e quando ainda servem.
- Material Design: [Snackbar](https://m3.material.io/components/snackbar/guidelines) e [Dialogs](https://m3.material.io/components/dialogs/guidelines) (em inglês), com as regras de uso de cada um.

## O desafio

O projeto tem cinco `TODO`, numerados: dois em `MainActivity.kt`, para o aviso, e três em `TelaReceita.kt`, para a pergunta.

:::steps
1. Rode o projeto. Abra uma receita e toque em **☆ Favoritar**: aparece "★ Sua favorita", e mais nada. Volte, abra outra receita e favorite: a primeira perde o lugar sem aviso.
2. **TODO 1.** Em `MainActivity.kt`, no `LivroApp`, crie o estado dos avisos e entregue-o ao `Scaffold`:

   ```kotlin title="MainActivity.kt" hl_lines="2 6"
   var favorita by remember { mutableStateOf(0) }
   val avisos = remember { SnackbarHostState() }

   Scaffold(
       modifier = Modifier.fillMaxSize(),
       snackbarHost = { SnackbarHost(avisos) }
   ) { innerPadding ->
   ```

3. **TODO 2.** Abaixo de `avisos`, prepare o que o aviso usa. `stringResource` só pode ser chamado aqui, na composição, e não dentro de um toque:

   ```kotlin title="MainActivity.kt" hl_lines="2 3"
   val avisos = remember { SnackbarHostState() }
   val scope = rememberCoroutineScope()
   val mensagem = stringResource(R.string.favoritada)
   ```

4. Em `aoFavoritar`, mostre o aviso:

   ```kotlin title="MainActivity.kt" hl_lines="3-5"
   aoFavoritar = {
       favorita = chave.numero
       scope.launch {
           avisos.showSnackbar(mensagem)
       }
   },
   ```

5. Importe com <kbd>Alt</kbd>+<kbd>Enter</kbd> o que ficou vermelho: `SnackbarHostState`, `SnackbarHost`, `rememberCoroutineScope`, `stringResource` e `launch`. Rode e favorite uma receita. O aviso aparece na base da tela e some sozinho.
6. Agora o **Desfazer**. Crie o texto da ação junto de `mensagem`:

   ```kotlin title="MainActivity.kt" hl_lines="2"
   val mensagem = stringResource(R.string.favoritada)
   val desfazer = stringResource(R.string.desfazer)
   ```

7. Em `aoFavoritar`, guarde a favorita anterior antes de trocar e dê a ação ao aviso:

   ```kotlin title="MainActivity.kt" hl_lines="2 5-12"
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
   ```

8. Importe `SnackbarDuration` e `SnackbarResult` e rode. Favorite uma receita e toque em **Desfazer**.
9. **TODO 3.** Abra `TelaReceita.kt`. Crie o estado da pergunta, abaixo de `preparo`:

   ```kotlin title="TelaReceita.kt" hl_lines="2"
   val preparo = stringResource(preparoDa(numero))
   var perguntando by remember { mutableStateOf(false) }
   ```

10. **TODO 4.** No botão **☆ Favoritar**, troque `onClick = aoFavoritar` por uma escolha. Se o livro já tem favorita, o toque só abre a pergunta:

    ```kotlin title="TelaReceita.kt" hl_lines="2-8"
    Button(
        onClick = {
            if (favorita != 0) {
                perguntando = true
            } else {
                aoFavoritar()
            }
        },
        enabled = favorita != numero,
        modifier = Modifier.fillMaxWidth()
    ) {
    ```

11. **TODO 5.** No fim de `TelaReceita`, depois da `Column`, mostre a pergunta:

    ```kotlin title="TelaReceita.kt"
    if (perguntando) {
        AlertDialog(
            onDismissRequest = { perguntando = false },
            title = { Text(text = stringResource(R.string.trocar_titulo)) },
            text = {
                Text(text = stringResource(R.string.trocar_texto, stringResource(nomeDa(favorita))))
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        perguntando = false
                        aoFavoritar()
                    }
                ) {
                    Text(text = stringResource(R.string.trocar))
                }
            },
            dismissButton = {
                TextButton(onClick = { perguntando = false }) {
                    Text(text = stringResource(R.string.cancelar))
                }
            }
        )
    }
    ```

12. Importe `AlertDialog`, `TextButton`, `remember`, `mutableStateOf` e, sobre o `by`, `getValue` e `setValue`. Rode: favorite uma receita, volte ao livro e favorite outra.
:::

:::tip Qual launch importar?
O <kbd>Alt</kbd>+<kbd>Enter</kbd> pode oferecer mais de um `launch`. O certo é `kotlinx.coroutines.launch`.
:::

## Resultado

- [ ] Ao favoritar a primeira receita, aparece o aviso "Receita favorita atualizada", com **Desfazer**.
- [ ] **Desfazer** devolve a favorita anterior.
- [ ] Com uma favorita já escolhida, favoritar outra abre a pergunta "Trocar a favorita?".
- [ ] **Cancelar**, o toque fora da caixa e o gesto de voltar fecham a pergunta sem trocar nada.
- [ ] **Trocar** troca a favorita, e o aviso aparece.
- [ ] No livro, a página da favorita mostra "★ favorita".

## Missão extra

:::exercise O aviso acompanha a navegação?
Favorite uma receita e toque em **Voltar** antes de o aviso sumir. O aviso continua na tela? E o **Desfazer**, ainda funciona no livro? Explique.
:::

:::solution
Continua, e funciona: a estrela some da página. O `Scaffold`, com o `SnackbarHost`, está no `LivroApp`, por fora do `NavDisplay`. Trocar de tela muda só o que o `NavDisplay` mostra; o aviso e a corrotina que espera por ele ficam onde estavam.
:::

:::exercise Compare com um Toast
No `aoFavoritar`, troque o `scope.launch { … }` por um Toast. Você vai precisar do contexto, com `val context = LocalContext.current` no começo do `LivroApp`. O que se perde? Volte para o `Snackbar` ao terminar.
:::

:::solution
```kotlin title="MainActivity.kt"
val context = LocalContext.current

// … em aoFavoritar:
favorita = chave.numero
Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show()
```

Perde-se o **Desfazer**: o Toast só mostra texto. Ele também aparece fora do `Scaffold`, com a aparência do sistema, e não entra na fila do `SnackbarHostState`. Em compensação, é uma linha só e não pede corrotina.
:::

## Teste o que aprendeu

:::quiz Por que `showSnackbar` fica dentro de `scope.launch { … }`?
- [ ] Para o aviso aparecer mais rápido
- [x] Porque ela espera o aviso sair da tela, e essa espera não pode travar o toque
- [ ] Porque o `Scaffold` exige
- [ ] Para o aviso seguir o tema do app

`showSnackbar` só termina quando o aviso some ou alguém toca na ação. Por isso ela só pode ser chamada dentro de uma corrotina, e `scope.launch` cria uma.
:::

:::quiz O usuário toca fora da caixa do `AlertDialog`. O que faz o diálogo sair da tela?
- [ ] O próprio `AlertDialog` se fecha
- [ ] O `Scaffold` remove o diálogo
- [x] `onDismissRequest` põe `perguntando` em `false`, e o `if` deixa de mostrar o diálogo
- [ ] Nada: ele só sai com **Cancelar**

O diálogo está na tela enquanto o estado for verdadeiro. O toque fora só chama `onDismissRequest`; se essa lambda não mudar o estado, o diálogo continua lá.
:::

:::quiz Em qual destes casos um `AlertDialog` é melhor escolha que um `Snackbar`?
- [ ] Avisar que a receita foi compartilhada
- [ ] Confirmar que a favorita foi marcada
- [x] Perguntar se a pessoa quer mesmo apagar todas as anotações
- [ ] Dizer que o livro voltou à primeira receita

O diálogo interrompe e exige resposta: serve para decisões que jogam algo fora. Os outros três são avisos de algo que já aconteceu.
:::
