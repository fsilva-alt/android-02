# 11 · Favoritei!

App: **Livro de Receitas** · Conceito: `Snackbar` e `AlertDialog` · ⏱ 24 min

Abra esta pasta no Android Studio (**File › Open**). A explicação está no módulo 11 do curso.

## Como começa

A tela da receita ganhou o botão **☆ Favoritar**. Ele funciona: a receita vira a favorita do livro. Mas o app não avisa nada, e favoritar outra receita troca a favorita sem perguntar.

## Tarefa

Em `MainActivity.kt`, o aviso:

1. **TODO 1.** Crie o estado dos avisos, um `SnackbarHostState`, e entregue-o ao `Scaffold` em `snackbarHost`.
2. **TODO 2.** Ao favoritar, mostre o aviso com `showSnackbar`, dentro de `scope.launch`. Dê a ele a ação **Desfazer**, que devolve a favorita anterior.

Em `TelaReceita.kt`, a pergunta:

3. **TODO 3.** Crie o estado `perguntando`, que diz se a pergunta está na tela.
4. **TODO 4.** No botão **☆ Favoritar**, se o livro já tem favorita, abra a pergunta em vez de trocar direto.
5. **TODO 5.** Enquanto `perguntando` for verdadeiro, mostre um `AlertDialog` com os botões **Cancelar** e **Trocar**.

## Resultado

Ao favoritar, aparece na base da tela o aviso "Receita favorita atualizada", com **Desfazer**. Com uma favorita já escolhida, favoritar outra abre a pergunta "Trocar a favorita?": **Trocar** confirma; **Cancelar** e o toque fora da caixa deixam tudo como estava.

Solução: [solucoes/11-favoritei](../../solucoes/11-favoritei)
