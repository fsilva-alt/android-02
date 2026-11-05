---
duration: 5
---

# Resumo e próximos passos

:::success Parabéns!
Você concluiu a **Parte 2**: quatro apps que respondem ao toque, com estado, campos de texto e testes.
:::

## O que você aprendeu

| App | Módulos | Ideias principais |
| --- | --- | --- |
| Prato do Dia | [O botão Sortear](#o-botao-sortear), [Um prato para cada número](#um-prato-para-cada-numero), [O prato vazio](#o-prato-vazio) (opcional) | `Button`, `onClick`, `random()`, recomposição, `remember`, `mutableStateOf`, `when`, estado mínimo, depurador |
| Brigadeiro | [Toque na panela](#toque-na-panela) | `Modifier.clickable`, ordem dos modificadores, `if` como expressão, dois estados |
| Docinhos | [Quantos convidados?](#quantos-convidados), [Um campo, dois usos](#um-campo-dois-usos), [O teclado certo](#o-teclado-certo) (opcional), [Só latas inteiras](#so-latas-inteiras) | `TextField`, `toIntOrNull()`, `?:`, elevação de estado, `KeyboardOptions`, `Switch`, `ceil` |
| Testes | [A conta está certa?](#a-conta-esta-certa), [O robô que digita](#o-robo-que-digita) (opcional) | `@Test`, `assertEquals`, teste local, teste de interface, `createComposeRule` |
| Livro de Receitas | [Livro de receitas](#livro-de-receitas) | Tudo junto em um app novo |

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
- [Depurar o app no Android Studio](https://developer.android.com/studio/debug?hl=pt-br)
- [Testar o layout do Compose](https://developer.android.com/develop/ui/compose/testing?hl=pt-br)

## Próximos passos

- Refaça o **Livro de Receitas** com as suas receitas: troque os textos em `strings.xml` e os desenhos em `res/drawable`.
- Junte dois apps: um botão **Calcular docinhos** no Livro de Receitas pede mais de uma tela, o assunto da Unidade 4 do curso oficial.
- Continue pela **Unidade 3** do [Android Basics with Compose](https://developer.android.com/courses/android-basics-compose/course?hl=pt-br): listas, cartões e o tema do Material Design.

## Antes de fechar

- [ ] Os quatro apps estão instalados no emulador ou no celular.
- [ ] Você sabe explicar por que `var prato = 1` não atualiza a tela.
- [ ] Você sabe dizer onde mora o estado de um `TextField`.
- [ ] Você rodou um teste local e viu um teste falhar antes de passar.
