---
duration: 16
---

# Só latas inteiras

{.lead}
Dois acabamentos na calculadora. Os campos pedem números e abrem o teclado de letras: o `TextField` aceita opções que escolhem o teclado. E ninguém compra 2,7 latas de leite condensado: um interruptor decide se a conta arredonda para cima, e ele funciona como o campo de texto, sem estado não liga.

::::columns
:::card Neste módulo
- `KeyboardOptions`: o teclado de cada campo.
- `Switch`: `checked` e `onCheckedChange`, com um estado `Boolean`.
- `ceil`: arredondar para cima.
:::
:::card Projeto
- Abra `exercicios/07-so-latas-inteiras`.
- App **Docinhos**, com o teclado de letras e o interruptor na tela, ainda sem função.
:::
::::

<div class="phones">
<figure class="figure">
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/07-antes.png" alt="Tela do Docinhos com 80 brigadeiros, 2,7 latas e o interruptor Só latas inteiras desligado"></div></div>
<figcaption>Como começa: o interruptor não sai do lugar.</figcaption>
</figure>
<figure class="figure">
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/07-depois.png" alt="Tela do Docinhos com 80 brigadeiros, 3 latas e o interruptor Só latas inteiras ligado"></div></div>
<figcaption>Como fica: ligado, 2,7 latas viram 3.</figcaption>
</figure>
</div>

## As opções do teclado

```kotlin
TextField(
    // …
    keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Number,
        imeAction = ImeAction.Next
    )
)
```

| Opção | Valores comuns | Efeito |
| --- | --- | --- |
| `keyboardType` | `Number`, `Decimal`, `Phone`, `Email`, `Password` | Qual teclado abre. |
| `imeAction` | `Next`, `Done`, `Search`, `Send` | O desenho e a função da tecla do canto inferior direito. |

O projeto já traz a tecla de ação resolvida: `CampoNumero` recebe a ação pelo parâmetro `acao`, e cada campo passa a sua.

| Campo | `acao` | A tecla de ação |
| --- | --- | --- |
| Convidados | `ImeAction.Next` | Leva o cursor para o campo seguinte. |
| Docinhos por convidado | `ImeAction.Done` | Fecha o teclado. |

Falta o tipo do teclado.

:::note O teclado numérico não valida nada
Ele só facilita. Ainda dá para colar letras no campo ou usar um teclado físico, por isso `toIntOrNull() ?: 0` continua necessário.
:::

## Switch: outro componente sem memória

```kotlin
Switch(
    checked = latasInteiras,
    onCheckedChange = { latasInteiras = it }
)
```

| Componente | Mostra | Avisa | Tipo do estado |
| --- | --- | --- | --- |
| `TextField` | `value` | `onValueChange` | `String` |
| `Switch` | `checked` | `onCheckedChange` | `Boolean` |
| `Checkbox` | `checked` | `onCheckedChange` | `Boolean` |

O desenho é sempre o mesmo: um parâmetro para mostrar o valor, uma lambda para avisar da mudança, e o estado do lado de fora.

## Arredondar para cima

| Função | 2,1 | 2,7 | Para quê |
| --- | --- | --- | --- |
| `ceil` | 3.0 | 3.0 | O próximo inteiro. É o caso das latas: 2,1 latas pedem 3. |
| `floor` | 2.0 | 2.0 | O inteiro anterior. |
| `round` | 2.0 | 3.0 | O inteiro mais próximo. |

As três estão em `kotlin.math` e devolvem `Double`.

## Para saber mais

