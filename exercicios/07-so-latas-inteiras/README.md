# 07 · Só latas inteiras

App: **Docinhos** · Conceito: `KeyboardOptions`, `Switch` e `ceil` · ⏱ 16 min

Abra esta pasta no Android Studio (**File › Open**). A explicação está no módulo 7 do curso.

## Como começa

Os dois campos pedem números e abrem o teclado de letras. O interruptor "Só latas inteiras" está na tela, mas não liga.

## Tarefa

1. **TODO 1.** Em `CampoNumero`, peça o teclado numérico: `keyboardType = KeyboardType.Number`.
2. **TODO 2.** Crie o estado `latasInteiras`, que começa em `false`, e ligue o `Switch` a ele.
3. **TODO 3.** Dê a `calcularLatas` um segundo parâmetro, `inteiras: Boolean`. Quando for verdadeiro, arredonde para cima com `ceil`.
4. Passe `latasInteiras` na chamada de `calcularLatas`.

## Resultado

Os campos abrem o teclado numérico. Com 20 convidados: 2,7 latas com o interruptor desligado e 3 com ele ligado.

Solução: [solucoes/07-so-latas-inteiras](../../solucoes/07-so-latas-inteiras)
