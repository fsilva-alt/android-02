# 10 · Duas telas

App: **Livro de Receitas** · Conceito: navegação entre telas (Navigation 3) · ⏱ 16 min

Abra esta pasta no Android Studio (**File › Open**). A explicação está no módulo 10 do curso.

Na primeira vez, o Android Studio baixa a biblioteca de navegação: cerca de um minuto, com internet.

## Como começa

O livro ganhou o botão **Ver receita**, mas ele não faz nada. A tela da receita já existe, em `TelaReceita.kt`; falta chegar até ela.

## Tarefa

Tudo em `MainActivity.kt`:

1. **TODO 1.** Crie a chave da tela nova: `data object Receita`.
2. **TODO 2.** Crie a entrada da tela: `entry<Receita> { … }`, chamando `TelaReceita` com a receita 1.
3. **TODO 3.** Em **Ver receita**, ponha a chave na pilha: `pilha.add(Receita)`.

## Resultado

**Ver receita** abre a segunda tela, com ingredientes e modo de preparo. O botão **Voltar** e o gesto de voltar do Android trazem o livro de volta, na mesma página.

Por enquanto, a tela aberta é sempre a do brigadeiro. O módulo 11 resolve.

Solução: [solucoes/10-duas-telas](../../solucoes/10-duas-telas)
