---
duration: 8
---

# Um prato para cada número

{.lead}
O estado guarda só um número. Quem transforma esse número em imagem e em nome é o `when`.

::::columns
:::card Neste módulo
- `when` como expressão: devolve um valor.
- O `else`, que cobre todos os outros casos.
- Estado mínimo: guardar o número, calcular o resto.
:::
:::card Projeto
- Abra `exercicios/03-um-prato-para-cada-numero`.
- App **Prato do Dia**. O nome já muda com o sorteio; a imagem, não.
:::
::::

<div class="phones">
<figure class="figure">
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/03-antes.png" alt="Tela do Prato do Dia com o nome Coxinha e a imagem de um brigadeiro"></div></div>
<figcaption>Como começa: o nome diz coxinha, a imagem é de brigadeiro.</figcaption>
</figure>
<figure class="figure">
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/03-depois.png" alt="Tela do Prato do Dia com o nome Coxinha e a imagem de uma coxinha"></div></div>
<figcaption>Como fica: imagem e nome combinam.</figcaption>
</figure>
</div>

## when devolve um valor

O `when` do nome já está no projeto:

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

- [`when` na documentação do Kotlin](https://kotlinlang.org/docs/control-flow.html#when-expressions-and-statements) (em inglês): intervalos, vários valores por linha e `when` sem argumento.
- [Recursos no Compose](https://developer.android.com/develop/ui/compose/resources?hl=pt-br): o que são `R.drawable` e `R.string`.
- [Estado e Jetpack Compose](https://developer.android.com/develop/ui/compose/state?hl=pt-br): por que guardar pouco estado e calcular o resto.
- Codelab oficial: [Criar um app Dice Roller interativo](https://developer.android.com/codelabs/basic-android-kotlin-compose-build-a-dice-roller-app?hl=pt-br), que escolhe a imagem do dado com um `when`.

## O desafio

:::steps
1. Rode o projeto e toque em **Sortear**. O nome muda; a imagem fica no brigadeiro.
2. Em `MainActivity.kt`, ache o `// TODO` e troque `val imagem = R.drawable.brigadeiro` por um `when`, na mesma ordem do `when` de `nome`:

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

3. Rode e sorteie até ver os seis pratos.
:::

## Resultado

- [ ] A imagem muda a cada sorteio.
- [ ] Imagem e nome sempre combinam.

## Missão extra

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

:::quiz No `when` de `imagem`, o que acontece se `prato` valer 6?
- [ ] Erro: não existe a linha `6 ->`
- [x] Cai no `else`, e a imagem é a do açaí
- [ ] A imagem fica vazia
- [ ] Vale a primeira linha, a do brigadeiro

Nenhuma das linhas de 1 a 5 casa com 6, então vale o `else`. Ele cobre qualquer valor que não foi listado.
:::
