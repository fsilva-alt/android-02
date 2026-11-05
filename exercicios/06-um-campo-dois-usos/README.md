# 06 · Um campo, dois usos

App: **Docinhos** · Conceito: elevação de estado · ⏱ 12 min

Abra esta pasta no Android Studio (**File › Open**). A explicação está no módulo 6 do curso.

## Como começa

Há dois campos feitos com o mesmo `CampoNumero`. Os dois aceitam texto, mas a conta fica em 0.

## Tarefa

1. Em `Docinhos`, crie os estados `convidados` (vazio) e `porConvidado` (começa em "4").
2. Em `CampoNumero`, apague o estado interno e receba dois parâmetros: `valor: String` e `aoMudar: (String) -> Unit`.
3. Use os parâmetros no `TextField` e passe-os nas duas chamadas de `CampoNumero`.

## Resultado

Com 20 convidados e 4 por convidado aparecem 80 brigadeiros. Mude para 5: 100.

Solução: [solucoes/06-um-campo-dois-usos](../../solucoes/06-um-campo-dois-usos)
