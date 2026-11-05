---
duration: 12
---

# O robô que digita (opcional)

{.lead}
O teste do módulo anterior confere a conta, mas não sabe se a tela mostra o resultado. Um teste de interface abre a tela no emulador, digita como uma pessoa e confere o que aparece.

:::info Módulo opcional
O projeto final não depende deste módulo. Com o tempo curto, siga para o [Livro de receitas](#livro-de-receitas) e volte aqui depois.
:::

::::columns
:::card Neste módulo
- Teste de interface: roda no emulador ou no celular.
- Achar um elemento, agir sobre ele, conferir a tela.
- `createComposeRule`: a tela dentro do teste.
:::
:::card Projeto
- Abra `exercicios/11-o-robo-que-digita`.
- App **Docinhos**, pronto, com um teste de interface que abre a tela e ainda não confere nada.
:::
::::

<figure class="figure">
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/09-antes.png" alt="Tela do Docinhos com 20 no campo Convidados e o total de 80 brigadeiros"></div></div>
<figcaption>O que o teste faz sozinho: digita 20 e confere se o total é 80.</figcaption>
</figure>

## Dois tipos de teste

| | Teste local | Teste de interface |
| --- | --- | --- |
| **Pasta** | `app/src/test/` | `app/src/androidTest/` |
| **Roda em** | Computador | Emulador ou celular |
| **Velocidade** | Milésimos de segundo | Alguns segundos |
| **Serve para** | Contas e regras | O que aparece na tela |

## Achar, agir, conferir

```kotlin
composeTestRule.onNodeWithText("Convidados").performTextInput("20")

composeTestRule.onNodeWithText("80").assertExists()
```

| Etapa | Funções | Aqui |
| --- | --- | --- |
| **Achar** | `onNodeWithText("…")` | O campo com o rótulo "Convidados". |
| **Agir** | `performTextInput("…")`, `performClick()` | Digitar 20. |
| **Conferir** | `assertExists()`, `assertIsDisplayed()` | Há um texto "80" na tela? |

O teste procura os elementos pelo texto, como uma pessoa faria.

## A tela dentro do teste

O projeto já traz esta parte:

```kotlin title="DocinhosTest.kt"
class DocinhosTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun vinteConvidados_oitentaBrigadeiros() {
        composeTestRule.setContent {
            ReceitasTheme {
                Docinhos()
            }
        }

        // TODO: digite 20 no campo "Convidados" e confira se o total passa a ser 80
    }
}
```

- `createComposeRule()` prepara um lugar para desenhar composables durante o teste.
- `setContent { … }` desenha só o que o teste precisa: aqui, `Docinhos()` com o tema.

## Para saber mais

- [Testar o layout do Compose](https://developer.android.com/develop/ui/compose/testing?hl=pt-br): a regra de teste, os nós e a árvore de semântica.
- [Folha de referência de testes](https://developer.android.com/develop/ui/compose/testing/testing-cheatsheet?hl=pt-br): as funções para achar, agir e conferir.
- Codelab oficial: [Criar testes automatizados](https://developer.android.com/codelabs/basic-android-kotlin-compose-write-automated-tests?hl=pt-br), na parte de testes de instrumentação.

## O desafio

:::steps
1. Ligue o emulador ou conecte o celular.
2. Abra `DocinhosTest.kt`. No painel do projeto, está em `com.example.docinhos (androidTest)`.
3. No lugar do `// TODO`, escreva as duas linhas:

   ```kotlin title="DocinhosTest.kt" hl_lines="1 3"
   composeTestRule.onNodeWithText("Convidados").performTextInput("20")

   composeTestRule.onNodeWithText("80").assertExists()
   ```

4. Importe `onNodeWithText` e `performTextInput` com <kbd>Alt</kbd>+<kbd>Enter</kbd>.
5. Rode pela seta verde ao lado do nome do teste. O app abre no emulador, o campo é preenchido e a tela fecha.
6. Faça o teste falhar de propósito: troque `"80"` por `"81"`, rode e leia a mensagem. Depois, desfaça.
:::

:::note Por que 80?
O campo **Docinhos por convidado** já começa com 4. O teste só precisa digitar os convidados: 20 × 4 = 80.
:::

## Resultado

- [ ] O teste passa, e você vê o campo ser preenchido sozinho.
- [ ] Com `"81"`, o teste falha e a mensagem diz que não encontrou o texto.

## Missão extra

:::exercise Teste o interruptor
Escreva um segundo teste: digite 20 convidados, ligue o interruptor e confira o texto "Latas de leite condensado: 3". Para achar o interruptor, use `composeTestRule.onNode(isToggleable())`.
:::

:::solution
```kotlin title="DocinhosTest.kt"
@Test
fun vinteConvidados_latasInteiras_tresLatas() {
    composeTestRule.setContent {
        ReceitasTheme {
            Docinhos()
        }
    }

    composeTestRule.onNodeWithText("Convidados").performTextInput("20")
    composeTestRule.onNode(isToggleable()).performClick()

    composeTestRule.onNodeWithText("Latas de leite condensado: 3").assertExists()
}
```

O interruptor não tem texto próprio, então `onNodeWithText` não o encontra. `isToggleable()` procura um elemento que liga e desliga, e nesta tela só existe um.
:::

## Teste o que aprendeu

:::quiz Por que um teste de interface demora mais que um teste local?
- [ ] Porque tem mais linhas
- [x] Porque precisa instalar o app em um aparelho e desenhar a tela de verdade
- [ ] Porque o `assertExists()` é lento
- [ ] Porque roda duas vezes

O teste de interface instala o app e um app de teste no emulador ou no celular, desenha a tela e interage com ela. Por isso os dois tipos se completam: muitos testes locais para as contas, alguns de interface para os caminhos principais.
:::
