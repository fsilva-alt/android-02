# Instala o curso "Introdução ao Desenvolvimento Nativo · Parte 2" em Downloads\android-02.
#
#   irm https://raw.githubusercontent.com/fsilva-alt/android-02/main/install.ps1 | iex
#
# Baixa o ZIP do repositório no GitHub (não precisa de Git), extrai e deixa os projetos em
# Downloads\android-02. Se a pasta já existir, ela é guardada com outro nome: nada é apagado.
# Se houver um Release com a página do curso (index.html), ela é baixada junto.
#
# Variáveis de ambiente opcionais:
#   ANDROID02_DEST   pasta de destino (padrão: Downloads\android-02)
#   ANDROID02_ZIP    endereço do ZIP do curso (padrão: o ramo main no GitHub)
#   ANDROID02_PAGE   endereço da página do curso (padrão: o index.html do último Release)

& {
    $ErrorActionPreference = 'Stop'
    $ProgressPreference = 'SilentlyContinue'   # sem a barra de progresso o download é bem mais rápido
    [Net.ServicePointManager]::SecurityProtocol = [Net.ServicePointManager]::SecurityProtocol -bor [Net.SecurityProtocolType]::Tls12

    $repo = 'fsilva-alt/android-02'
    $zipUrl = if ($env:ANDROID02_ZIP) { $env:ANDROID02_ZIP } else { "https://github.com/$repo/archive/refs/heads/main.zip" }
    $pageUrl = if ($env:ANDROID02_PAGE) { $env:ANDROID02_PAGE } else { "https://github.com/$repo/releases/latest/download/index.html" }

    # Destino: a pasta Downloads do usuário atual. O Windows informa onde ela está, mesmo que
    # tenha sido movida; se não informar, vale %USERPROFILE%\Downloads.
    if ($env:ANDROID02_DEST) {
        $dest = $env:ANDROID02_DEST
    } else {
        $downloads = $null
        try { $downloads = (New-Object -ComObject Shell.Application).NameSpace('shell:Downloads').Self.Path } catch { }
        if (-not $downloads) { $downloads = Join-Path $HOME 'Downloads' }
        $dest = Join-Path $downloads 'android-02'

        # O Android Studio não compila projetos em caminhos com acentos (ex.: C:\Users\João).
        if ($dest -match '[^\x00-\x7F]') {
            $semAcento = Join-Path $env:SystemDrive 'cursos\android-02'
            Write-Host ''
            Write-Host "  A pasta $dest tem acentos, e o Android Studio não compila projetos em caminhos assim." -ForegroundColor Yellow
            Write-Host "  O curso vai para $semAcento." -ForegroundColor Yellow
            $dest = $semAcento
        }
    }

    $pai = Split-Path -Parent $dest
    $tmpZip = Join-Path ([IO.Path]::GetTempPath()) ("android-02-" + [Guid]::NewGuid().ToString('N') + '.zip')
    $tmpDir = Join-Path $pai ('.android-02-' + [Guid]::NewGuid().ToString('N'))   # ao lado do destino: mover é só renomear

    try {
        Write-Host ''
        Write-Host '  Introdução ao Desenvolvimento Nativo · Parte 2' -ForegroundColor Green
        Write-Host ''
        Write-Host "  Baixando o curso de github.com/$repo ..."
        New-Item -ItemType Directory -Force -Path $pai | Out-Null
        Invoke-WebRequest -Uri $zipUrl -OutFile $tmpZip -UseBasicParsing

        Write-Host '  Extraindo ...'
        Expand-Archive -LiteralPath $tmpZip -DestinationPath $tmpDir -Force
        $raiz = Get-ChildItem -LiteralPath $tmpDir -Directory | Select-Object -First 1
        if (-not $raiz -or -not (Test-Path -LiteralPath (Join-Path $raiz.FullName 'exercicios'))) {
            throw 'O arquivo baixado não tem a pasta exercicios.'
        }

        if (Test-Path -LiteralPath $dest) {
            $anterior = "$dest-anterior-" + (Get-Date -Format 'yyyyMMdd-HHmmss')
            try {
                Move-Item -LiteralPath $dest -Destination $anterior
            } catch {
                throw "A pasta $dest está em uso. Feche o Android Studio e rode o instalador de novo."
            }
            Write-Host "  Já existia uma pasta android-02. Ela foi guardada como $(Split-Path -Leaf $anterior)."
        }
        Move-Item -LiteralPath $raiz.FullName -Destination $dest

        # A página do curso é opcional: só existe depois que o instrutor publica um Release.
        $pagina = Join-Path $dest 'index.html'
        try {
            Invoke-WebRequest -Uri $pageUrl -OutFile $pagina -UseBasicParsing
        } catch {
            if (Test-Path -LiteralPath $pagina) { Remove-Item -LiteralPath $pagina -Force }
            $pagina = $null
        }

        $primeiro = Join-Path $dest 'exercicios\01-a-cozinha-funciona'
        Write-Host ''
        Write-Host "  Curso instalado em $dest" -ForegroundColor Green
        Write-Host ''
        Write-Host '  Próximos passos:'
        Write-Host '    1. Abra o Android Studio.'
        Write-Host '    2. File > Open e escolha a pasta do primeiro módulo:'
        Write-Host "       $primeiro"
        Write-Host "    3. Espere o Gradle sincronizar e clique em Run 'app'."
        Write-Host ''
        if ($pagina) {
            Write-Host '  A página do curso, com a explicação de cada módulo, abre no navegador:'
            Write-Host "       $pagina"
        } else {
            Write-Host '  A página do curso (index.html) é distribuída pelo instrutor.'
        }

        $studio = @("$env:ProgramFiles\Android\Android Studio\bin\studio64.exe", "$env:LOCALAPPDATA\Programs\Android Studio\bin\studio64.exe") |
            Where-Object { Test-Path -LiteralPath $_ } | Select-Object -First 1
        if (-not $studio) {
            Write-Host ''
            Write-Host '  Aviso: não achei o Android Studio na pasta padrão. Instale-o antes da aula:' -ForegroundColor Yellow
            Write-Host '       https://developer.android.com/studio' -ForegroundColor Yellow
        }
        Write-Host ''
    } catch {
        Write-Host ''
        Write-Host "  Não deu certo: $($_.Exception.Message)" -ForegroundColor Red
        Write-Host '  Confira a internet e tente de novo. Se continuar, baixe o ZIP em' -ForegroundColor Red
        Write-Host "  https://github.com/$repo (Code > Download ZIP)." -ForegroundColor Red
        Write-Host ''
    } finally {
        if (Test-Path -LiteralPath $tmpZip) { Remove-Item -LiteralPath $tmpZip -Force -ErrorAction SilentlyContinue }
        if (Test-Path -LiteralPath $tmpDir) { Remove-Item -LiteralPath $tmpDir -Recurse -Force -ErrorAction SilentlyContinue }
    }
}
