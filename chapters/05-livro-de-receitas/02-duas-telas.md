---
duration: 16
---

# Duas telas

{.lead}
O livro mostra a foto e o nome de cada receita, mas não os ingredientes nem o modo de preparo. Eles ficam em outra tela, que já existe no projeto. Falta ir até ela e voltar.

::::columns
:::card Neste módulo
- Uma Activity, várias telas: cada tela é um composable.
- A pilha de telas é um estado: `add` abre, `removeLastOrNull` volta.
- `NavDisplay`: mostra a tela do topo da pilha.
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
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/10-depois.png" alt="Tela da receita com os ingredientes e o modo de preparo do brigadeiro e o botão Voltar"></div></div>
<figcaption>Como fica: Ver receita abre a segunda tela. Por enquanto, sempre a do brigadeiro.</figcaption>
</figure>
</div>

## O que já vem pronto

O app agora tem quatro arquivos de código. Você só edita o primeiro.

| Arquivo | O quê |
| --- | --- |
| `MainActivity.kt` | A chave, a pilha e o `NavDisplay`. É o arquivo deste módulo. |
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

:::note Uma Activity por tela
Em apps mais antigos, e em muito código que continua em uso, cada tela é uma `Activity`, e uma tela abre a outra com um `Intent`. Isso segue funcionando. Para um app novo, todo em Compose, a recomendação do Android é uma Activity só, com os destinos da navegação sendo composables, como aqui.
:::

:::note Navigation 3 e o curso oficial
Este módulo usa a **Navigation 3**, a biblioteca de navegação mais nova, feita para o Compose. O curso oficial ainda ensina a anterior, **Navigation Compose**, com `NavHost` e `NavController`. A ideia é a mesma: um nome para cada tela, uma pilha e um composable por destino.
:::

## Para saber mais

- [Navigation 3](https://developer.android.com/guide/navigation/navigation-3?hl=pt-br): a visão geral da biblioteca.
- [Noções básicas da Navigation 3](https://developer.android.com/guide/navigation/navigation-3/basics?hl=pt-br): chaves, pilha, `NavDisplay` e `entryProvider`.
- [Princípios de navegação](https://developer.android.com/guide/navigation/principles?hl=pt-br): o que se espera da pilha e do gesto de voltar em qualquer app.
- [Data objects](https://kotlinlang.org/docs/object-declarations.html#data-objects) na documentação do Kotlin (em inglês).
- Codelab oficial: [Navegar entre telas com o Compose](https://developer.android.com/codelabs/basic-android-kotlin-compose-navigation?hl=pt-br), com a Navigation Compose.

## O desafio

O projeto tem três `TODO`, numerados, todos em `MainActivity.kt`.

:::steps
1. Rode o projeto. Vá até o pudim com **Próxima** e toque em **Ver receita**. Nada acontece.
2. **TODO 1.** No começo de `MainActivity.kt`, crie a chave da tela nova, abaixo de `Livro`:

   ```kotlin title="MainActivity.kt" hl_lines="2"
   data object Livro
   data object Receita
   ```

3. **TODO 2.** Dentro de `entryProvider`, abaixo da entrada do livro, crie a entrada da tela nova. Por enquanto, sempre com a receita 1:

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

5. Rode e toque em **Ver receita**. A segunda tela abre. Volte com o botão **Voltar** e, na vez seguinte, com o gesto de voltar do Android.
:::

:::warning Chave sem entrada
Se a pilha receber uma chave que não tem `entry`, o app fecha no toque. No **Logcat**, a mensagem é `IllegalStateException: Unknown screen Receita`. Toda chave que entra na pilha precisa da sua entrada.
:::

:::note Sempre o brigadeiro?
Por enquanto, sim: a entrada chama `TelaReceita` com `numero = 1`, em qualquer página do livro. O próximo módulo, [Qual receita?](#qual-receita), leva o número da página até a segunda tela.
:::

## Resultado

- [ ] **Ver receita** abre a segunda tela, com ingredientes e modo de preparo.
- [ ] O botão **Voltar** e o gesto de voltar do Android trazem o livro de volta, na mesma página.
- [ ] No livro, o gesto de voltar sai do app.

## Missão extra

:::exercise O livro esquece a página
Depois de voltar da receita, o livro continua na página em que estava. Em `TelaLivro.kt`, troque `rememberSaveable` por `remember`. Rode, vá até o pudim, abra a receita e volte. Em que página o livro está? Desfaça a troca ao terminar.
:::

:::solution
Na página 1. Enquanto a receita estava na frente, o composable do livro não estava na tela, e o `remember` dele foi descartado. Ao voltar, `TelaLivro` foi composto de novo, com o valor inicial. O `rememberSaveable` guarda o valor fora da composição e o devolve na volta. O módulo 13, opcional, explica como.
:::

## Teste o que aprendeu

:::quiz A pilha é `Livro`, `Receita`. O usuário faz o gesto de voltar. O que acontece?
- [ ] O app fecha
- [x] `onBack` tira `Receita` da pilha, e o `NavDisplay` mostra o livro
- [ ] A tela da receita é desenhada de novo
- [ ] Nada: o gesto só funciona com o botão **Voltar**

`onBack` chama `pilha.removeLastOrNull()`. A pilha fica só com `Livro`, o estado mudou, e o `NavDisplay` mostra a tela da última chave.
:::

:::quiz O que acontece em `pilha.add(Receita)`?
- [ ] A tela da receita é desenhada na hora, por cima do livro
- [x] A lista ganha uma chave; como a lista é um estado, o `NavDisplay` é recomposto e mostra a tela dessa chave
- [ ] O Android cria uma Activity nova
- [ ] O livro volta para a página 1

Navegar é mudar a lista. O `NavDisplay` lê a pilha, percebe a mudança e procura a entrada da última chave.
:::

:::quiz Onde fica decidido qual composable aparece para a chave `Receita`?
- [ ] Em `TelaLivro.kt`, no botão **Ver receita**
- [x] Na `entry<Receita> { … }`, dentro do `entryProvider`
- [ ] No `AndroidManifest.xml`
- [ ] Em `Receitas.kt`

O botão só avisa que foi tocado, e quem tem a pilha põe a chave nela. A ligação entre a chave e a tela está na entrada.
:::
