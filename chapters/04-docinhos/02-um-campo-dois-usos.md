---
duration: 12
---

# Um campo, dois usos

{.lead}
A calculadora ganhou um segundo campo, feito com o mesmo composable do primeiro. Os dois aceitam texto, mas a conta parou: o estado ficou preso dentro de cada campo, onde a tela não alcança.

::::columns
:::card Neste módulo
- Composable com estado e sem estado.
- Elevação de estado: o estado sobe para quem usa.
- O par `valor` e `aoMudar`.
:::
:::card Projeto
- Abra `exercicios/07-um-campo-dois-usos`.
- App **Docinhos**, com dois `CampoNumero` e o total parado em 0.
:::
::::

<div class="phones">
<figure class="figure">
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/07-antes.png" alt="Tela do Docinhos com 20 convidados e 4 docinhos por convidado digitados e o total de 0 brigadeiros"></div></div>
<figcaption>Como começa: os campos guardam o texto, a tela não fica sabendo.</figcaption>
</figure>
<figure class="figure">
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/07-depois.png" alt="Tela do Docinhos com 20 convidados, 5 docinhos por convidado e o total de 100 brigadeiros"></div></div>
<figcaption>Como fica: a tela é dona dos dois valores e faz a conta.</figcaption>
</figure>
</div>

## Com estado e sem estado

::::columns
:::card Com estado
O `CampoNumero` do projeto inicial cria o próprio `remember`. Funciona sozinho, mas ninguém de fora consegue ler o que foi digitado.
:::
:::card Sem estado
Recebe o texto por parâmetro e avisa quando ele muda. Quem chama decide onde o estado mora, e pode usá-lo em uma conta.
:::
::::

## Elevar o estado

Elevar o estado é trocar a variável de dentro do composable por **dois parâmetros**:

| Parâmetro | Sentido | Papel |
| --- | --- | --- |
| `valor: String` | Desce, da tela para o campo. | O texto a mostrar. |
| `aoMudar: (String) -> Unit` | Sobe, do campo para a tela. | Chamado a cada tecla, com o texto novo. |

`(String) -> Unit` é o tipo de uma função que recebe um `String` e não devolve nada.

```kotlin
@Composable
fun Docinhos(modifier: Modifier = Modifier) {
    var convidados by remember { mutableStateOf("") }   // o estado mora na tela

    CampoNumero(
        rotulo = R.string.convidados,
        valor = convidados,                  // desce
        aoMudar = { convidados = it }        // sobe
    )
}
```

É o mesmo par do `TextField`: `value` e `onValueChange`. Os componentes do Compose são todos sem estado, por isso servem em qualquer tela.

## Para saber mais

- [Elevação de estado](https://developer.android.com/develop/ui/compose/state?hl=pt-br#state-hoisting): o par valor e aviso de mudança, na documentação.
- [Onde elevar o estado](https://developer.android.com/develop/ui/compose/state-hoisting?hl=pt-br): como escolher o composable que fica com o estado.
- Codelab oficial: [Introdução ao estado no Compose](https://developer.android.com/codelabs/basic-android-kotlin-compose-using-state?hl=pt-br), na parte sobre elevação de estado.

## O desafio

:::steps
1. Rode o projeto e preencha os dois campos. O total continua 0.
2. Em `Docinhos`, ache o `// TODO` e troque os dois `val` por estado. O segundo campo começa em 4:

   ```kotlin title="MainActivity.kt" hl_lines="1 2"
   var convidados by remember { mutableStateOf("") }
   var porConvidado by remember { mutableStateOf("4") }
   ```

3. Em `CampoNumero`, apague o estado interno, receba os dois parâmetros e use-os no `TextField`:

   ```kotlin title="MainActivity.kt" hl_lines="4 5 9 10"
   @Composable
   fun CampoNumero(
       @StringRes rotulo: Int,
       valor: String,
       aoMudar: (String) -> Unit,
       modifier: Modifier = Modifier
   ) {
       TextField(
           value = valor,
           onValueChange = aoMudar,
           label = { Text(text = stringResource(rotulo)) },
           singleLine = true,
           modifier = modifier.fillMaxWidth()
       )
   }
   ```

4. Em `Docinhos`, passe os valores nas duas chamadas:

   ```kotlin title="MainActivity.kt" hl_lines="3 4 9 10"
   CampoNumero(
       rotulo = R.string.convidados,
       valor = convidados,
       aoMudar = { convidados = it }
   )
   Spacer(modifier = Modifier.height(16.dp))
   CampoNumero(
       rotulo = R.string.por_convidado,
       valor = porConvidado,
       aoMudar = { porConvidado = it }
   )
   ```

5. Rode. Digite 20 convidados e mude os docinhos por convidado para 5.
:::

:::note @StringRes
A anotação avisa que aquele `Int` é o identificador de um texto, como `R.string.convidados`. Se alguém passar outro número, o Android Studio reclama.
:::

## Resultado

- [ ] Com 20 convidados e 4 por convidado aparecem 80 brigadeiros.
- [ ] Mudando para 5 por convidado, o total vai para 100.

## Missão extra

:::exercise Botão Limpar
Acrescente no fim da tela um botão **Limpar**, que esvazia o campo **Convidados** e devolve o outro campo para 4. Por que isso só é possível depois de elevar o estado?
:::

:::solution
```kotlin title="MainActivity.kt"
Spacer(modifier = Modifier.height(16.dp))
Button(
    onClick = {
        convidados = ""
        porConvidado = "4"
    }
) {
    Text(text = "Limpar")
}
```

O botão está em `Docinhos`, e é lá que os dois estados moram agora. Enquanto o texto ficava dentro de cada `CampoNumero`, nada de fora conseguia lê-lo nem mudá-lo.
:::

## Teste o que aprendeu

:::quiz Depois da elevação de estado, quem guarda o texto do campo **Convidados**?
- [ ] O `TextField`
- [ ] O `CampoNumero`
- [x] `Docinhos`, a tela que usa o campo
- [ ] O arquivo `strings.xml`

`CampoNumero` e `TextField` só recebem o texto em `valor` e avisam por `aoMudar`. O `remember { mutableStateOf("") }` está em `Docinhos`.
:::
