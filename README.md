# Introdução ao Desenvolvimento Nativo · Parte 2

Continuação do curso de Android com Kotlin e Jetpack Compose ([Parte 1](https://github.com/fsilva-alt/android-01)), em português (pt-BR). A Parte 1 monta telas; esta as faz responder: botões, estado, campos de texto, mais de uma tela, avisos e estado que resiste ao giro do aparelho.

São 13 módulos, 6 deles opcionais. Cada um explica **um conceito**, ou dois que andam juntos, e termina com um **desafio** de poucas linhas em um projeto do Android Studio que já vem pela metade. O tema dos apps são receitas brasileiras simples, como nos cursos de DevOps.

| # | Módulo | Conceito | App | Tempo |
| --- | --- | --- | --- | --- |
| 1 | A cozinha funciona? | abrir e rodar um projeto pronto | Cozinha | 15 min |
| 2 | O botão Sortear | `Button`, `onClick`, `remember`, `mutableStateOf`, `when` | Prato do Dia | 20 min |
| 3 | O prato vazio *(opcional)* | depurador | Prato do Dia | 12 min |
| 4 | Toque na panela *(opcional)* | `Modifier.clickable`, dois estados | Brigadeiro | 16 min |
| 5 | Quantos convidados? | `TextField`, `toIntOrNull()`, `?:` | Docinhos | 14 min |
| 6 | Um campo, dois usos | elevação de estado | Docinhos | 12 min |
| 7 | Só latas inteiras | `KeyboardOptions`, `Switch`, `ceil` | Docinhos | 16 min |
| 8 | A conta está certa? *(opcional)* | teste local e teste de interface | Docinhos | 20 min |
| 9 | Livro de receitas *(opcional)* | projeto de revisão | Livro de Receitas | 25 min |
| 10 | Duas telas | navegação entre telas (Navigation 3) | Livro de Receitas | 16 min |
| 11 | Qual receita? | dados entre telas, na chave da navegação | Livro de Receitas | 12 min |
| 12 | Favoritei! *(opcional)* | `Snackbar`, `AlertDialog` | Livro de Receitas | 24 min |
| 13 | Girou, sumiu *(opcional)* | `rememberSaveable`, estado salvo | Livro de Receitas | 12 min |

Cerca de 1h50 no caminho principal e 3h40 com os seis módulos opcionais. Nenhum módulo depende de um opcional: cada projeto já começa com o que os anteriores construíram.

Os módulos 1 a 9 seguem a Unidade 2 do curso oficial [Android Basics with Compose](https://developer.android.com/courses/android-basics-compose/course?hl=pt-br), trilhas 2 e 3, com outros apps. Os módulos 10 a 13 tratam de navegação entre telas, passagem de dados, avisos ao usuário e estado salvo, com o que o Android recomenda hoje para apps em Compose: uma Activity só, [Navigation 3](https://developer.android.com/guide/navigation/navigation-3?hl=pt-br), Material 3 e `rememberSaveable`. `Intent` aparece onde continua sendo o caminho, para abrir outro app, como missão extra do módulo 11; Toast e o padrão de uma Activity por tela aparecem em notas.

## Para o aluno

**Antes da primeira aula**, deixe o módulo 1 pronto:

1. Instale o curso. Aperte <kbd>Win</kbd>+<kbd>R</kbd>, cole a linha abaixo e dê <kbd>Enter</kbd>:

   ```bat
   cmd /c "cd /d "%TEMP%" && curl -fsSL https://raw.githubusercontent.com/fsilva-alt/android-02/main/install.cmd -o install.cmd && .\install.cmd & del install.cmd"
   ```

   O instalador baixa o repositório como ZIP (não precisa de Git) e o deixa em `Downloads\android-02`. Se a pasta já existir, ela é guardada como `android-02-anterior-…`: nada é apagado. No PowerShell, a linha equivalente é:

   ```powershell
   irm https://raw.githubusercontent.com/fsilva-alt/android-02/main/install.ps1 | iex
   ```

   Também serve `git clone https://github.com/fsilva-alt/android-02`, ou **Code › Download ZIP**, em uma pasta sem acentos no caminho.

2. Abra o curso (`index.html`) e faça o módulo 1: abrir o primeiro projeto no Android Studio e rodar o app. É nessa hora que o Android Studio baixa o Gradle e as bibliotecas: uns cinco minutos em uma boa conexão e perto de 1 GB em disco. Os outros projetos usam as mesmas versões e abrem sem internet; só os módulos 8 (testes) e 10 (navegação) baixam mais algumas bibliotecas na primeira vez. No 10, é cerca de um minuto e 180 MB; os módulos 11 a 13 aproveitam o que ele baixou. O instalador baixa o `index.html` junto quando existe um Release publicado; sem Release, o instrutor distribui o arquivo.

Na aula, siga os módulos na ordem. Em cada um, abra no Android Studio a pasta de mesmo número em `exercicios/` (**File › Open**): a pasta do módulo, não a do curso inteiro.

Se o seu Android Studio estiver com zoom, barras ou janelas diferentes do padrão, `ferramentas\studio-padrao.cmd` guarda a sua configuração e volta a aparência de fábrica; `ferramentas\studio-restaurar.cmd` devolve a configuração guardada. Os dois rodam com dois cliques, com o Android Studio fechado, e não mexem em tema, atalhos, plugins nem projetos.

Cada pasta de `exercicios/` é um projeto independente, com o seu `README.md`: como o app começa, a tarefa e o resultado esperado. Dá para fazer um módulo sem ter feito o anterior, e os módulos marcados como opcionais podem ser pulados.

Os projetos foram validados no Windows 11 com o Android Studio 2026.2.2, usando o JDK e o SDK que vêm com ele (Gradle 9.6.0, AGP 9.4.1, SDK 37, Navigation 3 1.2.1).

## Estrutura

```
course.yml                 título, cores, idioma e outras configurações
chapters/                  um capítulo por módulo, agrupados por app
exercicios/NN-nome/        projeto do Android Studio de cada módulo, no estado inicial
solucoes/NN-nome/          os arquivos de cada exercício já resolvidos
assets/telas/              capturas das telas dos apps, usadas nos capítulos
assets/pratos/             os desenhos dos pratos (SVG) e a versão para Android (android/*.xml)
assets/roadmap.svg         o diagrama da abertura
styles/, scripts/          identidade visual do curso, a mesma da Parte 1
ferramentas/ilustracoes.py gera os desenhos dos pratos
.github/workflows/         build manual do index.html no GitHub Actions
install.cmd, install.ps1   instalador para os alunos: baixa o ZIP e põe o curso em Downloads\android-02
ferramentas/studio-*       deixam o Android Studio com a aparência padrão e depois restauram a do usuário
.gitattributes             fins de linha: scripts do Windows em CRLF, scripts de shell em LF
```

Para usar uma solução, copie o arquivo de `solucoes/NN-nome/` sobre o de mesmo nome no projeto. Na maioria dos módulos é só o `MainActivity.kt`.

## Gerar o index.html

**Só o `dist/index.html` é distribuído.** Ele é gerado com o [coursekit](https://github.com/fsilva-alt/coursekit) e é autocontido: estilos, scripts e imagens ficam dentro dele.

### No GitHub

**Actions › Build do curso › Run workflow**. Ao terminar, baixe o arquivo em **Summary › Artifacts**. Informe uma tag (ex.: `v1.0`) para publicar também um Release com o `index.html` anexado: é desse Release que o instalador dos alunos baixa a página do curso.

### Localmente

Precisa do coursekit ao lado deste repositório (`../coursekit`) e de Python 3.9+.

```bash
git clone https://github.com/fsilva-alt/coursekit ../coursekit   # uma vez
./build.sh            # gera dist/index.html
./build.sh dev        # pré-visualização em http://127.0.0.1:8000, recarregada ao salvar
./build.sh --strict   # falha em qualquer aviso
```

No Windows, rode os mesmos comandos no **Git Bash**, que vem com o [Git para Windows](https://git-scm.com/download/win). Se o `build.sh` não achar o Python, indique o executável: `PYTHON=python ./build.sh`. Com um Python recém-lançado, para o qual o PyYAML ainda não tem pacote pronto, o `pip` tenta compilá-lo e pede o Visual C++; nesse caso, rode a primeira vez com `PYYAML_FORCE_LIBYAML=0 ./build.sh`.

## Para alterar o curso

- **Um exercício:** edite o projeto em `exercicios/NN-nome/` e, se a solução mudar, o arquivo em `solucoes/NN-nome/`. Os trechos de código do capítulo correspondente precisam acompanhar.
- **Versões do Gradle e das bibliotecas:** são as mesmas nos 13 projetos, em `gradle/libs.versions.toml` e `gradle/wrapper/gradle-wrapper.properties`. Atualize todos juntos. Os projetos 10 a 13 têm a mais a Navigation 3 e o plugin de serialização do Kotlin, e os quatro precisam ficar iguais entre si: é isso que deixa o 11, o 12 e o 13 abrirem sem internet depois do 10.
- **Um módulo opcional:** o título do capítulo termina em "(opcional)" e o capítulo abre com um aviso `:::info Módulo opcional`. O projeto do módulo seguinte precisa começar com o que o opcional teria construído.
- **Um desenho:** altere `ferramentas/ilustracoes.py` e rode `python3 ferramentas/ilustracoes.py --apps`. O script regrava os SVG e atualiza as cópias dentro dos projetos.
- **Uma tela dos capítulos:** as imagens de `assets/telas/` são capturas dos apps. Se a tela de um app mudar, substitua a captura.
