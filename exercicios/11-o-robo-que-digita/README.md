# 11 · O robô que digita (opcional)

App: **Docinhos** · Conceito: teste de interface · ⏱ 12 min

Abra esta pasta no Android Studio (**File › Open**). A explicação está no módulo 11 do curso.

> **Módulo opcional.** Nenhum módulo seguinte depende deste; pule-o se o tempo estiver curto.

## Como começa

O app está pronto. O teste de interface abre a tela, mas ainda não confere nada.

## Tarefa

1. Abra `DocinhosTest.kt` (em `app/src/androidTest`).
2. Ache o campo pelo texto "Convidados" e digite 20 com `performTextInput`.
3. Confira com `assertExists()` que o total 80 aparece na tela.
4. Rode o teste com o emulador ligado.

## Resultado

O teste passa. Você vê o app abrir e o campo ser preenchido sozinho.

Solução: [solucoes/11-o-robo-que-digita](../../solucoes/11-o-robo-que-digita)
