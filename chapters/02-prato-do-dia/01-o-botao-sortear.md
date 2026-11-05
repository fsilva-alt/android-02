---
duration: 20
---

# O botão Sortear

{.lead}
O **Prato do Dia** mostra um prato e não tem como trocar. Falta um botão, falta a tela perceber que algo mudou, e falta escolher a imagem certa para cada número sorteado: o Compose só redesenha quando um **estado** muda, e o `when` transforma esse estado no que aparece.

::::columns
:::card Neste módulo
- `Button` e `onClick`: o código que roda a cada toque.
- `mutableStateOf` e `remember`: o estado que faz a tela ser redesenhada.
- `when`: um valor para cada número.
:::
:::card Projeto
- Abra `exercicios/02-o-botao-sortear` (**File › Open**).
- App **Prato do Dia**. A tela está montada; faltam o botão, o estado e a imagem de cada prato.
:::
::::

<div class="phones">
<figure class="figure">
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/02-antes.png" alt="Tela do Prato do Dia com um brigadeiro e nenhum botão"></div></div>
<figcaption>Como começa: sempre brigadeiro, sem botão.</figcaption>
</figure>
<figure class="figure">
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/02-meio.png" alt="Tela do Prato do Dia com o nome Coxinha e a imagem de um brigadeiro"></div></div>
<figcaption>No meio do caminho: o nome muda com o sorteio, a imagem não.</figcaption>
</figure>
<figure class="figure">
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/02-depois.png" alt="Tela do Prato do Dia com o nome Coxinha e a imagem de uma coxinha"></div></div>
<figcaption>Como fica: cada toque em Sortear mostra um prato, com imagem e nome combinando.</figcaption>
</figure>
</div>

## Um botão tem duas partes

```kotlin
Button(onClick = { /* o que fazer no toque */ }) {
    Text(text = "Sortear")
}
```

| Parte | O que é |
| --- | --- |
| `onClick = { … }` | Uma lambda: código guardado, que só roda quando o botão é tocado. |
| `{ Text(…) }` | O conteúdo do botão. Quase sempre um `Text`, mas aceita qualquer composable. |

O botão não sabe o que fazer. Ele só avisa que foi tocado; o que acontece está em `onClick`.

## Sortear um número

```kotlin
(1..6).random()
```

- `1..6` é um intervalo: 1, 2, 3, 4, 5 e 6.
- `random()` devolve um desses números, ao acaso.

O projeto guarda o prato atual em `prato`, um número de 1 a 6. **Mudar `prato` deveria mudar a tela.**

## A tela é o resultado de uma função

O Compose desenha a tela chamando `PratoDoDia()`. Para mostrar outro prato, precisa chamar a função de novo. Essa nova chamada é a **recomposição**, e quem a dispara é a mudança de um estado.

:::steps
1. **Toque.** `onClick` roda e escreve um número novo em `prato`.
2. **O estado avisa.** Se `prato` foi criado com `mutableStateOf`, o Compose fica sabendo.
3. **Recomposição.** O Compose chama `PratoDoDia()` de novo.
4. **Tela nova.** Os `when` leem o valor atual e escolhem outra imagem e outro nome.
:::

O projeto começa com `var prato = 1`. Com ele, o caminho para no passo 2: é uma variável local, ninguém é avisado e nada é redesenhado.

## Três peças em uma linha

```kotlin
var prato by remember { mutableStateOf(1) }
```

| Peça | Para quê |
| --- | --- |
| `mutableStateOf(1)` | Cria um valor **observável** que começa em 1. Quando ele muda, o Compose recompõe quem o lê. |
| `remember { … }` | Guarda o valor entre uma recomposição e outra. Sem ele, cada chamada de `PratoDoDia()` criaria o estado de novo, valendo 1. |
| `by` | Deixa usar `prato` como variável comum: `prato = 3`, `when (prato)`. |

## when devolve um valor

O estado guarda só um número. Quem transforma esse número em imagem e em nome é o `when`. O do nome já está no projeto:

