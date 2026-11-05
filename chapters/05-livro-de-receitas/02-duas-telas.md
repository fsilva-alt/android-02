---
duration: 22
---

# Duas telas

{.lead}
O livro mostra a foto e o nome de cada receita, mas não os ingredientes nem o modo de preparo. Eles ficam em outra tela, que já existe no projeto. Falta ir até ela, voltar, e dizer a ela qual receita mostrar.

::::columns
:::card Neste módulo
- Uma Activity, várias telas: cada tela é um composable.
- A pilha de telas é um estado: `add` abre, `removeLastOrNull` volta.
- `NavDisplay`: mostra a tela do topo da pilha.
- Dados na chave: `Receita(numero = 4)`.
:::
:::card Projeto
- Abra `exercicios/10-duas-telas`.
- App **Livro de Receitas**, com o livro pronto e um botão novo, **Ver receita**, ainda sem função.
- Na primeira vez, o Android Studio baixa a biblioteca de navegação: cerca de um minuto, com internet.
:::
::::

<div class="phones">
<figure class="figure">
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/10-antes.png" alt="Tela do Livro de Receitas na receita 4 de 6, pudim de leite, com os botões Ver receita, Anterior e Próxima"></div></div>
<figcaption>Como começa: Ver receita não faz nada.</figcaption>
</figure>
<figure class="figure">
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/10-meio.png" alt="Tela da receita com os ingredientes e o modo de preparo do brigadeiro e o botão Voltar"></div></div>
<figcaption>No meio do caminho: a tela abre, mas é sempre a do brigadeiro.</figcaption>
</figure>
<figure class="figure">
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/10-depois.png" alt="Tela da receita com os ingredientes e o modo de preparo do pudim de leite e o botão Voltar"></div></div>
<figcaption>Como fica: na página do pudim, abre a receita do pudim.</figcaption>
</figure>
</div>

## O que já vem pronto

O app agora tem quatro arquivos de código. Você só edita o primeiro.

| Arquivo | O quê |
| --- | --- |
| `MainActivity.kt` | As chaves, a pilha e o `NavDisplay`. É o arquivo deste módulo. |
| `TelaLivro.kt` | O livro do módulo 9, com o botão **Ver receita**. |
| `TelaReceita.kt` | A tela nova: ingredientes e modo de preparo de uma receita. |
| `Receitas.kt` | Os `when` que escolhem imagem, nome e textos pelo número da receita. |

## Uma Activity, várias telas

Até aqui, cada app tinha uma tela. Um app com mais telas continua com uma Activity só: cada tela é um composable, e a navegação decide qual deles aparece. São três peças.

| Peça | O que é | No projeto |
| --- | --- | --- |
| **Chave** | Um nome para cada tela. | `data object Livro` |
| **Pilha** | A lista das telas abertas, em ordem. A última é a que aparece. | `pilha` |
| **`NavDisplay`** | Mostra a tela da última chave da pilha. | Dentro do `Scaffold` |

`data object Livro` cria um objeto único chamado `Livro`. Ele não guarda nada: só serve de nome para a tela.

## A pilha é um estado

```kotlin
val pilha = remember { mutableStateListOf<Any>(Livro) }
```

- `mutableStateListOf` é o `mutableStateOf` das listas: quando a lista muda, o Compose recompõe quem a lê.
- A lista começa com uma chave, `Livro`: a primeira tela do app.
- `<Any>` diz que a lista aceita chaves de qualquer tipo.

| Para | Código | A pilha fica |
| --- | --- | --- |
| Abrir a receita | `pilha.add(Receita)` | `Livro`, `Receita` |
| Voltar | `pilha.removeLastOrNull()` | `Livro` |

Não existe um comando "abrir tela". A tela muda porque um estado mudou, como em todo o resto do curso.

## NavDisplay: uma entrada por tela

```kotlin
NavDisplay(
    backStack = pilha,
    onBack = { pilha.removeLastOrNull() },
    entryProvider = entryProvider {
        entry<Livro> {
            TelaLivro(aoVerReceita = { /* … */ })
        }
    }
)
```

| Parâmetro | Papel |
| --- | --- |
| `backStack` | A pilha que o `NavDisplay` acompanha. |
| `onBack` | O que fazer no gesto de voltar do Android. |
| `entryProvider` | Para cada chave, a tela. Cada `entry<Chave> { … }` é uma entrada. |

As telas não sabem que existe navegação. `TelaLivro` só avisa, por `aoVerReceita`, que o botão foi tocado; `TelaReceita` avisa por `aoVoltar`. Quem tem a pilha decide o que fazer. É a elevação de estado do módulo 6.

