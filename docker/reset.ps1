$status = wsl.exe -d Ubuntu-22.04 service docker status
if ($status.Split(' ')[4] -eq 'not') {
  wsl.exe -d Ubuntu-22.04 sudo service docker start
}

$loop = $true
while ($loop) {
  $result = Read-Host "Delete all Docker data. Are you sure?`nYes(y) No(n)"
  switch ($result) {
    ({ $_ -eq "Yes" -or $_ -eq "yes" -or $_ -eq "y" }) {
      $loop = $false
      $targetFolder = "\\wsl$\Ubuntu-22.04\home\$env:UserName\develop\spring-app\docker"
      if (-not (Test-Path $targetFolder)) {
        echo "The target folder does not exist."
        break
      }
      cd $targetFolder
      wsl.exe -d Ubuntu-22.04 docker compose down
      cd ..
      wsl.exe -d Ubuntu-22.04 sudo rm -r docker
      echo "All Docker data has been deleted."
    }
    ({ $_ -eq "No" -or $_ -eq "no" -or $_ -eq "n" }) {
      $loop = $false
      echo "Complete the process."
    }
    default {
      echo "Input value is invalid."
    }
  }
}
pause
