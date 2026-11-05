---
duration: 8
---

# O teclado certo (opcional)

{.lead}
Os dois campos pedem números e abrem o teclado de letras. O `TextField` aceita opções que escolhem o teclado e a tecla do canto.

:::info Módulo opcional
Nenhum módulo seguinte depende deste: os próximos projetos já vêm com o teclado numérico. Com o tempo curto, siga para [Só latas inteiras](#so-latas-inteiras) e volte aqui depois.
:::

::::columns
:::card Neste módulo
- `KeyboardOptions`: as opções de teclado de um campo.
- `keyboardType`: qual teclado abre.
- `imeAction`: o que a tecla de ação faz.
:::
:::card Projeto
- Abra `exercicios/08-o-teclado-certo`.
- App **Docinhos**, com a conta funcionando e o teclado de letras.
:::
::::

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

Nesta tela:

| Campo | `imeAction` | A tecla de ação |
| --- | --- | --- |
| Convidados | `ImeAction.Next` | Leva o cursor para o campo seguinte. |
| Docinhos por convidado | `ImeAction.Done` | Fecha o teclado. |

:::note O teclado numérico não valida nada
Ele só facilita. Ainda dá para colar letras no campo ou usar um teclado físico, por isso `toIntOrNull() ?: 0` continua necessário.
:::

## Para saber mais

- [Opções de teclado](https://developer.android.com/develop/ui/compose/text/user-input?hl=pt-br#keyboard-options): todos os tipos de teclado e de ação.
- Codelab oficial: [Calcular uma gorjeta personalizada](https://developer.android.com/codelabs/basic-android-kotlin-compose-calculate-tip?hl=pt-br), que configura o teclado e a tecla de ação.

## O desafio

:::steps
1. Rode o projeto e toque em um campo. Abre o teclado de letras.
2. Em `CampoNumero`, ache o `// TODO` dentro do `TextField` e peça o teclado numérico:

   ```kotlin title="MainActivity.kt" hl_lines="2-4"
   singleLine = true,
   keyboardOptions = KeyboardOptions(
       keyboardType = KeyboardType.Number
   ),
   modifier = modifier.fillMaxWidth()
   ```

3. Importe `KeyboardOptions` e `KeyboardType` e rode. Os dois campos abrem o teclado numérico.
4. Cada campo precisa de uma tecla de ação diferente. Crie o parâmetro `acao` e use-o em `imeAction`:

   ```kotlin title="MainActivity.kt" hl_lines="6 15"
   @Composable
   fun CampoNumero(
       @StringRes rotulo: Int,
       valor: String,
       aoMudar: (String) -> Unit,
       acao: ImeAction,
       modifier: Modifier = Modifier
   ) {
       TextField(
           value = valor,
           onValueChange = aoMudar,
           label = { Text(text = stringResource(rotulo)) },
           singleLine = true,
           keyboardOptions = KeyboardOptions(
               keyboardType = KeyboardType.Number,
               imeAction = acao
           ),
           modifier = modifier.fillMaxWidth()
       )
   }
   ```

5. Passe a ação em cada chamada:

   ```kotlin title="MainActivity.kt" hl_lines="5 12"
   CampoNumero(
       rotulo = R.string.convidados,
       valor = convidados,
       aoMudar = { convidados = it },
       acao = ImeAction.Next
   )
   Spacer(modifier = Modifier.height(16.dp))
   CampoNumero(
       rotulo = R.string.por_convidado,
       valor = porConvidado,
       aoMudar = { porConvidado = it },
       acao = ImeAction.Done
   )
   ```

6. Importe `ImeAction` e rode.
:::

:::note E quando o teclado cobre a tela?
A `Column` do app já vem com `verticalScroll(rememberScrollState())`. Em telas pequenas ou com o aparelho deitado, o conteúdo rola.
:::

:::tip O teclado não aparece no emulador?
Com o teclado do computador ativo, o emulador pode esconder o teclado da tela. Em **Settings**, procure por **Physical keyboard** e ligue **Show on-screen keyboard**.
:::

## Resultado

- [ ] Os dois campos abrem o teclado numérico.
- [ ] Em **Convidados**, a tecla de ação pula para o campo seguinte.
- [ ] Em **Docinhos por convidado**, a tecla de ação fecha o teclado.

## Missão extra

:::exercise Outros teclados
Troque `KeyboardType.Number` por `KeyboardType.Phone` e depois por `KeyboardType.Email`. O que muda no teclado? Volte para `Number` ao terminar.
:::

:::solution
`Phone` abre o teclado de discagem, com `*` e `#`. `Email` abre o de letras com `@` e `.` à mão. O campo aceita o mesmo texto nos três casos; o que muda é a facilidade de digitar.
:::

## Teste o que aprendeu

:::quiz O campo usa `keyboardType = KeyboardType.Number`. O texto recebido em `onValueChange` é sempre um número?
- [ ] Sim, o teclado numérico garante isso
- [x] Não: o usuário pode colar outro texto ou usar um teclado físico
- [ ] Sim, mas só no emulador
- [ ] Não, porque o estado é do tipo `Int`

`keyboardType` é um pedido ao teclado da tela, não uma validação. O estado continua sendo um `String`, e a conversão segura continua sendo `toIntOrNull() ?: 0`.
:::
