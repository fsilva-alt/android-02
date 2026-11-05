@echo off
rem Traz de volta a configuracao do Android Studio que o studio-padrao guardou.
rem De dois cliques neste arquivo, com o Android Studio fechado.
rem
rem Este arquivo so chama o studio-restaurar.ps1, lendo-o como UTF-8 para os acentos
rem sairem certos. Mantenha-o em ASCII: o cmd.exe nao le UTF-8.
setlocal
set "ANDROID02_SCRIPT=%~dp0studio-restaurar.ps1"
powershell -NoProfile -ExecutionPolicy Bypass -Command "Get-Content -Raw -Encoding UTF8 -LiteralPath $env:ANDROID02_SCRIPT | Invoke-Expression"
echo.
pause
endlocal
