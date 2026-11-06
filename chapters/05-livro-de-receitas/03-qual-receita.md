---
duration: 12
---

# Qual receita?

{.lead}
A segunda tela abre, mas é sempre a do brigadeiro: ela não sabe de que página o toque veio. O livro sabe. Falta levar esse número de uma tela para a outra, e quem leva é a chave.

::::columns
:::card Neste módulo
- `data class`: uma chave que guarda um valor.
- Quem navega põe o dado na chave.
- A entrada da tela lê o dado e o entrega ao composable.
:::
:::card Projeto
- Abra `exercicios/11-qual-receita`.
- App **Livro de Receitas**, como ficou no módulo 10: **Ver receita** abre sempre o brigadeiro.
:::
::::

<div class="phones">
<figure class="figure">
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/10-antes.png" alt="Tela do Livro de Receitas na receita 4 de 6, pudim de leite, com os botões Ver receita, Anterior e Próxima"></div></div>
<figcaption>Na página do pudim, um toque em Ver receita.</figcaption>
</figure>
<figure class="figure">
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/10-depois.png" alt="Tela da receita com os ingredientes e o modo de preparo do brigadeiro e o botão Voltar"></div></div>
<figcaption>Como começa: abre o brigadeiro.</figcaption>
</figure>
<figure class="figure">
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/11-depois.png" alt="Tela da receita com os ingredientes e o modo de preparo do pudim de leite e o botão Voltar"></div></div>
<figcaption>Como fica: abre o pudim.</figcaption>
</figure>
</div>

## Onde o número se perde

O número da página já sai do livro. Ele some no meio do caminho:

| Onde | Código | O número |
| --- | --- | --- |
| `TelaLivro.kt` | `onClick = { aoVerReceita(receita) }` | Sai do livro, no aviso do toque. |
| `MainActivity.kt` | `aoVerReceita = { pilha.add(Receita) }` | Chega, e ninguém usa. |
| `MainActivity.kt` | `TelaReceita(numero = 1, …)` | A segunda tela recebe sempre 1. |

A chave `Receita` é um `data object`: só um nome. Não tem onde guardar o número.

## Uma chave que guarda um valor

```kotlin
data class Receita(val numero: Int)
```

| | Guarda | Exemplo |
| --- | --- | --- |
| `data object` | Nada. Existe um só. | `Livro` |
| `data class` | Os valores entre parênteses. Cada objeto tem os seus. | `Receita(4)`, `Receita(6)` |

`Receita(4)` cria uma chave com o número 4 dentro. Quem tem a chave lê o valor com `chave.numero`.

## Pôr na chave, ler da chave

Quem navega põe o dado na chave:

```kotlin
aoVerReceita = { numero -> pilha.add(Receita(numero)) }
```

`numero ->` dá um nome ao valor que a lambda recebe. Sem isso, ele se chamaria `it`, como no `onValueChange` do módulo 5.

A entrada da tela recebe a chave e lê o dado:

```kotlin
entry<Receita> { chave ->
    TelaReceita(numero = chave.numero, aoVoltar = { pilha.removeLastOrNull() })
}
```

| Toque | Código | A pilha fica |
| --- | --- | --- |
| **Ver receita**, na página 4 | `pilha.add(Receita(4))` | `Livro`, `Receita(4)` |
| **Voltar** | `pilha.removeLastOrNull()` | `Livro` |

## O mínimo na chave

Passe pela chave o mínimo: um número, um identificador. A tela busca o resto a partir dele. É o que `TelaReceita` faz, com os `when` de `Receitas.kt`:

```kotlin
val nome = stringResource(nomeDa(numero))
val ingredientes = stringResource(ingredientesDa(numero))
```

:::note E os extras do Intent?
No padrão de uma Activity por tela, o dado viaja no `Intent` que abre a tela: `intent.putExtra("numero", 4)` de um lado, `intent.getIntExtra("numero", 1)` do outro. A regra é a mesma: mandar um identificador, e não os dados inteiros.
:::

