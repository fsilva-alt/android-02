---
duration: 25
---

# Livro de receitas (opcional)

{.lead}
Um projeto de revisão em um app novo: um livro com seis receitas e dois botões para folhear. Não há conceito novo. Tudo o que ele pede já apareceu nos módulos anteriores, e é este app que os próximos módulos fazem crescer.

:::info Módulo opcional
Este módulo é prática. O projeto de [Duas telas](#duas-telas) já começa com o livro pronto. Com o tempo curto, siga para lá e volte aqui depois.
:::

::::columns
:::card Neste módulo
- Estado, `when`, `Button` e `if` trabalhando juntos.
- Dois botões mudando o mesmo estado.
- Dar a volta: da última receita para a primeira.
:::
:::card Projeto
- Abra `exercicios/09-livro-de-receitas`.
- App **Livro de Receitas**. A tela está montada; os botões não fazem nada.
:::
::::

<div class="phones">
<figure class="figure">
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/09-antes.png" alt="Tela do Livro de Receitas na receita 1 de 6, brigadeiro, com os botões Anterior e Próxima"></div></div>
<figcaption>Como começa: sempre a receita 1.</figcaption>
</figure>
<figure class="figure">
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/09-depois.png" alt="Tela do Livro de Receitas na receita 4 de 6, pudim de leite"></div></div>
<figcaption>Como fica: os botões percorrem as seis receitas.</figcaption>
</figure>
</div>

## O que o app precisa

| O app precisa | Você já fez | Módulo |
| --- | --- | --- |
| Lembrar qual receita está aberta. | `remember { mutableStateOf(1) }` | 2 |
| Mostrar a imagem da receita. | `when` que devolve `R.drawable…` | 2 |
| Reagir aos dois botões. | `onClick` | 2 |
| Voltar ao começo depois da última. | `if` como expressão | 4 |

O módulo 4 é opcional. Se você o pulou: em Kotlin, `if` também devolve um valor, o da parte que for executada. `if (receita == 6) 1 else receita + 1` vale 1 na última receita e o número seguinte nas outras.

O número da receita, de 1 a 6, é o único estado. Imagem, nome, detalhes e o texto "Receita 1 de 6" são calculados a partir dele. Os `when` do nome e dos detalhes já estão prontos.

## Dar a volta

| Botão | Regra | Caso especial |
| --- | --- | --- |
| **Próxima** | Soma 1. | Na receita 6, vai para a 1. |
| **Anterior** | Subtrai 1. | Na receita 1, vai para a 6. |

## Para saber mais

- [Estado e Jetpack Compose](https://developer.android.com/develop/ui/compose/state?hl=pt-br) e [Botão](https://developer.android.com/develop/ui/compose/components/button?hl=pt-br): as duas referências que este projeto mais usa.
- Codelab oficial: [Criar um app de galeria de arte](https://developer.android.com/codelabs/basic-android-kotlin-compose-art-space?hl=pt-br), o projeto equivalente do curso oficial.

## O desafio

O projeto tem quatro `TODO`, numerados. Tente resolver cada um antes de abrir a solução.

:::steps
1. **TODO 1.** Transforme `receita` em estado, começando em 1.
2. **TODO 2.** Escolha a imagem com um `when (receita)`, na mesma ordem do `when` de `nome`.
3. **TODO 3.** No `onClick` de **Próxima**, avance uma receita. Depois da sexta vem a primeira.
4. **TODO 4.** No `onClick` de **Anterior**, volte uma receita. Antes da primeira vem a sexta.
5. Rode e dê uma volta completa para a frente e outra para trás.
:::

:::solution
```kotlin title="MainActivity.kt"
var receita by remember { mutableStateOf(1) }

val imagem = when (receita) {
    1 -> R.drawable.brigadeiro
    2 -> R.drawable.pao_de_queijo
    3 -> R.drawable.bolo_de_cenoura
    4 -> R.drawable.pudim
    5 -> R.drawable.coxinha
    else -> R.drawable.acai
}
```

```kotlin title="MainActivity.kt" hl_lines="2 8"
OutlinedButton(
    onClick = { receita = if (receita == 1) 6 else receita - 1 },
    modifier = Modifier.weight(1f)
) {
    Text(text = stringResource(R.string.anterior))
}
Button(
    onClick = { receita = if (receita == 6) 1 else receita + 1 },
    modifier = Modifier.weight(1f)
) {
    Text(text = stringResource(R.string.proxima))
}
```
:::

## Resultado

- [ ] **Próxima** percorre as seis receitas e, depois da sexta, volta à primeira.
- [ ] **Anterior** faz o caminho contrário e, na primeira, vai para a sexta.
- [ ] Imagem, nome, detalhes e "Receita N de 6" sempre combinam.

## Missão extra

:::exercise Receita surpresa
Acrescente abaixo dos dois botões um `TextButton` com o texto "Surpresa", que abre uma receita ao acaso.
:::

:::solution
```kotlin title="MainActivity.kt"
TextButton(onClick = { receita = (1..6).random() }) {
    Text(text = "Surpresa")
}
```

É o botão do módulo 2, agora em outro app.
:::

:::exercise Receitas vistas
Conte quantas vezes uma receita foi aberta, somando 1 a cada toque em qualquer botão, e mostre o total no fim da tela.
:::

:::solution
```kotlin title="MainActivity.kt"
var vistas by remember { mutableStateOf(1) }

// … em cada onClick, depois de mudar a receita:
onClick = {
    receita = if (receita == 6) 1 else receita + 1
    vistas++
},

// … no fim da Column:
Text(text = "Receitas vistas: $vistas")
```
:::
