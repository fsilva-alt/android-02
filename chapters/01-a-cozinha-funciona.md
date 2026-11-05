---
duration: 15
---

# A cozinha funciona?

![Diagrama com o caminho do curso: Prato do Dia, Brigadeiro, Docinhos e Livro de Receitas, ligados por setas](assets/roadmap.svg "Quatro apps de receitas, um conceito por módulo")

{.lead}
Na Parte 1 você montou telas. Aqui elas passam a **responder**: botões, campos de texto, avisos, e um app com mais de uma tela. Este primeiro módulo prepara a cozinha: baixar os projetos, abrir um deles e rodar.

## Como o curso funciona

::::columns
:::card Módulos curtos
São 12 módulos, 4 deles opcionais. Cada um explica **uma** ideia, ou duas que andam juntas, e termina com um desafio de poucas linhas.
:::
:::card Projetos pela metade
Você não cria projetos do zero. Cada desafio parte de um projeto que já tem a tela montada e deixa faltando só o assunto do módulo.
:::
:::card Resultado à vista
Quase todo módulo abre com as telas do app: como ele começa e como deve ficar. Em aula, o instrutor faz as mudanças e a turma acompanha.
:::
::::

## O que você vai construir

::::columns
:::card Prato do Dia
Sorteia o cardápio a cada toque. `Button`, estado, `when` e o depurador. Módulos 2 e 3.
:::
:::card Brigadeiro
A receita em quatro passos, avançando com toques na imagem. `clickable` e dois estados. Módulo 4, opcional.
:::
:::card Docinhos
Calcula os brigadeiros de uma festa. `TextField`, teclado, `Switch` e testes. Módulos 5 a 8.
:::
:::card Livro de Receitas
Seis receitas, uma tela para cada uma e uma favorita. Navegação, `Snackbar`, diálogo e estado que resiste ao giro do aparelho. Módulos 9 a 12.
:::
::::

## Baixe os projetos

:::important Faça antes da primeira aula
Chegue à aula com este módulo pronto: projetos baixados e o app **Cozinha** rodando. Na primeira vez, o Android Studio baixa o Gradle e as bibliotecas: uns cinco minutos em uma boa conexão e perto de 1 GB em disco. Os outros projetos usam as mesmas versões e, depois do primeiro, abrem bem mais rápido, mesmo sem internet. Só dois módulos baixam mais algumas bibliotecas na primeira vez: o 8, de testes, e o 10, de navegação. Se a internet da sala for fraca, abra também o projeto `10-duas-telas` em casa e espere a sincronização: é cerca de um minuto.
:::

O instalador baixa o curso do GitHub e o deixa na sua pasta **Downloads**, em `android-02`. Não precisa de Git.

:::steps
1. Aperte <kbd>Win</kbd>+<kbd>R</kbd>. Abre a janela **Executar**.
2. Cole esta linha e dê <kbd>Enter</kbd>:

   ```bat
   cmd /c "cd /d "%TEMP%" && curl -fsSL https://raw.githubusercontent.com/fsilva-alt/android-02/main/install.cmd -o install.cmd && .\install.cmd & del install.cmd"
   ```

3. Uma janela preta mostra o andamento. No fim, ela diz onde o curso ficou e espera uma tecla para fechar.
:::

Os projetos estão em `Downloads\android-02\exercicios`, um por módulo. O número da pasta é o número do módulo: `01-a-cozinha-funciona`, `02-o-botao-sortear`…

:::details Outras formas de baixar
**No PowerShell**, uma linha faz o mesmo que o instalador acima:

```powershell
irm https://raw.githubusercontent.com/fsilva-alt/android-02/main/install.ps1 | iex
```

**Com Git**, no Prompt de Comando:

```bat
cd /d "%USERPROFILE%\Downloads"
git clone https://github.com/fsilva-alt/android-02
```

