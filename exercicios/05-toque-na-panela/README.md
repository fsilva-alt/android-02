# 05 · Toque na panela

App: **Brigadeiro** · Conceito: `Modifier.clickable` e dois estados · ⏱ 16 min

Abra esta pasta no Android Studio (**File › Open**). A explicação está no módulo 5 do curso.

## Como começa

A receita tem quatro passos, mas a tela está parada no primeiro.

## Tarefa

1. **TODO 1.** Acrescente `.clickable { }` ao `Modifier` da imagem, depois de `.clip(CircleShape)`. Dentro dele, avance `passo`; depois do passo 4, volte para o 1.
2. Rode: a receita anda, mas um toque já basta para sair do passo 2. Brigadeiro de verdade pede várias mexidas.
3. **TODO 2.** Crie o estado `mexidas`, começando em 0.
4. No `clickable`, troque a conta por um `when (passo)`: ao sair do passo 1, sorteie de 3 a 6 mexidas; no passo 2, cada toque desconta uma, e a receita só avança no zero.
5. **TODO 3.** No texto de baixo, mostre `R.string.faltam` com o valor de `mexidas` enquanto o passo for o 2.

## Resultado

Cada toque na imagem faz a receita andar. No passo 2 a contagem diminui a cada toque, e a receita só avança no zero.

Solução: [solucoes/05-toque-na-panela](../../solucoes/05-toque-na-panela)
