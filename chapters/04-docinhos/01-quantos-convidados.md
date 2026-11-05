---
duration: 14
---

# Quantos convidados?

{.lead}
Um campo de texto não guarda o que você digita. Quem guarda é um estado; o campo só mostra o valor que recebe. E o que ele entrega é texto: para fazer conta, o texto precisa virar número.

::::columns
:::card Neste módulo
- `TextField`: `value` e `onValueChange`.
- Um estado do tipo `String`.
- `toIntOrNull()` e `?:`: texto que vira número sem derrubar o app.
:::
:::card Projeto
- Abra `exercicios/06-quantos-convidados`.
- App **Docinhos**, a calculadora de brigadeiros da festa. O campo **Convidados** não guarda o que você digita, e o total está fixo em 0.
:::
::::

<div class="phones">
<figure class="figure">
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/06-antes.png" alt="Tela do Docinhos com o campo Convidados vazio e o total de 0 brigadeiros"></div></div>
<figcaption>Como começa: você digita e o campo continua vazio.</figcaption>
</figure>
<figure class="figure">
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/06-meio.png" alt="Tela do Docinhos com 20 convidados digitados e o total de 0 brigadeiros"></div></div>
<figcaption>No meio do caminho: 20 convidados, 0 brigadeiros.</figcaption>
</figure>
<figure class="figure">
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/06-depois.png" alt="Tela do Docinhos com 20 convidados, 80 brigadeiros e 2,7 latas de leite condensado"></div></div>
<figcaption>Como fica: 4 docinhos por convidado, 30 brigadeiros por lata.</figcaption>
</figure>
</div>

## O TextField não lembra de nada

```kotlin
TextField(
    value = convidados,
    onValueChange = { convidados = it },
    label = { Text(text = stringResource(R.string.convidados)) },
    singleLine = true
)
```

| Parâmetro | Papel |
| --- | --- |
| `value` | O texto que o campo mostra agora. |
| `onValueChange` | Chamado a cada tecla, com o texto novo. Dentro da lambda ele se chama `it`. |
| `label` | O rótulo. Sobe para o canto quando o campo recebe texto ou foco. |
| `singleLine` | Mantém o campo em uma linha só. |

## O caminho de uma tecla

:::steps
1. **Você digita 2.** O campo não muda sozinho: ele chama `onValueChange("2")`.
2. **A lambda guarda.** `convidados = it` escreve `"2"` no estado.
3. **Recomposição.** O estado mudou, a tela é redesenhada.
4. **O campo mostra.** Agora `value` vale `"2"`.
:::

No projeto inicial, `value = ""` é fixo e `onValueChange` está vazio. O passo 2 não acontece, e o campo é sempre redesenhado vazio.

## Texto que talvez seja número

`mutableStateOf("")` cria um estado do tipo `String`, mesmo que o usuário só digite algarismos. Para fazer a conta é preciso converter, e nem todo texto é um número: o campo pode estar vazio ou ter letras.

| Texto digitado | `toIntOrNull()` | `toIntOrNull() ?: 0` |
| --- | --- | --- |
| `"20"` | `20` | `20` |
| `""` | `null` | `0` |
| `"vinte"` | `null` | `0` |

- `toInt()` derruba o app quando o texto não é um número. Um campo vazio já basta.
- `toIntOrNull()` devolve `null` nesses casos, sem derrubar nada.
- `?:` é o operador Elvis: usa o valor da esquerda; se for `null`, usa o da direita.

## O resultado não é estado

```kotlin
var convidados by remember { mutableStateOf("") }      // estado: o que foi digitado

val brigadeiros = (convidados.toIntOrNull() ?: 0) * 4  // calculado a cada recomposição
val latas = calcularLatas(brigadeiros)
```

A cada tecla, `convidados` muda, a tela é recomposta e as duas contas são refeitas. É a mesma regra do módulo 3: estado é só o que não dá para calcular.

O projeto já traz duas funções prontas no fim do arquivo:

| Função | O que faz |
| --- | --- |
| `calcularLatas(brigadeiros)` | Divide por 30, o rendimento de uma lata de leite condensado. |
| `formatar(numero)` | Escreve o número com no máximo uma casa decimal. |

