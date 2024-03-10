$status = wsl.exe -d Ubuntu-22.04 service docker status
if ($status.Split(' ')[4] -eq 'not') {
  wsl.exe -d Ubuntu-22.04 sudo service docker start
}

$targetFolder = "\\wsl$\Ubuntu-22.04\home\$env:UserName\develop\spring-app\docker"
Copy-Item ".\" -Recurse $targetFolder -Exclude *.ps1 -Force
cd $targetFolder
wsl.exe -d Ubuntu-22.04 docker compose down
wsl.exe -d Ubuntu-22.04 docker compose build
wsl.exe -d Ubuntu-22.04 docker compose up -d
pause
