# Traz de volta a configuração do Android Studio que o studio-padrao guardou.
#
# No curso baixado: dois cliques em ferramentas\studio-restaurar.cmd. Ou, no PowerShell:
#
#   irm https://raw.githubusercontent.com/fsilva-alt/android-02/main/ferramentas/studio-restaurar.ps1 | iex
#
# Voltam o zoom, as fontes, as barras, os menus, a posição das janelas de ferramentas e a
# disposição dos projetos que você já tinha aberto. A disposição dos projetos abertos depois,
# como os do curso, continua como está. Depois de restaurada, a cópia guardada é apagada.
#
# Variáveis de ambiente opcionais, usadas nos testes:
#   ANDROID02_STUDIO_CONFIG   pasta que contém as pastas AndroidStudio* (padrão: %APPDATA%\Google)
#   ANDROID02_STUDIO_BACKUP   onde a configuração fica guardada (padrão: %LOCALAPPDATA%\android-02\studio)

& {
    $ErrorActionPreference = 'Stop'

    $raiz = if ($env:ANDROID02_STUDIO_CONFIG) { $env:ANDROID02_STUDIO_CONFIG } else { Join-Path $env:APPDATA 'Google' }
    $guarda = if ($env:ANDROID02_STUDIO_BACKUP) { $env:ANDROID02_STUDIO_BACKUP } else { Join-Path $env:LOCALAPPDATA 'android-02\studio' }

    # Os mesmos arquivos e o mesmo componente do studio-padrao.ps1.
    $arquivos = @('ui.lnf.xml', 'window.layouts.xml', 'window.state.xml', 'editor.xml', 'editor-font.xml',
                  'console-font.xml', 'terminal-font.xml', 'customization.xml', 'runner.layout.xml')
    $componente = '(?s)[ \t]*<component name="NotRoamableUiSettings"(?:\s*/>|>.*?</component>)\r?\n?'
    $utf8 = New-Object System.Text.UTF8Encoding($false)

    Write-Host ''
    Write-Host '  Android Studio com a sua configuração de volta' -ForegroundColor Green
    Write-Host ''

    if (Get-Process -Name 'studio64' -ErrorAction SilentlyContinue) {
        Write-Host '  O Android Studio está aberto. Feche-o e rode de novo: ao fechar,' -ForegroundColor Yellow
        Write-Host '  ele grava a configuração e desfaria a mudança.' -ForegroundColor Yellow
        Write-Host ''
        return
    }

    $copias = @()
    if (Test-Path -LiteralPath $guarda) {
        $copias = @(Get-ChildItem -LiteralPath $guarda -Directory | Where-Object { $_.Name -match '^AndroidStudio(Preview)?[0-9.]+$' })
    }
    if ($copias.Count -eq 0) {
        Write-Host '  Não há configuração guardada. Quem guarda é o studio-padrao, e a cópia' -ForegroundColor Yellow
        Write-Host '  é apagada depois de restaurada.' -ForegroundColor Yellow
        Write-Host ''
        return
    }

    try {
        foreach ($copia in $copias) {
            $versao = Join-Path $raiz $copia.Name
            $opcoes = Join-Path $versao 'options'
            if (-not (Test-Path -LiteralPath $opcoes)) {
                Write-Host "  $($copia.Name): esta versão não está mais instalada; a cópia continua guardada." -ForegroundColor Yellow
                continue
            }

            # Cada arquivo volta a ser o que era: o que existia é copiado, o que não existia some.
            foreach ($a in $arquivos) {
                $atual = Join-Path $opcoes $a
                $salvo = Join-Path $copia.FullName "options\$a"
                if (Test-Path -LiteralPath $atual) { Remove-Item -LiteralPath $atual -Force }
                if (Test-Path -LiteralPath $salvo) { Copy-Item -LiteralPath $salvo -Destination $atual }
            }

            # O componente do zoom volta para dentro do other.xml, que pode ter mudado no resto.
            $outros = Join-Path $opcoes 'other.xml'
            $salvo = Join-Path $copia.FullName 'NotRoamableUiSettings.xml'
            $texto = if (Test-Path -LiteralPath $outros) { [IO.File]::ReadAllText($outros, $utf8) } else { "<application>`n</application>`n" }
            $novo = [regex]::Replace($texto, $componente, '')
            if (Test-Path -LiteralPath $salvo) {
                $fim = $novo.LastIndexOf('</application>')
                if ($fim -lt 0) { throw "O arquivo $outros não tem o formato esperado." }
                $novo = $novo.Insert($fim, [IO.File]::ReadAllText($salvo, $utf8))
            }
            if ($novo -ne $texto) { [IO.File]::WriteAllText($outros, $novo, $utf8) }

            # A disposição dos projetos antigos volta; a dos projetos abertos depois é mantida.
            $projetos = Join-Path $copia.FullName 'workspace'
            if (Test-Path -LiteralPath $projetos) {
                $destino = Join-Path $versao 'workspace'
                New-Item -ItemType Directory -Force -Path $destino | Out-Null
                Copy-Item -Path (Join-Path $projetos '*') -Destination $destino -Recurse -Force
            }

            Remove-Item -LiteralPath $copia.FullName -Recurse -Force
            Write-Host "  $($copia.Name): configuração restaurada." -ForegroundColor Green
        }

        if ((Test-Path -LiteralPath $guarda) -and -not (Get-ChildItem -LiteralPath $guarda -Force)) {
            Remove-Item -LiteralPath $guarda -Force
        }
        Write-Host ''
        Write-Host '  Abra o Android Studio: ele está como antes do studio-padrao.'
        Write-Host ''
    } catch {
        Write-Host ''
        Write-Host "  Não deu certo: $($_.Exception.Message)" -ForegroundColor Red
        Write-Host "  A cópia continua guardada em $guarda" -ForegroundColor Red
        Write-Host ''
    }
}
