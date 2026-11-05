# 08 · A conta está certa? (opcional)

App: **Docinhos** · Conceito: teste local e teste de interface · ⏱ 20 min

Abra esta pasta no Android Studio (**File › Open**). A explicação está no módulo 8 do curso.

> **Módulo opcional.** Nenhum módulo seguinte depende deste; pule-o se o tempo estiver curto.

Na primeira vez, o Android Studio baixa as bibliotecas de teste.

## Como começa

O teste local que já existe passa, mas a conta tem um erro que ele não pega. O teste de interface abre a tela e ainda não confere nada.

## Tarefa

1. Abra `CalcularLatasTest.kt` (em `app/src/test`) e rode o teste que já existe.
2. **TODO 1.** Escreva um teste para 45 brigadeiros, sem arredondar. A resposta certa é 1,5.
3. Rode: o teste novo falha. Leia a mensagem e corrija `calcularLatas` em `MainActivity.kt`.
4. **TODO 2.** Abra `DocinhosTest.kt` (em `app/src/androidTest`). Ache o campo pelo texto "Convidados", digite 20 com `performTextInput` e confira com `assertExists()` que o total 80 aparece na tela.
5. Rode o teste de interface com o emulador ligado.

## Resultado

Os dois testes locais passam. O teste de interface passa, e você vê o app abrir e o campo ser preenchido sozinho.

Solução: [solucoes/08-a-conta-esta-certa](../../solucoes/08-a-conta-esta-certa)