```kotlin
val nome = when (prato) {
    1 -> R.string.brigadeiro
    2 -> R.string.pao_de_queijo
    3 -> R.string.bolo_de_cenoura
    4 -> R.string.pudim
    5 -> R.string.coxinha
    else -> R.string.acai
}
```

- O `when` compara `prato` com cada linha, de cima para baixo.
- O que está à direita de `->`, na primeira linha que casar, vira o valor de `nome`.
- `else` cobre o que sobrou. Quando o `when` devolve um valor, ele é obrigatório.

`R.string.coxinha` e `R.drawable.coxinha` são números inteiros, os identificadores dos recursos. Por isso cabem em uma variável e podem ser entregues a `stringResource()` e `painterResource()`.

## Estado mínimo

| | No projeto | Como surge |
| --- | --- | --- |
| **Estado** | `prato` | Muda no toque. |
| **Calculado** | `imagem`, `nome` | Saem de `prato`, a cada recomposição. |

Guarde como estado só o que não dá para calcular. Se imagem e nome fossem estados também, seria preciso atualizar os três a cada toque, e um dia eles iriam discordar.

## Para saber mais

- [Botão](https://developer.android.com/develop/ui/compose/components/button?hl=pt-br): os tipos de botão do Material 3 e quando usar cada um.
- [Estado e Jetpack Compose](https://developer.android.com/develop/ui/compose/state?hl=pt-br): `remember`, `mutableStateOf`, recomposição e por que guardar pouco estado.
- [Ciclo de vida de funções que podem ser compostas](https://developer.android.com/develop/ui/compose/lifecycle?hl=pt-br): quando e por que o Compose recompõe.
- [Recursos no Compose](https://developer.android.com/develop/ui/compose/resources?hl=pt-br): o que são `R.drawable` e `R.string`.
- [Lambdas](https://kotlinlang.org/docs/lambdas.html), [intervalos](https://kotlinlang.org/docs/ranges.html) e [`when`](https://kotlinlang.org/docs/control-flow.html#when-expressions-and-statements) na documentação do Kotlin (em inglês).
- Codelab oficial: [Criar um app Dice Roller interativo](https://developer.android.com/codelabs/basic-android-kotlin-compose-build-a-dice-roller-app?hl=pt-br), a mesma ideia com um dado no lugar dos pratos.

## O desafio

O projeto tem três `TODO`, numerados.

:::steps
1. Rode o projeto. Aparece o brigadeiro, sem botão.
2. **TODO 1.** Em `MainActivity.kt`, no fim da `Column`, escreva o botão no lugar do comentário:

   ```kotlin title="MainActivity.kt" hl_lines="2-4"
   Spacer(modifier = Modifier.height(32.dp))
   Button(onClick = { prato = (1..6).random() }) {
       Text(text = stringResource(R.string.sortear))
   }
   ```

3. `Button` fica vermelho: falta o import. Com o cursor nele, <kbd>Alt</kbd>+<kbd>Enter</kbd> › **Import**.
4. Rode e toque em **Sortear** várias vezes. Nada muda: o sorteio acontece, mas a tela não é redesenhada.
5. **TODO 2.** No começo de `PratoDoDia`, troque `var prato = 1` por:

   ```kotlin title="MainActivity.kt" hl_lines="1"
   var prato by remember { mutableStateOf(1) }
   ```

6. Importe o que ficou vermelho com <kbd>Alt</kbd>+<kbd>Enter</kbd>: `remember`, `mutableStateOf` e, sobre o `by`, `getValue` e `setValue`. Ao final, o arquivo ganha estas quatro linhas:

   ```kotlin title="MainActivity.kt"
   import androidx.compose.runtime.getValue
   import androidx.compose.runtime.mutableStateOf
   import androidx.compose.runtime.remember
   import androidx.compose.runtime.setValue
   ```

7. Rode e toque em **Sortear**. O nome muda; a imagem fica no brigadeiro.
8. **TODO 3.** Troque `val imagem = R.drawable.brigadeiro` por um `when`, na mesma ordem do `when` de `nome`:

   ```kotlin title="MainActivity.kt" hl_lines="1-8"
   val imagem = when (prato) {
       1 -> R.drawable.brigadeiro
       2 -> R.drawable.pao_de_queijo
       3 -> R.drawable.bolo_de_cenoura
       4 -> R.drawable.pudim
       5 -> R.drawable.coxinha
       else -> R.drawable.acai
   }
   ```

9. Rode e sorteie até ver os seis pratos.
:::

:::tip O aviso em `var prato`
No projeto inicial, o Android Studio sublinha `var prato` e sugere trocar por `val`, porque nada muda esse valor ainda. Deixe como está: o botão do TODO 1 vai mudá-lo, e o aviso some.
:::

:::warning O erro mais comum com estado
`Type 'MutableState<Int>' has no method 'getValue'` aparece quando faltam os imports de `getValue` e `setValue`. O código está certo; falta só importar.
:::

:::note Tocou e nada mudou?
Com o estado no lugar, o sorteio ainda pode repetir o número. Toque de novo.
:::

## Resultado

- [ ] O botão **Sortear** aparece abaixo do nome do prato.
- [ ] O arquivo compila sem nada em vermelho.
- [ ] Cada toque sorteia um prato; imagem e nome mudam juntos e sempre combinam.

## Missão extra

:::exercise Outros botões
O Material 3 tem mais de um botão. Troque `Button` por `FilledTonalButton`, depois por `OutlinedButton` e por `TextButton`. O que muda?
:::

:::solution
Só a aparência. Os quatro recebem `onClick` e conteúdo do mesmo jeito. O `Button` cheio é para a ação principal da tela; os outros, para ações de menor destaque.
:::

:::exercise Contador de sorteios
Crie um segundo estado, `sorteios`, que começa em 0 e aumenta a cada toque. Mostre o valor abaixo do botão.
:::

:::solution
```kotlin title="MainActivity.kt"
var prato by remember { mutableStateOf(1) }
var sorteios by remember { mutableStateOf(0) }

// …

Button(
    onClick = {
        prato = (1..6).random()
        sorteios++
    }
) {
    Text(text = stringResource(R.string.sortear))
}
Text(text = "Sorteios: $sorteios")
```

Cada estado tem o seu `remember { mutableStateOf(…) }`. Uma lambda com mais de uma instrução ocupa várias linhas.
:::

:::exercise Doce ou salgado?
Crie um terceiro `when` que devolve um texto: `"Doce"` para brigadeiro, bolo e pudim; `"Salgado"` para pão de queijo e coxinha; `"Gelado"` para o açaí. Mostre esse texto abaixo do nome. Dica: uma linha do `when` aceita vários valores separados por vírgula.
:::

:::solution
```kotlin title="MainActivity.kt"
val tipo = when (prato) {
    1, 3, 4 -> "Doce"
    2, 5 -> "Salgado"
    else -> "Gelado"
}

// … abaixo do Text do nome:
Text(text = tipo)
```
:::

## Teste o que aprendeu

:::quiz Quando roda o código de `onClick = { prato = (1..6).random() }`?
- [ ] Uma vez, quando a tela é desenhada
- [x] A cada toque no botão
- [ ] O tempo todo, enquanto o app está aberto
- [ ] Nunca: é só uma declaração

`onClick` recebe uma lambda. Ela fica guardada e só é executada quando o botão avisa que foi tocado.
:::

:::quiz O que aconteceria com `var prato by mutableStateOf(1)`, sem o `remember`?
- [ ] O app não compilaria
- [ ] Nada: `remember` é opcional
- [x] A cada recomposição o estado seria criado de novo com 1, e a tela voltaria para o brigadeiro
- [ ] O sorteio deixaria de ser aleatório

`mutableStateOf` avisa o Compose da mudança, mas a recomposição chama a função inteira outra vez. É o `remember` que devolve o valor guardado em vez de criar um novo.
:::

:::quiz No `when` de `imagem`, o que acontece se `prato` valer 6?
- [ ] Erro: não existe a linha `6 ->`
- [x] Cai no `else`, e a imagem é a do açaí
- [ ] A imagem fica vazia
- [ ] Vale a primeira linha, a do brigadeiro

Nenhuma das linhas de 1 a 5 casa com 6, então vale o `else`. Ele cobre qualquer valor que não foi listado.
:::
