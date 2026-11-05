---
duration: 12
---

# O prato vazio (opcional)

{.lead}
De vez em quando o sorteio mostra um prato vazio. O código parece certo. Para achar o defeito, pare o app no meio do caminho e olhe o valor das variáveis: é para isso que serve o **depurador**.

:::info Módulo opcional
Nenhum módulo seguinte depende deste. O próximo, [Toque na panela](#toque-na-panela), também é opcional. Com o tempo curto, siga para [Quantos convidados?](#quantos-convidados) e volte aqui depois.
:::

::::columns
:::card Neste módulo
- Ponto de parada: a linha onde o app pausa.
- **Debug**: rodar com o depurador ligado.
- **Variables**: os valores naquele instante.
- **Resume** e **Step Over**.
:::
:::card Projeto
- Abra `exercicios/03-o-prato-vazio`.
- App **Prato do Dia**, completo, com um defeito no sorteio.
:::
::::

<div class="phones">
<figure class="figure">
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/03-antes.png" alt="Tela do Prato do Dia mostrando um prato vazio com garfo e faca"></div></div>
<figcaption>Como começa: às vezes aparece um prato vazio.</figcaption>
</figure>
<figure class="figure">
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/03-depois.png" alt="Tela do Prato do Dia mostrando açaí na tigela"></div></div>
<figcaption>Como fica: só os seis pratos do cardápio.</figcaption>
</figure>
</div>

## O que o depurador faz

Rodando com **Debug**, o app funciona como sempre até chegar a um **ponto de parada**. Ali ele congela, e o Android Studio mostra em que linha está e quanto vale cada variável.

| Ação | Como | Atalho |
| --- | --- | --- |
| Criar ou remover um ponto de parada | Clique na margem, ao lado do número da linha. Aparece um círculo vermelho. | <kbd>Ctrl</kbd>+<kbd>F8</kbd> |
| Rodar com o depurador | Botão **Debug 'app'**, o inseto ao lado do **Run**. | <kbd>Shift</kbd>+<kbd>F9</kbd> |
| Continuar até a próxima parada | **Resume Program** | <kbd>F9</kbd> |
| Executar só a linha atual | **Step Over** | <kbd>F8</kbd> |
| Encerrar | **Stop** | <kbd>Ctrl</kbd>+<kbd>F2</kbd> |

Os atalhos são os do Windows e do Linux. No macOS, use os botões da janela **Debug**.

## Onde está o sorteio

```kotlin title="MainActivity.kt" hl_lines="3 4"
Button(
    onClick = {
        val sorteado = (0..6).random()
        prato = sorteado
    }
) {
    Text(text = stringResource(R.string.sortear))
}
```

Os `when` deste projeto têm uma linha para cada prato, de 1 a 6, e um `else` que mostra o prato vazio. Se o prato vazio aparece, é porque `prato` recebeu um valor fora de 1 a 6. Qual?

## Para saber mais

- [Depurar o app](https://developer.android.com/studio/debug?hl=pt-br): pontos de parada, variáveis e as outras janelas do depurador.
- Codelab oficial: [Usar o depurador no Android Studio](https://developer.android.com/codelabs/basic-android-kotlin-compose-intro-debugger?hl=pt-br).

## O desafio

:::steps
1. Rode o projeto com **Run** e toque em **Sortear** até ver o prato vazio. Pode levar vários toques.
2. Em `MainActivity.kt`, clique na margem da linha `prato = sorteado`. O círculo vermelho marca o ponto de parada.
3. Clique em **Debug 'app'**. O app abre normalmente.
4. Toque em **Sortear**. O app congela, a linha fica destacada e a janela **Debug** abre. Na lista **Variables**, leia o valor de `sorteado`.
5. Clique em **Resume Program** e veja qual prato apareceu. Repita os passos 4 e 5 algumas vezes, comparando o número com o prato.
6. Quando o prato vazio aparecer, qual era o valor de `sorteado`?
7. Corrija o intervalo do sorteio:

   ```kotlin title="MainActivity.kt" hl_lines="1"
   val sorteado = (1..6).random()
   ```

8. Remova o ponto de parada (clique no círculo vermelho), encerre com **Stop** e rode com **Run**.
:::

:::details O que estava errado
`(0..6)` sorteia sete números, de 0 a 6. O 0 não tem linha em nenhum dos `when` e cai no `else`: prato vazio, uma vez a cada sete toques, em média.
:::

:::tip Outra forma de ver um valor
Com o app pausado, pare o mouse sobre uma variável no editor: o valor aparece ali mesmo.
:::

## Resultado

- [ ] Você viu o app pausar na linha marcada.
- [ ] Você leu o valor de `sorteado` na lista **Variables**.
- [ ] Depois da correção, o prato vazio não aparece mais.

## Missão extra

:::exercise Uma linha de cada vez
Ponha o ponto de parada uma linha acima, em `val sorteado = …`, e rode com **Debug**. Quando o app pausar, procure `sorteado` na lista **Variables**. Aperte **Step Over** (<kbd>F8</kbd>) e procure de novo.
:::

:::solution
Na primeira parada, `sorteado` não está na lista: a linha destacada ainda **não foi executada**. Depois do **Step Over**, ela roda, o depurador para na linha seguinte e `sorteado` aparece com o número sorteado.
:::

## Teste o que aprendeu

:::quiz O app está pausado em um ponto de parada. O que **Resume Program** faz?
- [ ] Executa só a linha atual e pausa de novo
- [ ] Reinicia o app
- [x] Deixa o app seguir até o próximo ponto de parada, se houver
- [ ] Remove o ponto de parada

**Resume** solta o app. Ele só volta a pausar se passar por outro ponto de parada, ou pelo mesmo outra vez. Para avançar uma linha por vez, o botão é **Step Over**.
:::
