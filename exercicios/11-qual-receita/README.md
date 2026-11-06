# 11 · Qual receita?

App: **Livro de Receitas** · Conceito: dados entre telas, na chave da navegação · ⏱ 12 min

Abra esta pasta no Android Studio (**File › Open**). A explicação está no módulo 11 do curso.

## Como começa

**Ver receita** abre a segunda tela, mas é sempre a do brigadeiro, em qualquer página do livro.

## Tarefa

Tudo em `MainActivity.kt`:

1. **TODO 1.** Faça a chave levar o número da receita: `data class Receita(val numero: Int)`.
2. **TODO 2.** Em **Ver receita**, ponha na chave o número que o livro avisa: `pilha.add(Receita(numero))`.
3. **TODO 3.** Na entrada da tela, receba a chave e passe `chave.numero` para `TelaReceita`.

## Resultado

Em cada página, **Ver receita** abre os ingredientes e o modo de preparo daquela receita.

Solução: [solucoes/11-qual-receita](../../solucoes/11-qual-receita)
