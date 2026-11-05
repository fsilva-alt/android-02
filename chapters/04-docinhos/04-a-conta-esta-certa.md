---
duration: 20
---

# A conta está certa? (opcional)

{.lead}
Conferir a conta à mão a cada mudança cansa, e uma hora alguém esquece. Um teste automatizado refaz a conferência em segundos, sempre do mesmo jeito. Neste projeto, um teste vai achar um erro que a tela esconde, e outro vai digitar no app sozinho.

:::info Módulo opcional
Nenhum módulo seguinte depende deste. O próximo, [Livro de receitas](#livro-de-receitas), é um projeto de revisão e também é opcional. Com o tempo curto, siga para [Duas telas](#duas-telas) e volte aqui depois.
:::

::::columns
:::card Neste módulo
- Teste local: roda no computador, sem emulador.
- `@Test` e `assertEquals`, e a mensagem de um teste que falha.
- Teste de interface: abre a tela no emulador, digita e confere.
:::
:::card Projeto
- Abra `exercicios/08-a-conta-esta-certa`.
- App **Docinhos**, completo, com um erro em `calcularLatas`, um teste local pronto e um teste de interface pela metade.
:::
::::

<figure class="figure">
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/07-antes.png" alt="Tela do Docinhos com 20 no campo Convidados e o total de 80 brigadeiros"></div></div>
<figcaption>O que o teste de interface faz sozinho: digita 20 e confere se o total é 80.</figcaption>
</figure>

## Dois tipos de teste

| | Teste local | Teste de interface |
| --- | --- | --- |
| **Pasta** | `app/src/test/` | `app/src/androidTest/` |
| **Roda em** | Computador | Emulador ou celular |
| **Velocidade** | Milésimos de segundo | Alguns segundos |
| **Serve para** | Contas e regras | O que aparece na tela |

No painel do projeto, as pastas aparecem como `com.example.docinhos (test)` e `com.example.docinhos (androidTest)`.

## Um teste é uma função

```kotlin title="CalcularLatasTest.kt"
class CalcularLatasTest {

    @Test
    fun sessentaBrigadeiros_duasLatas() {
        val latas = calcularLatas(brigadeiros = 60, inteiras = false)

        assertEquals(2.0, latas, 0.001)
    }
}
```

| Parte | Papel |
| --- | --- |
| `@Test` | Marca a função como um teste. |
| Nome da função | Diz o caso e o resultado esperado. |
| `assertEquals(esperado, obtido, tolerância)` | Falha se os dois valores forem diferentes. Com `Double`, o terceiro argumento é a diferença aceita. |

## Rodar e ler o resultado

- **Rodar:** clique na seta verde ao lado do nome do teste, ou da classe para rodar todos.
- **Passou:** marca verde na janela **Run**.
- **Falhou:** marca vermelha e uma mensagem com o valor esperado e o obtido:

```text
java.lang.AssertionError: expected:<1.5> but was:<1.0>
```

## Um teste que passa não prova tudo

O teste pronto usa 60 brigadeiros, uma conta redonda: 60 / 30 = 2. Ele passa, mas o app erra em outros casos. Escolha também valores que não dão resultado inteiro.

:::note Int dividido por Int dá Int
Em Kotlin, `45 / 30` vale `1`: entre dois inteiros, a divisão descarta a parte decimal. Já `45 / 30.0` vale `1.5`, porque um dos lados é `Double`.
:::

## Achar, agir, conferir

O teste local confere a conta, mas não sabe se a tela mostra o resultado. O teste de interface procura os elementos pelo texto, como uma pessoa faria:

```kotlin
composeTestRule.onNodeWithText("Convidados").performTextInput("20")

composeTestRule.onNodeWithText("80").assertExists()
```

| Etapa | Funções | Aqui |
| --- | --- | --- |
| **Achar** | `onNodeWithText("…")` | O campo com o rótulo "Convidados". |
| **Agir** | `performTextInput("…")`, `performClick()` | Digitar 20. |
| **Conferir** | `assertExists()`, `assertIsDisplayed()` | Há um texto "80" na tela? |

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

        // TODO 2: digite 20 no campo "Convidados" e confira se o total passa a ser 80
    }
}
```

- `createComposeRule()` prepara um lugar para desenhar composables durante o teste.
- `setContent { … }` desenha só o que o teste precisa: aqui, `Docinhos()` com o tema.

## Para saber mais

- [Criar testes de unidade locais](https://developer.android.com/training/testing/local-tests?hl=pt-br): onde ficam, como rodar e o que testar.
- [Testar o layout do Compose](https://developer.android.com/develop/ui/compose/testing?hl=pt-br): a regra de teste, os nós e a árvore de semântica.
- [Folha de referência de testes](https://developer.android.com/develop/ui/compose/testing/testing-cheatsheet?hl=pt-br): as funções para achar, agir e conferir.
- [JUnit 4](https://junit.org/junit4/) (em inglês): a biblioteca por trás de `@Test` e `assertEquals`.
- Codelab oficial: [Criar testes automatizados](https://developer.android.com/codelabs/basic-android-kotlin-compose-write-automated-tests?hl=pt-br), com os dois tipos de teste.

## O desafio

O projeto tem dois `TODO`, numerados: um em cada arquivo de teste. Na primeira vez, o Android Studio baixa as bibliotecas de teste.

:::steps
1. Abra `CalcularLatasTest.kt` e rode o teste que já existe. Ele passa.
2. **TODO 1.** Escreva um teste para 45 brigadeiros, sem arredondar. A resposta certa é 1,5 lata:

   ```kotlin title="CalcularLatasTest.kt" hl_lines="1-6"
   @Test
   fun quarentaECincoBrigadeiros_umaLataEMeia() {
       val latas = calcularLatas(brigadeiros = 45, inteiras = false)

       assertEquals(1.5, latas, 0.001)
   }
   ```

3. Rode a classe inteira. O teste novo falha: esperava 1.5, veio 1.0.
4. Abra `MainActivity.kt` e ache a conta em `calcularLatas`:

   ```kotlin title="MainActivity.kt" hl_lines="2"
   fun calcularLatas(brigadeiros: Int, inteiras: Boolean): Double {
       var latas = (brigadeiros / 30).toDouble()
   ```

   `brigadeiros / 30` divide dois inteiros e perde a parte decimal antes de virar `Double`.

5. Corrija a linha e rode a classe de novo. Os dois testes passam.

   ```kotlin title="MainActivity.kt" hl_lines="1"
   var latas = brigadeiros / 30.0
   ```

6. Agora o teste de interface. Ligue o emulador ou conecte o celular e abra `DocinhosTest.kt`.
7. **TODO 2.** Escreva as duas linhas:

   ```kotlin title="DocinhosTest.kt" hl_lines="1 3"
   composeTestRule.onNodeWithText("Convidados").performTextInput("20")

   composeTestRule.onNodeWithText("80").assertExists()
   ```

8. Importe `onNodeWithText` e `performTextInput` com <kbd>Alt</kbd>+<kbd>Enter</kbd>.
9. Rode pela seta verde ao lado do nome do teste. O app abre no emulador, o campo é preenchido e a tela fecha.
10. Faça o teste falhar de propósito: troque `"80"` por `"81"`, rode e leia a mensagem. Depois, desfaça.
:::

:::note Por que 80?
O campo **Docinhos por convidado** já começa com 4. O teste só precisa digitar os convidados: 20 × 4 = 80.
:::

## Resultado

- [ ] O teste local novo falhou antes da correção, com `expected:<1.5> but was:<1.0>`.
- [ ] Depois da correção, os dois testes locais passam.
- [ ] O teste de interface passa, e você vê o campo ser preenchido sozinho.
- [ ] Com `"81"`, ele falha e a mensagem diz que não encontrou o texto.

## Missão extra

:::exercise E com o interruptor ligado?
Escreva um terceiro teste local: 45 brigadeiros com `inteiras = true`. Quantas latas ele deve esperar?
:::

:::solution
```kotlin title="CalcularLatasTest.kt"
@Test
fun quarentaECincoBrigadeiros_latasInteiras_duasLatas() {
    val latas = calcularLatas(brigadeiros = 45, inteiras = true)

    assertEquals(2.0, latas, 0.001)
}
```

1,5 lata arredondada para cima dá 2.
:::

:::exercise Teste o interruptor na tela
Escreva um segundo teste de interface: digite 20 convidados, ligue o interruptor e confira o texto "Latas de leite condensado: 3". Para achar o interruptor, use `composeTestRule.onNode(isToggleable())`.
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

:::quiz Onde roda um teste local, da pasta `app/src/test/`?
- [ ] No emulador
- [ ] No celular conectado
- [x] No próprio computador, sem aparelho nenhum
- [ ] Na nuvem

Testes locais rodam na máquina de desenvolvimento. Por isso são rápidos e servem bem para funções de cálculo, que não dependem da tela.
:::

:::quiz Por que um teste de interface demora mais que um teste local?
- [ ] Porque tem mais linhas
- [x] Porque precisa instalar o app em um aparelho e desenhar a tela de verdade
- [ ] Porque o `assertExists()` é lento
- [ ] Porque roda duas vezes

O teste de interface instala o app e um app de teste no emulador ou no celular, desenha a tela e interage com ela. Por isso os dois tipos se completam: muitos testes locais para as contas, alguns de interface para os caminhos principais.
:::
