---
duration: 12
---

# Girou, sumiu

{.lead}
Marque uma favorita e gire o aparelho: a estrela some. Abra uma receita e gire: o app volta para o livro. Ao girar, o Android desmonta a tela e monta outra, e o que estava em `remember` não sobrevive a isso.

::::columns
:::card Neste módulo
- Mudança de configuração: girar a tela recria a Activity.
- `rememberSaveable`: o estado que o Android guarda e devolve.
- `rememberNavBackStack`: a pilha de telas, guardada do mesmo jeito.
:::
:::card Projeto
- Abra `exercicios/12-girou-sumiu`.
- App **Livro de Receitas**, completo. Funciona bem, até alguém girar o aparelho.
:::
::::

<div class="phones">
<figure class="figure deitada">
<div class="phone deitado"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/12-antes.png" alt="Livro de Receitas com o aparelho deitado, na receita 4 de 6, sem a marca de favorita"></div></div>
<figcaption>Como começa: o pudim era a favorita. Girou, a estrela sumiu.</figcaption>
</figure>
<figure class="figure deitada">
<div class="phone deitado"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/12-depois.png" alt="Livro de Receitas com o aparelho deitado, na receita 4 de 6, com a marca de favorita"></div></div>
<figcaption>Como fica: depois de girar, a favorita continua marcada.</figcaption>
</figure>
</div>

## Girar é recomeçar

Girar o aparelho é uma **mudança de configuração**, como trocar o idioma ou ligar o tema escuro. Para montar a tela com as medidas novas, o Android destrói a Activity e cria outra:

:::steps
1. **Gira.** O Android encerra a `MainActivity` que estava na tela.
2. **Cria de novo.** Uma `MainActivity` nova roda o `onCreate` e o `setContent`.
3. **Compõe do zero.** `LivroApp()` é chamado pela primeira vez, e cada `remember` cria o seu valor inicial: `favorita` volta a 0, e a pilha volta a ter só o `Livro`.
:::

O `remember` guarda valores entre uma recomposição e outra. Aqui não houve recomposição: a composição inteira foi jogada fora.

## Uma palavra de diferença

```kotlin
var favorita by rememberSaveable { mutableStateOf(0) }
```

`rememberSaveable` faz o que o `remember` faz e, além disso, entrega o valor ao Android antes de a Activity ser destruída, e o pega de volta quando ela é recriada.

| O que acontece | `remember` | `rememberSaveable` |
| --- | --- | --- |
| Recomposição | Mantém | Mantém |
| Girar a tela, trocar o tema ou o idioma | Perde | Mantém |
| O Android encerra o app em segundo plano, por falta de memória | Perde | Mantém |
| A pessoa fecha o app pela lista de recentes | Perde | Perde |

## O pacote do onCreate

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
```

Esse parâmetro esteve em todos os projetos do curso. Antes de destruir uma Activity, o Android pede a ela um pacote com o que precisa ser lembrado: o **estado salvo da instância**. A Activity nova recebe o mesmo pacote no `onCreate`. O `rememberSaveable` põe e tira os valores desse pacote; você não precisa mexer no `Bundle`.

- **Cabe pouco.** Números, textos, `Boolean`: o que identifica o que a tela mostrava, e não os dados dela.
- **Vale para a tela, não para sempre.** Fechou o app, o pacote vai embora.

:::note Já estava em uso
A página do livro nunca se perdeu, nem ao abrir outra tela: desde o módulo 10, `TelaLivro` guarda `receita` com `rememberSaveable`. Quando uma tela sai da frente, a navegação desmonta o composable dela e guarda só o que é salvável.
:::

## A pilha também é estado

A pilha criada com `remember { mutableStateListOf<Any>(Livro) }` se perde no giro como qualquer outro `remember`. `rememberNavBackStack` cria uma pilha que vai para o pacote:

```kotlin
val pilha = rememberNavBackStack(Livro)
```

Para caber no pacote, cada chave precisa poder virar dados e voltar:

```kotlin
@Serializable
data object Livro : NavKey

