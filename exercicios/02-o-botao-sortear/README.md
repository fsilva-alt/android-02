# 02 · O botão Sortear

App: **Prato do Dia** · Conceito: `Button`, `onClick`, estado (`remember`, `mutableStateOf`) e `when` · ⏱ 20 min

Abra esta pasta no Android Studio (**File › Open**). A explicação está no módulo 2 do curso.

## Como começa

A tela mostra sempre o brigadeiro. Não há como trocar de prato.

## Tarefa

1. **TODO 1.** Crie um `Button` com o texto `R.string.sortear`. No `onClick`, sorteie um número de 1 a 6 e guarde em `prato`.
2. Rode e toque em **Sortear**: o sorteio acontece, mas a tela continua no brigadeiro.
3. **TODO 2.** Transforme `prato` em estado: `var prato by remember { mutableStateOf(1) }`.
4. Importe o que ficar vermelho (<kbd>Alt</kbd>+<kbd>Enter</kbd>) e rode: o nome muda, a imagem não.
5. **TODO 3.** Escolha a imagem com um `when (prato)`, na mesma ordem do `when` de `nome`.

## Resultado

Toque em **Sortear** algumas vezes: a imagem e o nome mudam juntos e sempre combinam.

Solução: [solucoes/02-o-botao-sortear](../../solucoes/02-o-botao-sortear)
