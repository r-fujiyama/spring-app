$userName = $env:UserName
$targetFolder = "\\wsl$\Ubuntu-22.04\home\${userName}\develop\spring-app\docker"
Copy-Item ".\" -Recurse $targetFolder -Exclude *.ps1  -Force
cd $targetFolder
wsl.exe -d Ubuntu-22.04 docker compose down
wsl.exe -d Ubuntu-22.04 docker compose build
wsl.exe -d Ubuntu-22.04 docker compose up
pause
