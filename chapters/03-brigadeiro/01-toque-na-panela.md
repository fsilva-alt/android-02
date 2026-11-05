---
duration: 16
---

# Toque na panela (opcional)

{.lead}
Nem todo toque acontece em um botão. Com o modificador `clickable`, qualquer composable responde: uma imagem, um texto, um cartão inteiro. E, como brigadeiro não fica pronto com uma mexida só, a tela vai precisar de um segundo estado.

:::info Módulo opcional
Este módulo é prática: reforça o estado do módulo 2 em um app novo. Nenhum módulo seguinte depende dele. Com o tempo curto, siga para [Quantos convidados?](#quantos-convidados) e volte aqui depois.
:::

::::columns
:::card Neste módulo
- `Modifier.clickable`: toque em qualquer composable.
- `if` como expressão.
- Dois estados na mesma tela, um mudando o outro.
:::
:::card Projeto
- Abra `exercicios/04-toque-na-panela`.
- App **Brigadeiro**: a receita em quatro passos, parada no primeiro.
:::
::::

<div class="phones">
<figure class="figure">
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/04-antes.png" alt="Tela do app Brigadeiro no passo 1: uma panela, uma lata de leite condensado e uma barra de chocolate"></div></div>
<figcaption>Como começa: passo 1, e tocar na imagem não faz nada.</figcaption>
</figure>
<figure class="figure">
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/04-meio.png" alt="Tela do app Brigadeiro no passo 2, a panela no fogo com uma colher de pau, e o texto Toque na imagem para continuar"></div></div>
<figcaption>No meio do caminho: o toque avança, mas um só já encerra o passo 2.</figcaption>
</figure>
<figure class="figure">
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/04-depois.png" alt="Tela do app Brigadeiro no passo 2 com a contagem de mexidas que faltam"></div></div>
<figcaption>Como fica: no passo 2, a contagem diminui a cada toque.</figcaption>
</figure>
</div>

## clickable é um modificador

```kotlin
Image(
    painter = painterResource(imagem),
    contentDescription = null,
    modifier = Modifier
        .size(280.dp)
        .clip(CircleShape)
        .clickable { /* o que fazer no toque */ }
)
```

- A lambda de `clickable` faz o papel do `onClick` do botão.
- **A ordem conta.** Depois de `clip(CircleShape)`, a área de toque e a onda que aparece no toque são redondas. Antes do `clip`, seriam quadradas.
- `Button` continua sendo a escolha para uma ação com nome. `clickable` serve para imagens, cartões e itens de lista.

## A tela em passos

O app usa o mesmo desenho do Prato do Dia: **um número como estado** e `when` para o resto.

| `passo` | Imagem | Instrução |
| --- | --- | --- |
| 1 | panela e ingredientes | Leite condensado, chocolate e manteiga na panela. |
| 2 | panela no fogo | Mexa em fogo baixo até soltar do fundo. |
| 3 | bolinhas | Deixe esfriar e enrole as bolinhas. |
| 4 | brigadeiro | Passe no granulado. Está pronto! |

Avançar é somar 1. Depois do passo 4, a receita volta ao começo:

```kotlin
passo = if (passo < 4) passo + 1 else 1
```

Em Kotlin, `if` também devolve um valor: o da parte que for executada.

## Um estado para cada coisa que muda

Com essa linha a receita anda, mas o passo 2 acaba em um toque. Para pedir várias mexidas, a tela precisa lembrar quantas faltam.

| Estado | Guarda | Muda quando |
| --- | --- | --- |
| `passo` | Em que ponto da receita você está. | Um passo termina. |
| `mexidas` | Quantas mexidas ainda faltam. | A cada toque no passo 2. |

Cada um tem a sua linha de `remember { mutableStateOf(…) }`. Quando qualquer dos dois muda, a tela é recomposta.

## O toque depende do passo

| `passo` | O toque faz |
| --- | --- |
| 1 | Vai para o passo 2 e sorteia de 3 a 6 mexidas. |
| 2 | Desconta uma mexida. Se chegou a zero, vai para o passo 3. |
| 3 | Vai para o passo 4. |
| 4 | Volta ao passo 1. |

Em código, um `when` dentro do `clickable`:

```kotlin
.clickable {
    when (passo) {
        1 -> {
            passo = 2
            mexidas = (3..6).random()
        }
        2 -> {
            mexidas--
            if (mexidas == 0) {
                passo = 3
            }
        }
        3 -> passo = 4
        else -> passo = 1
    }
}
```

- Uma linha do `when` com mais de uma instrução vai entre chaves.
- `mexidas--` é o mesmo que `mexidas = mexidas - 1`.
- Aqui o `when` não devolve valor: ele só escolhe o que executar.

## Para saber mais

- [Toque e pressione](https://developer.android.com/develop/ui/compose/touch-input/pointer-input/tap-and-press?hl=pt-br): `clickable` e os outros gestos de toque.
- [Modificadores do Compose](https://developer.android.com/develop/ui/compose/modifiers?hl=pt-br#order-modifier-matters): por que a ordem dos modificadores importa.
- [Estado e Jetpack Compose](https://developer.android.com/develop/ui/compose/state?hl=pt-br): mais de um estado no mesmo composable.
- Codelab oficial: [Prática: comportamento de clique](https://developer.android.com/codelabs/basic-android-kotlin-compose-button-click-practice-problem?hl=pt-br), a mesma ideia com uma limonada no lugar do brigadeiro.

## O desafio

O projeto tem três `TODO`, numerados.

:::steps
1. Rode o projeto e toque na imagem. Nada acontece.
2. **TODO 1.** Em `MainActivity.kt`, no `Modifier` da imagem, troque o comentário pelo `clickable`:

   ```kotlin title="MainActivity.kt" hl_lines="4"
   modifier = Modifier
       .size(280.dp)
       .clip(CircleShape)
       .clickable { passo = if (passo < 4) passo + 1 else 1 }
   ```

3. Importe `clickable` com <kbd>Alt</kbd>+<kbd>Enter</kbd>, rode e toque na imagem quatro vezes. A receita anda e recomeça, mas um toque já encerra o passo 2.
4. **TODO 2.** Crie o estado novo logo abaixo de `passo`:

   ```kotlin title="MainActivity.kt" hl_lines="2"
   var passo by remember { mutableStateOf(1) }
   var mexidas by remember { mutableStateOf(0) }
   ```

5. No `clickable`, troque a linha do `if` por um `when`:

   ```kotlin title="MainActivity.kt" hl_lines="2-15"
   .clickable {
       when (passo) {
           1 -> {
               passo = 2
               mexidas = (3..6).random()
           }
           2 -> {
               mexidas--
               if (mexidas == 0) {
                   passo = 3
               }
           }
           3 -> passo = 4
           else -> passo = 1
       }
   }
   ```

6. **TODO 3.** Mostre a contagem. No último `Text` da tela, troque o valor de `text`:

   ```kotlin title="MainActivity.kt" hl_lines="2-6"
   Text(
       text = if (passo == 2) {
           stringResource(R.string.faltam, mexidas)
       } else {
           stringResource(R.string.toque)
       },
       style = MaterialTheme.typography.bodyLarge,
       color = MaterialTheme.colorScheme.onSurfaceVariant
   )
   ```

7. Rode e faça a receita inteira duas vezes.
:::

## Resultado

- [ ] Cada toque na imagem faz a receita andar: imagem, instrução e "Passo 2 de 4" mudam juntos.
- [ ] No passo 2 aparece "Mexidas que faltam", e o número diminui a cada toque.
- [ ] A receita só vai para o passo 3 quando a contagem chega a zero.
- [ ] Depois do passo 4, a receita volta ao passo 1. Na segunda vez, o número de mexidas pode ser outro.

## Missão extra

:::exercise Área de toque quadrada
Mude o `clickable` de lugar: ponha-o antes de `.clip(CircleShape)`. Rode e toque no canto da imagem, fora do círculo. Depois, volte para onde estava.
:::

:::solution
O toque passa a valer no quadrado inteiro de 280 dp, e a onda do toque também fica quadrada. Cada modificador age sobre o resultado do anterior: o que vem depois do `clip` fica recortado; o que vem antes, não.
:::

:::exercise Quantas receitas?
Crie um terceiro estado, `receitas`, que conta quantas vezes o brigadeiro ficou pronto. Some 1 quando a receita chegar ao passo 4 e mostre o total em um `Text` no fim da tela.
:::

:::solution
```kotlin title="MainActivity.kt"
var receitas by remember { mutableStateOf(0) }

// … no when do clickable:
3 -> {
    passo = 4
    receitas++
}

// … no fim da Column:
Text(text = "Receitas prontas: $receitas")
```
:::

## Teste o que aprendeu

:::quiz Em quais composables `Modifier.clickable` funciona?
- [ ] Só em `Image`
- [ ] Só em `Button` e `Image`
- [x] Em qualquer composable que aceite um `Modifier`
- [ ] Só em composables do Material 3

`clickable` é um modificador como `padding` ou `size`. Vale para `Text`, `Row`, `Column`, `Box`, `Image` e também para os composables que você escreve, desde que repassem o `modifier`.
:::

:::quiz Por que `mexidas` é sorteado no toque do passo 1, e não na criação do estado?
- [ ] Porque `mutableStateOf` não aceita `random()`
- [x] Porque o valor dado ao criar o estado só vale uma vez; sorteando no toque, cada receita tem a sua contagem
- [ ] Porque o `when` exige
- [ ] Não faz diferença

O valor inicial entra no `remember` na primeira composição e nunca mais é calculado. Para ter um número novo a cada receita, o sorteio precisa acontecer em um evento que se repete: o toque que inicia o passo 2.
:::
