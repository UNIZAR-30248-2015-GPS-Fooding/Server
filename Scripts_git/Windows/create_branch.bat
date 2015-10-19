@echo off

rem Directorio del proyecto de git (donde esta la carpeta .git)
set /P dir="Enter git directory: "
cd /d "%dir%"

rem Branch to fork
set /P obranch="Branch to start: "

rem New branch
set /P nbranch="New branch's name: "

rem Push to Openshift
rem ==========================================
rem Checkout a obranch
git checkout %obranch%
git pull

rem Create the new branch
git checkout -b %nbranch%
git push origin %nbranch%
rem ==========================================

rem Push to Github
rem ==========================================
git push github %nbranch%

pause