@Serializable
data class Receita(val numero: Int) : NavKey
```

| Marca | Papel |
| --- | --- |
| `@Serializable` | Manda o Kotlin gerar o código que transforma a chave em dados, e os dados em chave. |
| `: NavKey` | Diz que a classe é uma chave de navegação que pode ser guardada. |

O Gradle do projeto já vem com o plugin de serialização do Kotlin, que é quem atende ao `@Serializable`.

## Onde guardar cada coisa

| O quê | Com | Sobrevive a |
| --- | --- | --- |
| Um detalhe que pode recomeçar | `remember` | Recomposição |
| O estado da tela: página, texto digitado, diálogo aberto, pilha | `rememberSaveable`, `rememberNavBackStack` | Giro da tela e encerramento pelo sistema |
| Os dados que a tela mostra e as regras do app | `ViewModel` | Giro da tela. É o assunto da Unidade 4 do curso oficial. |
| O que não pode se perder: favoritos de verdade, anotações | DataStore ou Room | Fechar o app. É o assunto da Unidade 6. |

Neste app a favorita ainda some quando o app é fechado. Guardá-la de verdade é trabalho para o DataStore.

## Para saber mais

- [Salvar estados da interface](https://developer.android.com/topic/libraries/architecture/saving-states?hl=pt-br): o que cada opção guarda e por quanto tempo.
- [Restaurar o estado da interface no Compose](https://developer.android.com/develop/ui/compose/state?hl=pt-br#restore-ui-state): `rememberSaveable` e os tipos que ele aceita.
- [Processar mudanças de configuração](https://developer.android.com/guide/topics/resources/runtime-changes?hl=pt-br): o que conta como mudança de configuração.
- [Navigation 3: salvar o estado da navegação](https://developer.android.com/guide/navigation/navigation-3/save-state?hl=pt-br): `rememberNavBackStack`, `NavKey` e `@Serializable`.
- [Visão geral do ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel?hl=pt-br): o passo seguinte.
- Codelab oficial: [Fases do ciclo de vida da atividade](https://developer.android.com/codelabs/basic-android-kotlin-compose-activity-lifecycle?hl=pt-br), que mostra a Activity sendo recriada e corrige o app com `rememberSaveable`.

## O desafio

O projeto tem três `TODO`, numerados, todos em `MainActivity.kt`.

:::steps
1. Rode o projeto. No emulador, confira se o giro automático está ligado: deslize do alto da tela para baixo e procure **Auto-rotate**.
2. Abra uma receita, toque em **☆ Favoritar** e volte ao livro. A página mostra "★ favorita". Gire o emulador com os botões de girar da barra dele. A estrela some.
3. Abra uma receita e gire de novo. O app volta para o livro.
4. **TODO 1.** No `LivroApp`, troque o `remember` de `favorita`:

   ```kotlin title="MainActivity.kt" hl_lines="1"
   var favorita by rememberSaveable { mutableStateOf(0) }
   ```

5. Importe `rememberSaveable`, rode e repita o passo 2. A estrela fica.
6. **TODO 2.** Marque as duas chaves, no começo do arquivo:

   ```kotlin title="MainActivity.kt" hl_lines="1 2 4 5"
   @Serializable
   data object Livro : NavKey

   @Serializable
   data class Receita(val numero: Int) : NavKey
   ```

7. **TODO 3.** Troque a lista da pilha:

   ```kotlin title="MainActivity.kt" hl_lines="1"
   val pilha = rememberNavBackStack(Livro)
   ```

8. Importe o que ficou vermelho. Ao final, o arquivo ganha estas três linhas:

   ```kotlin title="MainActivity.kt"
   import androidx.navigation3.runtime.NavKey
   import androidx.navigation3.runtime.rememberNavBackStack
   import kotlinx.serialization.Serializable
   ```

9. Rode, abra uma receita e gire. A receita continua aberta.
:::

:::warning Qual Serializable importar?
O <kbd>Alt</kbd>+<kbd>Enter</kbd> pode oferecer mais de um. O certo é `kotlinx.serialization.Serializable`. Com `java.io.Serializable`, o `@` fica vermelho: aquele não é uma anotação.
:::

:::tip O emulador não gira?
O giro automático precisa estar ligado no aparelho. No celular de verdade, ele fica no mesmo lugar: os atalhos do alto da tela.
:::

## Resultado

- [ ] Depois de girar, a página da favorita continua com "★ favorita".
- [ ] Com uma receita aberta, girar não leva de volta ao livro.
- [ ] **Voltar** continua funcionando depois do giro.

## Missão extra

:::exercise A pergunta também some
Com uma favorita já escolhida, abra outra receita, toque em **☆ Favoritar** e, com a pergunta "Trocar a favorita?" na tela, gire o aparelho. O que acontece? Conserte.
:::

:::solution
A pergunta some: `perguntando` voltou a `false`. Em `TelaReceita.kt`:

```kotlin title="TelaReceita.kt"
var perguntando by rememberSaveable { mutableStateOf(false) }
```

Diálogo aberto também é estado da tela.
:::

:::exercise O que não cabe no pacote
A favorita sobrevive ao giro. Feche o app pela lista de recentes e abra de novo. Ela continua lá? Por quê?
:::

:::solution
Não. O estado salvo da instância existe para o Android devolver a tela como estava depois de uma interrupção que a pessoa não pediu. Quando ela mesma fecha o app, o pacote é descartado. O que precisa durar além disso vai para o armazenamento do aparelho, com DataStore ou Room.
:::

## Teste o que aprendeu

:::quiz Um contador começa em 0 e está em 7 quando o aparelho é girado. Quanto ele mostra depois, com `remember` e com `rememberSaveable`?
- [ ] 7 e 7
- [x] 0 e 7
- [ ] 0 e 0
- [ ] 7 e 0

No giro, a Activity é recriada e a composição começa do zero. O `remember` cria o valor inicial de novo; o `rememberSaveable` recebe de volta o 7 que entregou ao Android.
:::

:::quiz O que **não** deve ir para um `rememberSaveable`?
- [ ] O número da página aberta
- [ ] O texto que a pessoa digitou em um campo
- [x] A lista com todas as receitas e as suas imagens
- [ ] Se um diálogo está aberto

O pacote do estado salvo é pequeno. Guarde o que identifica a tela, como um número ou um texto curto, e busque os dados a partir disso.
:::
