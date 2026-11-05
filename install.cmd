@echo off
rem Instala o curso em Downloads\android-02, sem precisar de Git.
rem
rem Aperte Win+R, cole a linha abaixo e de Enter:
rem
rem   cmd /c "cd /d "%TEMP%" && curl -fsSL https://raw.githubusercontent.com/fsilva-alt/android-02/main/install.cmd -o install.cmd && .\install.cmd & del install.cmd"
rem
rem Este arquivo so chama o install.ps1, que faz o trabalho, e espera uma tecla no fim
rem para a janela nao fechar. Mantenha-o em ASCII: o cmd.exe nao le UTF-8.
setlocal
if not defined ANDROID02_PS1 set "ANDROID02_PS1=https://raw.githubusercontent.com/fsilva-alt/android-02/main/install.ps1"
powershell -NoProfile -ExecutionPolicy Bypass -Command "[Net.ServicePointManager]::SecurityProtocol = [Net.ServicePointManager]::SecurityProtocol -bor [Net.SecurityProtocolType]::Tls12; Invoke-RestMethod $env:ANDROID02_PS1 | Invoke-Expression"
if errorlevel 1 (
  echo.
  echo   Nao foi possivel rodar o instalador. Confira a internet e tente de novo.
)
echo.
pause
endlocal
