$userName = $env:UserName
Copy-Item ".\" -Recurse "\\wsl$\Ubuntu-22.04\home\${userName}\develop\spring-app\docker" -Exclude *.ps1  -Force
cd "\\wsl$\Ubuntu-22.04\home\${userName}\develop\spring-app\docker"
wsl.exe -d Ubuntu-22.04 docker compose down
wsl.exe -d Ubuntu-22.04 docker compose build
wsl.exe -d Ubuntu-22.04 docker compose up -d
echo "Container startup is complete."
pause