## Para saber mais

- [Noções básicas da Navigation 3](https://developer.android.com/guide/navigation/navigation-3/basics?hl=pt-br): chaves, com e sem dados, e a entrada que as recebe.
- [Data classes](https://kotlinlang.org/docs/data-classes.html) na documentação do Kotlin (em inglês).
- [Lambdas](https://kotlinlang.org/docs/lambdas.html#lambda-expression-syntax) na documentação do Kotlin (em inglês): parâmetros com nome e o `it`.
- [Intents e filtros de intent](https://developer.android.com/guide/components/intents-filters?hl=pt-br): o `Intent` e os seus extras.
- [Enviar dados simples para outros apps](https://developer.android.com/training/sharing/send?hl=pt-br): o `Intent` de compartilhamento da missão extra.

## O desafio

O projeto tem três `TODO`, numerados, todos em `MainActivity.kt`.

:::steps
1. Rode o projeto. Vá até o pudim com **Próxima** e toque em **Ver receita**: abre o brigadeiro.
2. **TODO 1.** No começo de `MainActivity.kt`, faça a chave levar o número. Troque `data object` por `data class`:

   ```kotlin title="MainActivity.kt" hl_lines="2"
   data object Livro
   data class Receita(val numero: Int)
   ```

   Mais abaixo, `pilha.add(Receita)` fica vermelho: a chave agora precisa de um número.

3. **TODO 2.** `TelaLivro` já avisa qual é a receita da página: `aoVerReceita` recebe esse número. Dê um nome a ele e ponha-o na chave:

   ```kotlin title="MainActivity.kt" hl_lines="2"
   TelaLivro(
       aoVerReceita = { numero -> pilha.add(Receita(numero)) }
   )
   ```

4. **TODO 3.** Na entrada da receita, receba a chave e use o número que veio nela:

   ```kotlin title="MainActivity.kt" hl_lines="1 3"
   entry<Receita> { chave ->
       TelaReceita(
           numero = chave.numero,
           aoVoltar = { pilha.removeLastOrNull() }
       )
   }
   ```

5. Rode e abra a receita de duas ou três páginas diferentes.
:::

## Resultado

- [ ] Em cada página, **Ver receita** abre os ingredientes e o modo de preparo daquela receita.
- [ ] O botão **Voltar** e o gesto de voltar do Android trazem o livro de volta, na mesma página.

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

O app não precisa saber quais apps existem no aparelho. Ele descreve o que quer, e o Android acha quem atende. O projeto do módulo 12 já vem com esse botão.
:::

## Teste o que aprendeu

:::quiz Por que `Receita` deixou de ser `data object` e virou `data class`?
- [ ] Porque `data object` não pode ser usado em uma pilha
- [ ] Porque a tela da receita tem mais botões
- [x] Porque a chave precisa levar um valor diferente a cada vez: o número da receita
- [ ] Porque `data class` é mais rápida

Um `data object` é um objeto só, sem dados. A `data class` cria um objeto para cada número, e a entrada da tela lê esse número em `chave.numero`.
:::

:::quiz Na página 6, o usuário toca em **Ver receita**. Como fica a pilha?
- [ ] `Livro`, `Receita`
- [x] `Livro`, `Receita(6)`
- [ ] `Receita(6)`
- [ ] `Livro`, `Receita(1)`

O livro avisa o número da página, e `pilha.add(Receita(numero))` põe no topo uma chave com esse número. O `Livro` continua embaixo, para a volta.
:::

:::quiz Uma tela de detalhes mostra nome, foto e ingredientes de uma receita. O que deve ir na chave?
- [ ] O nome, a foto e os ingredientes
- [ ] O composable da tela
- [x] Só o número da receita
- [ ] Nada: a tela descobre sozinha

A chave leva o que identifica o conteúdo. A tela busca o resto a partir disso, como `TelaReceita` faz com `nomeDa(numero)` e `ingredientesDa(numero)`.
:::
