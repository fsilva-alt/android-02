# 02 · O botão Sortear

App: **Prato do Dia** · Conceito: `Button`, `onClick` e estado (`remember`, `mutableStateOf`) · ⏱ 14 min

Abra esta pasta no Android Studio (**File › Open**). A explicação está no módulo 2 do curso.

## Como começa

A tela mostra sempre o brigadeiro. Não há como trocar de prato.

## Tarefa

1. **TODO 1.** Crie um `Button` com o texto `R.string.sortear`. No `onClick`, sorteie um número de 1 a 6 e guarde em `prato`.
2. Rode e toque em **Sortear**: o sorteio acontece, mas a tela continua no brigadeiro.
3. **TODO 2.** Transforme `prato` em estado: `var prato by remember { mutableStateOf(1) }`.
4. Importe o que ficar vermelho (<kbd>Alt</kbd>+<kbd>Enter</kbd>).

## Resultado

Toque em **Sortear** algumas vezes: o prato e o nome mudam.

Solução: [solucoes/02-o-botao-sortear](../../solucoes/02-o-botao-sortear)
