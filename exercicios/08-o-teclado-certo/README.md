# 08 · O teclado certo (opcional)

App: **Docinhos** · Conceito: `KeyboardOptions` · ⏱ 8 min

Abra esta pasta no Android Studio (**File › Open**). A explicação está no módulo 8 do curso.

> **Módulo opcional.** Nenhum módulo seguinte depende deste; pule-o se o tempo estiver curto.

## Como começa

Os campos pedem números, mas abrem o teclado de letras.

## Tarefa

1. No `TextField`, passe `keyboardOptions` com `keyboardType = KeyboardType.Number`.
2. Crie em `CampoNumero` o parâmetro `acao: ImeAction` e use-o em `imeAction`.
3. Passe `ImeAction.Next` no primeiro campo e `ImeAction.Done` no segundo.

## Resultado

O teclado é numérico. No primeiro campo a tecla de ação pula para o segundo; no segundo, fecha o teclado.

Solução: [solucoes/08-o-teclado-certo](../../solucoes/08-o-teclado-certo)
