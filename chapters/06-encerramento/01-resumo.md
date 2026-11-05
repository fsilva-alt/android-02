---
duration: 5
---

# Resumo e próximos passos

:::success Parabéns!
Você concluiu a **Parte 2**: apps que respondem ao toque, com estado, campos de texto, mais de uma tela, avisos e memória.
:::

## O que você aprendeu

| App | Módulos | Ideias principais |
| --- | --- | --- |
| Prato do Dia | [O botão Sortear](#o-botao-sortear), [O prato vazio](#o-prato-vazio) (opcional) | `Button`, `onClick`, `random()`, recomposição, `remember`, `mutableStateOf`, `when`, estado mínimo, depurador |
| Brigadeiro | [Toque na panela](#toque-na-panela) (opcional) | `Modifier.clickable`, ordem dos modificadores, `if` como expressão, dois estados |
| Docinhos | [Quantos convidados?](#quantos-convidados), [Um campo, dois usos](#um-campo-dois-usos), [Só latas inteiras](#so-latas-inteiras), [A conta está certa?](#a-conta-esta-certa) (opcional) | `TextField`, `toIntOrNull()`, `?:`, elevação de estado, `KeyboardOptions`, `Switch`, `ceil`, teste local, teste de interface |
| Livro de Receitas | [Livro de receitas](#livro-de-receitas) (opcional), [Duas telas](#duas-telas), [Favoritei!](#favoritei), [Girou, sumiu](#girou-sumiu) | Navigation 3, chaves, pilha de telas, dados na chave, `Intent` de compartilhamento, `Snackbar`, corrotina, `AlertDialog`, mudança de configuração, `rememberSaveable` |

## Glossário

Estado
: Um valor que muda com o tempo e que a tela precisa acompanhar. Criado com `mutableStateOf`.

Recomposição
: O Compose chamando de novo os composables que leem um estado que mudou.

`remember`
: Guarda um valor entre uma recomposição e outra.

Estado mínimo
: Guardar como estado só o que não pode ser calculado a partir de outro estado.

Elevação de estado
: Tirar o estado de dentro de um composable e recebê-lo por parâmetros: o valor desce, o aviso de mudança sobe.

Lambda
: Um bloco de código entre chaves, guardado para rodar depois. É o que `onClick` e `onValueChange` recebem.

Chave
: O nome de uma tela na navegação. Pode levar dados, como `Receita(4)`.

Pilha de telas
: A lista das telas abertas, em ordem. A última é a que aparece; voltar é tirar a última.

`Intent`
: Um pedido ao Android para abrir outra parte de um app ou outro app, como a lista de compartilhamento.

`Snackbar`
: Um aviso curto na base da tela, que some sozinho e pode ter uma ação.

Corrotina
: Um bloco de código que pode esperar sem travar a tela. `scope.launch { … }` cria uma.

Mudança de configuração
: Uma mudança no aparelho, como girar a tela, que faz o Android recriar a Activity.

`rememberSaveable`
: Um `remember` que também resiste à recriação da Activity, guardando o valor no estado salvo da instância.

Ponto de parada
: A linha em que o depurador pausa o app.

Teste local
: Teste que roda no computador. Fica em `app/src/test/`.

Teste de interface
: Teste que roda em um aparelho e interage com a tela. Fica em `app/src/androidTest/`.

## Documentação

- [Estado e Jetpack Compose](https://developer.android.com/develop/ui/compose/state?hl=pt-br)
- [Botões no Compose](https://developer.android.com/develop/ui/compose/components/button?hl=pt-br)
- [Campos de texto](https://developer.android.com/develop/ui/compose/text/user-input?hl=pt-br)
- [Switch](https://developer.android.com/develop/ui/compose/components/switch?hl=pt-br)
- [Navigation 3](https://developer.android.com/guide/navigation/navigation-3?hl=pt-br)
- [Snackbar](https://developer.android.com/develop/ui/compose/components/snackbar?hl=pt-br) e [caixas de diálogo](https://developer.android.com/develop/ui/compose/components/dialog?hl=pt-br)
- [Salvar estados da interface](https://developer.android.com/topic/libraries/architecture/saving-states?hl=pt-br)
- [Depurar o app no Android Studio](https://developer.android.com/studio/debug?hl=pt-br)
- [Testar o layout do Compose](https://developer.android.com/develop/ui/compose/testing?hl=pt-br)

## Próximos passos

- Refaça o **Livro de Receitas** com as suas receitas: troque os textos em `strings.xml` e os desenhos em `res/drawable`.
- Dê ao livro uma terceira tela: um botão **Calcular docinhos** que abre a calculadora do Docinhos. É mais uma chave e mais uma entrada.
- Continue pela **Unidade 3** do [Android Basics with Compose](https://developer.android.com/courses/android-basics-compose/course?hl=pt-br): listas, cartões e o tema do Material Design.
- Na **Unidade 4**, o `ViewModel` tira o estado de dentro dos composables. Na **Unidade 6**, o DataStore e o Room guardam dados que resistem ao fechamento do app, como uma favorita de verdade.

## Antes de fechar

- [ ] Os apps que você fez estão instalados no emulador ou no celular.
- [ ] Você sabe explicar por que `var prato = 1` não atualiza a tela.
- [ ] Você sabe dizer onde mora o estado de um `TextField`.
- [ ] Você sabe o que muda na pilha quando uma tela abre e quando o usuário volta.
- [ ] Você sabe quando avisar com um `Snackbar` e quando perguntar com um `AlertDialog`.
- [ ] Você sabe dizer o que se perde ao girar a tela, e como evitar.