## Dados na chave

A tela da receita precisa saber qual receita mostrar. Quem leva essa informação é a própria chave:

```kotlin
data class Receita(val numero: Int)
```

| | Guarda | Exemplo |
| --- | --- | --- |
| `data object` | Nada. Existe um só. | `Livro` |
| `data class` | Os valores entre parênteses. Cada objeto tem os seus. | `Receita(4)`, `Receita(6)` |

Quem navega põe o dado na chave. A entrada da tela recebe a chave e lê o dado:

```kotlin
pilha.add(Receita(numero = 4))

entry<Receita> { chave ->
    TelaReceita(numero = chave.numero, aoVoltar = { pilha.removeLastOrNull() })
}
```

Passe pela chave o mínimo: um número, um identificador. A tela busca o resto a partir dele, como `TelaReceita` faz com os `when` de `Receitas.kt`.

:::note Uma Activity por tela
Em apps mais antigos, e em muito código que continua em uso, cada tela é uma `Activity`, aberta com um `Intent`, e os dados viajam como "extras" desse `Intent`. Isso segue funcionando. Para um app novo, todo em Compose, a recomendação do Android é uma Activity só, com os destinos da navegação sendo composables, como aqui.
:::

:::note Navigation 3 e o curso oficial
Este módulo usa a **Navigation 3**, a biblioteca de navegação mais nova, feita para o Compose. O curso oficial ainda ensina a anterior, **Navigation Compose**, com `NavHost` e `NavController`. A ideia é a mesma: um nome para cada tela, uma pilha e um composable por destino.
:::

## Para saber mais