## Para saber mais

- [Configurar campos de texto](https://developer.android.com/develop/ui/compose/text/user-input?hl=pt-br): `TextField`, `OutlinedTextField` e as suas opções.
- [Operador Elvis](https://kotlinlang.org/docs/null-safety.html#elvis-operator) e [`toIntOrNull`](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.text/to-int-or-null.html) na documentação do Kotlin (em inglês).
- Codelab oficial: [Introdução ao estado no Compose](https://developer.android.com/codelabs/basic-android-kotlin-compose-using-state?hl=pt-br), a calculadora de gorjetas que corresponde ao Docinhos.

## O desafio

O projeto tem três `TODO`, numerados.

:::steps
1. Rode o projeto, toque no campo e digite. Nada aparece.
2. **TODO 1.** Em `MainActivity.kt`, crie o estado no lugar do comentário:

   ```kotlin title="MainActivity.kt" hl_lines="1"
   var convidados by remember { mutableStateOf("") }
   ```

3. **TODO 2.** Ligue o campo ao estado:

   ```kotlin title="MainActivity.kt" hl_lines="2 3"
   TextField(
       value = convidados,
       onValueChange = { convidados = it },
       label = { Text(text = stringResource(R.string.convidados)) },
       singleLine = true,
       modifier = Modifier.fillMaxWidth()
   )
   ```

4. Importe o que faltar (`remember`, `mutableStateOf`, `getValue`, `setValue`) e rode. Digite 20: o texto aparece no campo, mas o total continua 0.
5. **TODO 3.** Troque `val brigadeiros = 0` pela conta:

   ```kotlin title="MainActivity.kt" hl_lines="1"
   val brigadeiros = (convidados.toIntOrNull() ?: 0) * 4
   val latas = calcularLatas(brigadeiros)
   ```

6. Rode. Digite 20, depois apague tudo, depois digite letras.
:::

:::note 2,7 ou 2.7?
`formatar` segue o idioma do aparelho. Em português aparece `2,7`; em um emulador em inglês, `2.7`.
:::

## Resultado

- [ ] O que você digita aparece no campo.
- [ ] Com 20 convidados aparecem 80 brigadeiros e 2,7 latas.
- [ ] Com o campo vazio ou com letras, o total é 0 e o app continua aberto.

## Missão extra

:::exercise No máximo três algarismos
Faça o campo recusar textos com mais de três caracteres. Dica: quem decide o que entra no estado é a lambda de `onValueChange`.
:::

:::solution
```kotlin title="MainActivity.kt"
onValueChange = { if (it.length <= 3) convidados = it },
```

Se o texto novo for longo demais, o estado não muda, e o campo continua mostrando o texto anterior. O campo só exibe o que o estado guarda.
:::

:::exercise Veja o app cair
Troque a conta por `convidados.toInt() * 4` e rode. O que acontece? Abra a janela **Logcat** e procure a linha em vermelho que começa com `java.lang.NumberFormatException`. Depois, desfaça a mudança.
:::

:::solution
O app fecha logo ao abrir: o campo começa vazio, e `"".toInt()` lança `NumberFormatException`. O Logcat mostra o erro e, mais abaixo, o arquivo e a linha onde ele aconteceu. Com `toIntOrNull() ?: 0`, o texto vazio vira 0.
:::

## Teste o que aprendeu

:::quiz Um `TextField` tem `value = convidados` e `onValueChange = { }`. O que o usuário vê ao digitar?
- [ ] O texto aparece normalmente
- [x] Nada: o estado não muda, e o campo continua mostrando o valor antigo
- [ ] O app fecha
- [ ] O texto aparece e some em seguida

Sem guardar o texto novo no estado, não há recomposição com outro `value`. O campo mostra sempre o mesmo conteúdo.
:::

:::quiz Quanto vale `"12a".toIntOrNull() ?: 0`?
- [ ] 12
- [x] 0
- [ ] `null`
- [ ] O app fecha

`"12a"` não é um número inteiro, então `toIntOrNull()` devolve `null`, e o operador `?:` entrega o valor da direita: 0.
:::
