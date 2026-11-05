# Deixa o Android Studio com a aparência de uma instalação nova e guarda a configuração atual.
# Para trazer a configuração de volta, use o studio-restaurar.
#
# No curso baixado: dois cliques em ferramentas\studio-padrao.cmd. Ou, no PowerShell:
#
#   irm https://raw.githubusercontent.com/fsilva-alt/android-02/main/ferramentas/studio-padrao.ps1 | iex
#
# O que volta ao padrão, em cada versão do Android Studio instalada:
#   - o zoom da IDE e o tamanho das fontes (interface, editor, console e terminal)
#   - as barras e os menus: o que aparece e o que foi personalizado
#   - a posição e o tamanho das janelas de ferramentas (Project, Logcat, Build, Debug…)
#   - a disposição guardada de cada projeto já aberto (abas e janelas)
# O que não muda: tema e cores, atalhos de teclado, plugins, SDK, emuladores e os seus projetos.
#
# Variáveis de ambiente opcionais, usadas nos testes:
#   ANDROID02_STUDIO_CONFIG   pasta que contém as pastas AndroidStudio* (padrão: %APPDATA%\Google)
#   ANDROID02_STUDIO_BACKUP   onde a configuração fica guardada (padrão: %LOCALAPPDATA%\android-02\studio)

& {
    $ErrorActionPreference = 'Stop'

    $raiz = if ($env:ANDROID02_STUDIO_CONFIG) { $env:ANDROID02_STUDIO_CONFIG } else { Join-Path $env:APPDATA 'Google' }
    $guarda = if ($env:ANDROID02_STUDIO_BACKUP) { $env:ANDROID02_STUDIO_BACKUP } else { Join-Path $env:LOCALAPPDATA 'android-02\studio' }

    # Arquivos de options\ que só guardam aparência.
    $arquivos = @('ui.lnf.xml', 'window.layouts.xml', 'window.state.xml', 'editor.xml', 'editor-font.xml',
                  'console-font.xml', 'terminal-font.xml', 'customization.xml', 'runner.layout.xml')
    # O zoom da IDE fica em other.xml, junto de configurações de outro tipo: só este componente sai.
    $componente = '(?s)[ \t]*<component name="NotRoamableUiSettings"(?:\s*/>|>.*?</component>)\r?\n?'
    $utf8 = New-Object System.Text.UTF8Encoding($false)

    Write-Host ''
    Write-Host '  Android Studio com a aparência padrão' -ForegroundColor Green
    Write-Host ''

    if (Get-Process -Name 'studio64' -ErrorAction SilentlyContinue) {
        Write-Host '  O Android Studio está aberto. Feche-o e rode de novo: ao fechar,' -ForegroundColor Yellow
        Write-Host '  ele grava a configuração e desfaria a mudança.' -ForegroundColor Yellow
        Write-Host ''
        return
    }

    $versoes = @()
    if (Test-Path -LiteralPath $raiz) {
        $versoes = @(Get-ChildItem -LiteralPath $raiz -Directory | Where-Object {
            $_.Name -match '^AndroidStudio(Preview)?[0-9.]+$' -and (Test-Path -LiteralPath (Join-Path $_.FullName 'options'))
        })
    }
    if ($versoes.Count -eq 0) {
        Write-Host "  Não achei a configuração do Android Studio em $raiz." -ForegroundColor Yellow
        Write-Host '  Abra o Android Studio uma vez, feche-o e rode de novo.' -ForegroundColor Yellow
        Write-Host ''
        return
    }

    try {
        foreach ($v in $versoes) {
            $opcoes = Join-Path $v.FullName 'options'
            $outros = Join-Path $opcoes 'other.xml'
            $projetos = Join-Path $v.FullName 'workspace'
            $copia = Join-Path $guarda $v.Name

            # 1. Guarda a configuração atual. Só na primeira vez: rodar de novo não troca a cópia
            #    guardada, que é a configuração de antes do curso.
            if (Test-Path -LiteralPath $copia) {
                Write-Host "  $($v.Name): já existe uma configuração guardada, e ela foi mantida."
            } else {
                $parcial = "$copia.parcial"
                if (Test-Path -LiteralPath $parcial) { Remove-Item -LiteralPath $parcial -Recurse -Force }
                New-Item -ItemType Directory -Force -Path (Join-Path $parcial 'options') | Out-Null
                foreach ($a in $arquivos) {
                    $origem = Join-Path $opcoes $a
                    if (Test-Path -LiteralPath $origem) { Copy-Item -LiteralPath $origem -Destination (Join-Path $parcial "options\$a") }
                }
                if (Test-Path -LiteralPath $outros) {
                    $achado = [regex]::Match([IO.File]::ReadAllText($outros, $utf8), $componente)
                    if ($achado.Success) { [IO.File]::WriteAllText((Join-Path $parcial 'NotRoamableUiSettings.xml'), $achado.Value, $utf8) }
                }
                if (Test-Path -LiteralPath $projetos) { Copy-Item -LiteralPath $projetos -Destination (Join-Path $parcial 'workspace') -Recurse }
                [IO.File]::WriteAllText((Join-Path $parcial 'guardado-em.txt'), (Get-Date -Format 'yyyy-MM-dd HH:mm:ss'), $utf8)
                Move-Item -LiteralPath $parcial -Destination $copia     # só vale quando está completa
                Write-Host "  $($v.Name): configuração atual guardada."
            }

            # 2. Volta ao padrão: sem o arquivo, o Android Studio usa os valores de fábrica.
            foreach ($a in $arquivos) {
                $alvo = Join-Path $opcoes $a
                if (Test-Path -LiteralPath $alvo) { Remove-Item -LiteralPath $alvo -Force }
            }
            if (Test-Path -LiteralPath $outros) {
                $texto = [IO.File]::ReadAllText($outros, $utf8)
                $limpo = [regex]::Replace($texto, $componente, '')
                if ($limpo -ne $texto) { [IO.File]::WriteAllText($outros, $limpo, $utf8) }
            }
            if (Test-Path -LiteralPath $projetos) { Remove-Item -LiteralPath $projetos -Recurse -Force }
            Write-Host "  $($v.Name): aparência padrão aplicada." -ForegroundColor Green
        }

        Write-Host ''
        Write-Host '  Abra o Android Studio: zoom, barras e janelas estão como em uma instalação nova.'
        Write-Host '  Para ter a sua configuração de volta, feche o Android Studio e rode o studio-restaurar.'
        Write-Host "  Ela está guardada em $guarda"
        Write-Host ''
    } catch {
        Write-Host ''
        Write-Host "  Não deu certo: $($_.Exception.Message)" -ForegroundColor Red
        Write-Host '  Nada foi apagado sem antes ser guardado. Feche o Android Studio e tente de novo.' -ForegroundColor Red
        Write-Host ''
    }
}
