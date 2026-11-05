# 05 · Quantos convidados?

App: **Docinhos** · Conceito: `TextField`, `toIntOrNull()` e `?:` · ⏱ 14 min

Abra esta pasta no Android Studio (**File › Open**). A explicação está no módulo 5 do curso.

## Como começa

O campo aparece, o teclado abre, mas nada do que você digita fica no campo. A conta mostra sempre 0 brigadeiros.

## Tarefa

1. **TODO 1.** Crie o estado `convidados`, um texto que começa vazio.
2. **TODO 2.** Ligue o campo ao estado: `value` mostra o texto e `onValueChange` guarda o texto novo.
3. Rode e digite 20: o texto aparece no campo, mas o total continua 0.
4. **TODO 3.** Converta `convidados` para número com `toIntOrNull()`; se não for um número, use 0. Multiplique por 4 e guarde em `brigadeiros`.

## Resultado

Digite 20: aparecem 80 brigadeiros e 2,7 latas. Apague o campo: volta para 0.

Solução: [solucoes/05-quantos-convidados](../../solucoes/05-quantos-convidados)
