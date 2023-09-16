$result = ""
$loop = $true
while ($loop) {
  $result = Read-Host "Delete all DB data. Are you sure?`nYes(y) No(n)"
  switch ($result) {
    ({ $_ -eq "Yes" -or $_ -eq "yes" -or $_ -eq "y" }) {
      $loop = $false
      $userName = $env:UserName
      $targetFolder = "\\wsl$\Ubuntu-22.04\home\${userName}\develop\spring-app\docker"
      if (-not (Test-Path $targetFolder)) {
        echo "The target folder does not exist."
        break
      }
      cd $targetFolder
      wsl.exe -d Ubuntu-22.04 docker compose down
      cd ..
      wsl.exe -d Ubuntu-22.04 sudo rm -r docker
      echo "All DB data has been deleted."
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
