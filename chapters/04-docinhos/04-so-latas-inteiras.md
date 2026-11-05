---
duration: 10
---

# Só latas inteiras

{.lead}
Ninguém compra 2,7 latas de leite condensado. Um interruptor decide se a conta arredonda para cima, e ele funciona como o campo de texto: sem estado, não liga.

::::columns
:::card Neste módulo
- `Switch`: `checked` e `onCheckedChange`.
- Um estado do tipo `Boolean`.
- `ceil`: arredondar para cima.
:::
:::card Projeto
- Abra `exercicios/09-so-latas-inteiras`.
- App **Docinhos**, com o interruptor na tela, ainda sem função.
- Os campos já abrem o teclado numérico, assunto do módulo opcional [O teclado certo](#o-teclado-certo).
:::
::::

<div class="phones">
<figure class="figure">
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/09-antes.png" alt="Tela do Docinhos com 80 brigadeiros, 2,7 latas e o interruptor Só latas inteiras desligado"></div></div>
<figcaption>Como começa: o interruptor não sai do lugar.</figcaption>
</figure>
<figure class="figure">
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/09-depois.png" alt="Tela do Docinhos com 80 brigadeiros, 3 latas e o interruptor Só latas inteiras ligado"></div></div>
<figcaption>Como fica: ligado, 2,7 latas viram 3.</figcaption>
</figure>
</div>

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

- [Switch](https://developer.android.com/develop/ui/compose/components/switch?hl=pt-br) e [Caixa de seleção](https://developer.android.com/develop/ui/compose/components/checkbox?hl=pt-br): os dois componentes de ligar e desligar.
- [`ceil`](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.math/ceil.html) na documentação do Kotlin (em inglês).
- Codelab oficial: [Calcular uma gorjeta personalizada](https://developer.android.com/codelabs/basic-android-kotlin-compose-calculate-tip?hl=pt-br), que arredonda a gorjeta com um interruptor.

## O desafio

:::steps
1. Rode o projeto e toque no interruptor. Ele não liga.
2. Em `Docinhos`, crie o estado abaixo dos outros dois:

   ```kotlin title="MainActivity.kt" hl_lines="3"
   var convidados by remember { mutableStateOf("") }
   var porConvidado by remember { mutableStateOf("4") }
   var latasInteiras by remember { mutableStateOf(false) }
   ```

3. Ligue o `Switch` ao estado. O `// TODO` marca o lugar:

   ```kotlin title="MainActivity.kt" hl_lines="2 3"
   Switch(
       checked = latasInteiras,
       onCheckedChange = { latasInteiras = it }
   )
   ```

4. No fim do arquivo, dê a `calcularLatas` um segundo parâmetro e o arredondamento:

   ```kotlin title="MainActivity.kt" hl_lines="1-6"
   fun calcularLatas(brigadeiros: Int, inteiras: Boolean): Double {
       var latas = brigadeiros / 30.0
       if (inteiras) {
           latas = ceil(latas)
       }
       return latas
   }
   ```

5. A chamada ficou vermelha: falta o segundo argumento. Em `Docinhos`:

   ```kotlin title="MainActivity.kt" hl_lines="1"
   val latas = calcularLatas(brigadeiros, latasInteiras)
   ```

6. Importe `ceil` e rode. Digite 20 convidados e ligue o interruptor.
:::

## Resultado

- [ ] O interruptor liga e desliga.
- [ ] Com 20 convidados: 2,7 latas com ele desligado, 3 com ele ligado.

## Missão extra

:::exercise Caixa de seleção
Troque `Switch` por `Checkbox`. O que precisa mudar além do nome?
:::

:::solution
Nada, só o import. Os dois recebem `checked` e `onCheckedChange` e trabalham com o mesmo estado `Boolean`. A escolha é de aparência: `Switch` para ligar e desligar algo que vale na hora, `Checkbox` para marcar itens de uma lista.
:::

## Teste o que aprendeu

:::quiz Com o interruptor ligado, 31 brigadeiros pedem quantas latas?
- [ ] 1
- [ ] 1,03
- [x] 2
- [ ] 31

31 / 30 dá 1,03. `ceil` leva ao próximo inteiro, 2: uma lata não basta, e não existe um terço de lata à venda.
:::