- [Opções de teclado](https://developer.android.com/develop/ui/compose/text/user-input?hl=pt-br#keyboard-options): todos os tipos de teclado e de ação.
- [Switch](https://developer.android.com/develop/ui/compose/components/switch?hl=pt-br) e [Caixa de seleção](https://developer.android.com/develop/ui/compose/components/checkbox?hl=pt-br): os dois componentes de ligar e desligar.
- [`ceil`](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.math/ceil.html) na documentação do Kotlin (em inglês).
- Codelab oficial: [Calcular uma gorjeta personalizada](https://developer.android.com/codelabs/basic-android-kotlin-compose-calculate-tip?hl=pt-br), que configura o teclado e arredonda a gorjeta com um interruptor.

## O desafio

O projeto tem três `TODO`, numerados.

:::steps
1. Rode o projeto. Toque em um campo: abre o teclado de letras. Toque no interruptor: ele não liga.
2. **TODO 1.** Em `CampoNumero`, peça o teclado numérico dentro de `KeyboardOptions`:

   ```kotlin title="MainActivity.kt" hl_lines="2"
   keyboardOptions = KeyboardOptions(
       keyboardType = KeyboardType.Number,
       imeAction = acao
   ),
   ```

3. Importe `KeyboardType` com <kbd>Alt</kbd>+<kbd>Enter</kbd> e rode. Os dois campos abrem o teclado numérico.
4. **TODO 2.** Em `Docinhos`, crie o estado do interruptor abaixo dos outros dois:

   ```kotlin title="MainActivity.kt" hl_lines="3"
   var convidados by remember { mutableStateOf("") }
   var porConvidado by remember { mutableStateOf("4") }
   var latasInteiras by remember { mutableStateOf(false) }
   ```

5. Ligue o `Switch` a esse estado:

   ```kotlin title="MainActivity.kt" hl_lines="2 3"
   Switch(
       checked = latasInteiras,
       onCheckedChange = { latasInteiras = it }
   )
   ```

6. **TODO 3.** No fim do arquivo, dê a `calcularLatas` um segundo parâmetro e o arredondamento:

   ```kotlin title="MainActivity.kt" hl_lines="1-6"
   fun calcularLatas(brigadeiros: Int, inteiras: Boolean): Double {
       var latas = brigadeiros / 30.0
       if (inteiras) {
           latas = ceil(latas)
       }
       return latas
   }
   ```

7. A chamada ficou vermelha: falta o segundo argumento. Em `Docinhos`:

   ```kotlin title="MainActivity.kt" hl_lines="1"
   val latas = calcularLatas(brigadeiros, latasInteiras)
   ```

8. Importe `ceil` e rode. Digite 20 convidados e ligue o interruptor.
:::

:::note E quando o teclado cobre a tela?
A `Column` do app já vem com `verticalScroll(rememberScrollState())`. Em telas pequenas ou com o aparelho deitado, o conteúdo rola.
:::

:::tip O teclado não aparece no emulador?
Com o teclado do computador ativo, o emulador pode esconder o teclado da tela. Em **Settings**, procure por **Physical keyboard** e ligue **Show on-screen keyboard**.
:::

## Resultado

- [ ] Os dois campos abrem o teclado numérico.
- [ ] O interruptor liga e desliga.
- [ ] Com 20 convidados: 2,7 latas com ele desligado, 3 com ele ligado.

## Missão extra

:::exercise Outros teclados
Troque `KeyboardType.Number` por `KeyboardType.Phone` e depois por `KeyboardType.Email`. O que muda no teclado? Volte para `Number` ao terminar.
:::

:::solution
`Phone` abre o teclado de discagem, com `*` e `#`. `Email` abre o de letras com `@` e `.` à mão. O campo aceita o mesmo texto nos três casos; o que muda é a facilidade de digitar.
:::

:::exercise Caixa de seleção
Troque `Switch` por `Checkbox`. O que precisa mudar além do nome?
:::

:::solution
Nada, só o import. Os dois recebem `checked` e `onCheckedChange` e trabalham com o mesmo estado `Boolean`. A escolha é de aparência: `Switch` para ligar e desligar algo que vale na hora, `Checkbox` para marcar itens de uma lista.
:::

## Teste o que aprendeu

:::quiz O campo usa `keyboardType = KeyboardType.Number`. O texto recebido em `onValueChange` é sempre um número?
- [ ] Sim, o teclado numérico garante isso
- [x] Não: o usuário pode colar outro texto ou usar um teclado físico
- [ ] Sim, mas só no emulador
- [ ] Não, porque o estado é do tipo `Int`

`keyboardType` é um pedido ao teclado da tela, não uma validação. O estado continua sendo um `String`, e a conversão segura continua sendo `toIntOrNull() ?: 0`.
:::

:::quiz Com o interruptor ligado, 31 brigadeiros pedem quantas latas?
- [ ] 1
- [ ] 1,03
- [x] 2
- [ ] 31

31 / 30 dá 1,03. `ceil` leva ao próximo inteiro, 2: uma lata não basta, e não existe um terço de lata à venda.
:::
