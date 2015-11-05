@echo off

rem Directorio del proyecto de git (donde esta la carpeta .git)
set /P dir="Enter git directory: "
cd /d "%dir%"

rem Branch to commit
set /P branch="Branch's name: "

rem Commit message
set /P msg="Enter commit message: "

rem Push to openshift
git add .
git commit -m "%msg%"
git push origin %branch%

rem Push to Github
git push github %branch%

rem Get code coverage
set date=%DATE%%TIME%
"%SYSTEMDRIVE%\Program Files\Git\bin\bash.exe" --login -i -c "scp 5624a20f0c1e669609000099@fooding-gpsfooding.rhcloud.com:./app-root/repo/target/site/jacoco/index.html ./Reports/date.html"
pause