**À mão:** abra [github.com/fsilva-alt/android-02](https://github.com/fsilva-alt/android-02), escolha **Code › Download ZIP**, extraia o ZIP na pasta **Downloads** e renomeie a pasta extraída, de `android-02-main` para `android-02`.
:::

:::note Rodou o instalador de novo?
Ele não apaga nada. A pasta `android-02` que já existia é guardada como `android-02-anterior-…`, com tudo o que você fez, e uma cópia nova é instalada.
:::

:::warning Sem acentos no caminho
O Android Studio não compila projetos em pastas com acentos, como `C:\Users\João\Downloads`. Se o seu nome de usuário do Windows tem acento, o instalador avisa e põe o curso em `C:\cursos\android-02`.
:::

## Deixe o Android Studio como o do instrutor

Zoom, barras escondidas e janelas fora do lugar atrapalham na hora de acompanhar a aula. Dois scripts em `Downloads\android-02\ferramentas` cuidam disso. Rode-os com o Android Studio **fechado**, com dois cliques:

| Script | O que faz |
| --- | --- |
| `studio-padrao.cmd` | Guarda a sua configuração e deixa a aparência como a de uma instalação nova: zoom, tamanho das fontes, barras, menus e posição das janelas. |
| `studio-restaurar.cmd` | Traz a sua configuração de volta, como estava antes. |

Tema, cores, atalhos de teclado, plugins e projetos não mudam.

## Para saber mais

- [Baixar o Android Studio](https://developer.android.com/studio?hl=pt-br): o instalador e os requisitos do computador.
- [Criar e gerenciar dispositivos virtuais](https://developer.android.com/studio/run/managing-avds?hl=pt-br): como criar um emulador.
- [Executar apps em um dispositivo de hardware](https://developer.android.com/studio/run/device?hl=pt-br): como ligar a depuração USB do celular.
- [Recursos no Compose](https://developer.android.com/develop/ui/compose/resources?hl=pt-br): `painterResource`, `stringResource` e a classe `R`.

## O desafio

<div class="phones">
<figure class="figure">
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/01-antes.png" alt="Tela do app Cozinha com um brigadeiro e o texto Cozinha de Android"></div></div>
<figcaption>Como começa.</figcaption>
</figure>
<figure class="figure">
<div class="phone"><div class="status"><span>12:00</span><span>▾ ◢ ▮</span></div><div class="screen"><img src="assets/telas/01-depois.png" alt="Tela do app Cozinha com uma coxinha e o texto Cozinha de Ana"></div></div>
<figcaption>Como fica: o seu nome e o prato de que você mais gosta.</figcaption>
</figure>
</div>

:::steps
1. **Abra o projeto.** No Android Studio, **File › Open**, selecione a pasta `Downloads\android-02\exercicios\01-a-cozinha-funciona` e confirme. Abra a pasta do módulo, não a pasta do curso inteiro. Se aparecer **Trust Project**, confie.
2. **Espere a sincronização.** O Gradle baixa o que falta; na primeira vez leva alguns minutos. Termina quando a barra inferior para.
3. **Rode.** Escolha o emulador ou o celular e clique em **Run 'app'** (<kbd>Shift</kbd>+<kbd>F10</kbd>). O app **Cozinha** abre.
4. **Assine a cozinha.** Em `MainActivity.kt`, dentro de `onCreate()`, troque `"Android"` pelo seu nome:

   ```kotlin title="MainActivity.kt" hl_lines="2"
   BoasVindas(
       cozinheiro = "Ana",
       modifier = Modifier.padding(innerPadding)
   )
   ```

5. **Troque o prato.** Em `BoasVindas`, apague `brigadeiro` e digite as primeiras letras de outro prato. O Android Studio sugere os seis desenhos de `res/drawable`:

   ```kotlin title="MainActivity.kt" hl_lines="2"
   Image(
       painter = painterResource(R.drawable.coxinha),
       contentDescription = null,
       modifier = Modifier.size(260.dp)
   )
   ```

6. Rode de novo.
:::

## O que já vem pronto

Todos os projetos do curso têm a mesma estrutura:

| Onde | O quê |
| --- | --- |
| `MainActivity.kt` | A tela. É o único arquivo que você edita na maioria dos desafios. |
| `ui/theme/` | O tema do curso, `ReceitasTheme`: cores de doce de leite e chocolate, nos modos claro e escuro. |
| `res/drawable/` | Os desenhos dos pratos, em vetor. |
| `res/values/strings.xml` | Os textos do app. |

:::tip Procure o TODO
A partir do próximo módulo, o ponto que falta no projeto vem marcado com um comentário `// TODO`. Quando há mais de um, eles vêm numerados, na ordem do desafio. A janela **TODO** (**View › Tool Windows › TODO**) lista todos.
:::

:::note Código em português
Na Parte 1 o código ficou em inglês, como nos modelos do Android Studio. Aqui, os nomes que são do assunto (`prato`, `passo`, `convidados`) estão em português, sem acento. O que vem do Kotlin e do Compose continua em inglês.
:::

## Resultado

- [ ] O projeto abriu e sincronizou sem erros.
- [ ] O app **Cozinha** rodou no emulador ou no celular.
- [ ] A tela mostra o seu nome e o prato que você escolheu.

## O que você precisa

| | |
| --- | --- |
| **Conhecimento** | A Parte 1: composables, `Text`, `Image`, `Column`, `Modifier` e recursos. |
| **Android Studio** | O mesmo da Parte 1, com um emulador criado ou um celular com depuração USB. |
| **Tempo** | Cerca de 2h20. Com os quatro módulos opcionais, 3h35. |

:::note Sobre o curso oficial
Este curso segue a **Unidade 2** do [Android Basics with Compose](https://developer.android.com/courses/android-basics-compose/course?hl=pt-br), trilhas [Add a button to an app](https://developer.android.com/courses/pathways/android-basics-compose-unit-2-pathway-2?hl=pt-br) e [Interacting with UI and state](https://developer.android.com/courses/pathways/android-basics-compose-unit-2-pathway-3?hl=pt-br), com outros apps: sai o dado, entram as receitas. Os três últimos módulos vão além: navegação, avisos e estado salvo, assuntos que o curso oficial deixa para a Unidade 4.
:::

[Começar: o botão Sortear](02-prato-do-dia/01-o-botao-sortear.md){.button}