- [Navigation 3](https://developer.android.com/guide/navigation/navigation-3?hl=pt-br): a visão geral da biblioteca.
- [Noções básicas da Navigation 3](https://developer.android.com/guide/navigation/navigation-3/basics?hl=pt-br): chaves, pilha, `NavDisplay` e `entryProvider`.
- [Princípios de navegação](https://developer.android.com/guide/navigation/principles?hl=pt-br): o que se espera da pilha e do gesto de voltar em qualquer app.
- [Data classes](https://kotlinlang.org/docs/data-classes.html) e [data objects](https://kotlinlang.org/docs/object-declarations.html#data-objects) na documentação do Kotlin (em inglês).
- [Enviar dados simples para outros apps](https://developer.android.com/training/sharing/send?hl=pt-br): o `Intent` de compartilhamento da missão extra.
- Codelab oficial: [Navegar entre telas com o Compose](https://developer.android.com/codelabs/basic-android-kotlin-compose-navigation?hl=pt-br), com a Navigation Compose.

## O desafio

O projeto tem três `TODO`, numerados, todos em `MainActivity.kt`. Depois deles, mais três passos fazem a chave levar o número da receita.

:::steps
1. Rode o projeto. Vá até o pudim com **Próxima** e toque em **Ver receita**. Nada acontece.
2. **TODO 1.** No começo de `MainActivity.kt`, crie a chave da tela nova, abaixo de `Livro`:

   ```kotlin title="MainActivity.kt" hl_lines="2"
   data object Livro
   data object Receita
   ```

3. **TODO 2.** Dentro de `entryProvider`, crie a entrada da tela. Por enquanto, sempre com a receita 1:

   ```kotlin title="MainActivity.kt" hl_lines="1-6"
   entry<Receita> {
       TelaReceita(
           numero = 1,
           aoVoltar = { pilha.removeLastOrNull() }
       )
   }
   ```

4. **TODO 3.** Na entrada do livro, ponha a chave na pilha quando o botão for tocado:

   ```kotlin title="MainActivity.kt" hl_lines="2"
   TelaLivro(
       aoVerReceita = { pilha.add(Receita) }
   )
   ```

5. Rode. Vá até o pudim e toque em **Ver receita**: a tela abre, mas é a do brigadeiro. Volte com o botão **Voltar** e, na vez seguinte, com o gesto de voltar do Android.
6. Faça a chave levar o número. Troque `data object` por `data class`:

   ```kotlin title="MainActivity.kt" hl_lines="1"
   data class Receita(val numero: Int)
   ```

7. `TelaLivro` já avisa qual é a receita da página: `aoVerReceita` recebe esse número. Ponha-o na chave:

   ```kotlin title="MainActivity.kt" hl_lines="2"
   TelaLivro(
       aoVerReceita = { numero -> pilha.add(Receita(numero)) }
   )
   ```

8. Na entrada da receita, receba a chave e use o número que veio nela:

   ```kotlin title="MainActivity.kt" hl_lines="1 3"
   entry<Receita> { chave ->
       TelaReceita(
           numero = chave.numero,
           aoVoltar = { pilha.removeLastOrNull() }
       )
   }
   ```

9. Rode e abra a receita de duas ou três páginas diferentes.
:::

:::warning Chave sem entrada
Se a pilha receber uma chave que não tem `entry`, o app fecha no toque. No **Logcat**, a mensagem é `IllegalStateException: Unknown screen Receita`. Toda chave que entra na pilha precisa da sua entrada.
:::

:::note Por que o livro não volta para a página 1?
Quando a tela da receita abre, o composable do livro sai da tela, e o que estava em `remember` se perde. Em `TelaLivro.kt`, a página é guardada com `rememberSaveable`, que resiste a isso. O módulo 12 explica.
:::

## Resultado

- [ ] **Ver receita** abre os ingredientes e o modo de preparo da receita que está na página.
- [ ] O botão **Voltar** e o gesto de voltar do Android trazem o livro de volta, na mesma página.
- [ ] No livro, o gesto de voltar sai do app.

## Missão extra

:::exercise Compartilhar a receita
Sair do app para outro é trabalho de um `Intent`: um pedido ao Android, que diz a ação e leva os dados. Em `TelaReceita.kt`, ponha ao lado de **Voltar** um botão **Compartilhar**, que envia o texto da receita. O texto do botão já existe: `R.string.compartilhar`.

Um `Intent` com a ação `Intent.ACTION_SEND` pede para enviar algo; `Intent.createChooser` abre a lista dos apps que sabem receber. Para falar com o Android, o composable precisa do contexto: `LocalContext.current`.
:::

:::solution
```kotlin title="TelaReceita.kt"
// No começo de TelaReceita:
val context = LocalContext.current

// No lugar do botão Voltar:
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
```

| Linha | Papel |
| --- | --- |
| `Intent(Intent.ACTION_SEND)` | O pedido: enviar algo. |
| `intent.type = "text/plain"` | O tipo do conteúdo: texto simples. |
| `intent.putExtra(Intent.EXTRA_TEXT, …)` | O conteúdo, guardado como um "extra" do `Intent`. |
| `Intent.createChooser(intent, null)` | A lista de apps, para a pessoa escolher. |
| `context.startActivity(…)` | Entrega o pedido ao Android. |

O app não precisa saber quais apps existem no aparelho. Ele descreve o que quer, e o Android acha quem atende. O projeto do módulo 11 já vem com esse botão.
:::

:::exercise O livro esquece a página
Em `TelaLivro.kt`, troque `rememberSaveable` por `remember`. Rode, vá até o pudim, abra a receita e volte. Em que página o livro está? Desfaça a troca ao terminar.
:::

:::solution
Na página 1. Enquanto a receita estava na frente, o composable do livro não estava na tela, e o `remember` dele foi descartado. Ao voltar, `TelaLivro` foi composto de novo, com o valor inicial. O `rememberSaveable` guarda o valor fora da composição e o devolve na volta.
:::

## Teste o que aprendeu

:::quiz A pilha é `Livro`, `Receita(4)`. O usuário faz o gesto de voltar. O que acontece?
- [ ] O app fecha
- [x] `onBack` tira `Receita(4)` da pilha, e o `NavDisplay` mostra o livro
- [ ] A tela da receita é desenhada de novo
- [ ] Nada: o gesto só funciona com o botão **Voltar**

`onBack` chama `pilha.removeLastOrNull()`. A pilha fica só com `Livro`, o estado mudou, e o `NavDisplay` mostra a tela da última chave.
:::

:::quiz Por que `Receita` deixou de ser `data object` e virou `data class`?
- [ ] Porque `data object` não pode ser usado em uma pilha
- [ ] Porque a tela da receita tem mais botões
- [x] Porque a chave precisa levar um valor diferente a cada vez: o número da receita
- [ ] Porque `data class` é mais rápida

Um `data object` é um objeto só, sem dados. A `data class` cria um objeto para cada número, e a entrada da tela lê esse número em `chave.numero`.
:::

:::quiz O que acontece em `pilha.add(Receita(2))`?
- [ ] A tela da receita 2 é desenhada na hora, por cima do livro
- [x] A lista ganha uma chave; como a lista é um estado, o `NavDisplay` é recomposto e mostra a tela dessa chave
- [ ] O Android cria uma Activity nova
- [ ] A receita 2 vira a favorita

Navegar é mudar a lista. O `NavDisplay` lê a pilha, percebe a mudança e procura a entrada da última chave.
:::
