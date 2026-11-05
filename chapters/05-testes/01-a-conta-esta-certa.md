---
duration: 12
---

# A conta está certa?

{.lead}
Conferir a conta à mão a cada mudança cansa, e uma hora alguém esquece. Um teste automatizado refaz a conferência em segundos, sempre do mesmo jeito. Neste projeto, ele vai achar um erro que a tela esconde.

::::columns
:::card Neste módulo
- Teste local: roda no computador, sem emulador.
- `@Test` e `assertEquals`.
- Ler a mensagem de um teste que falha.
:::
:::card Projeto
- Abra `exercicios/10-a-conta-esta-certa`.
- App **Docinhos**, completo, com um teste pronto e um erro em `calcularLatas`.
:::
::::

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

Os testes locais ficam em `app/src/test/`. No painel do projeto, é a pasta `com.example.docinhos (test)`.

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

## Para saber mais

- [Criar testes de unidade locais](https://developer.android.com/training/testing/local-tests?hl=pt-br): onde ficam, como rodar e o que testar.
- [JUnit 4](https://junit.org/junit4/) (em inglês): a biblioteca por trás de `@Test` e `assertEquals`.
- Codelab oficial: [Criar testes automatizados](https://developer.android.com/codelabs/basic-android-kotlin-compose-write-automated-tests?hl=pt-br).

## O desafio

:::steps
1. Abra `CalcularLatasTest.kt` e rode o teste que já existe. Ele passa.
2. No lugar do `// TODO`, escreva um teste para 45 brigadeiros, sem arredondar. A resposta certa é 1,5 lata:

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

5. Corrija a linha:

   ```kotlin title="MainActivity.kt" hl_lines="1"
   var latas = brigadeiros / 30.0
   ```

6. Rode a classe de novo.
:::

## Resultado

- [ ] O teste novo falhou antes da correção, com `expected:<1.5> but was:<1.0>`.
- [ ] Depois da correção, os dois testes passam.

## Missão extra

:::exercise E com o interruptor ligado?
Escreva um terceiro teste: 45 brigadeiros com `inteiras = true`. Quantas latas ele deve esperar?
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

## Teste o que aprendeu

:::quiz Onde roda um teste local, da pasta `app/src/test/`?
- [ ] No emulador
- [ ] No celular conectado
- [x] No próprio computador, sem aparelho nenhum
- [ ] Na nuvem

Testes locais rodam na máquina de desenvolvimento. Por isso são rápidos e servem bem para funções de cálculo, que não dependem da tela.
:::
