# 12 · Girou, sumiu

App: **Livro de Receitas** · Conceito: `rememberSaveable` e estado salvo · ⏱ 12 min

Abra esta pasta no Android Studio (**File › Open**). A explicação está no módulo 12 do curso.

## Como começa

Girar o aparelho faz o app esquecer: a favorita some e, se a tela da receita estava aberta, o app volta para o livro.

## Tarefa

Tudo em `MainActivity.kt`:

1. **TODO 1.** Troque o `remember` de `favorita` por `rememberSaveable`.
2. **TODO 2.** Marque as duas chaves com `@Serializable` e `: NavKey`.
3. **TODO 3.** Troque a lista da pilha por `rememberNavBackStack(Livro)`.

## Resultado

Depois de girar o aparelho, a favorita continua marcada e a tela que estava aberta continua aberta.

Solução: [solucoes/12-girou-sumiu](../../solucoes/12-girou-sumiu)
