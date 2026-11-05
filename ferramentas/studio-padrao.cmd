@echo off
rem Deixa o Android Studio com a aparencia padrao e guarda a configuracao atual.
rem De dois cliques neste arquivo, com o Android Studio fechado.
rem
rem Este arquivo so chama o studio-padrao.ps1, lendo-o como UTF-8 para os acentos
rem sairem certos. Mantenha-o em ASCII: o cmd.exe nao le UTF-8.
setlocal
set "ANDROID02_SCRIPT=%~dp0studio-padrao.ps1"
powershell -NoProfile -ExecutionPolicy Bypass -Command "Get-Content -Raw -Encoding UTF8 -LiteralPath $env:ANDROID02_SCRIPT | Invoke-Expression"
echo.
pause
endlocal
