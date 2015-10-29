@echo off

rem Directorio del proyecto de git (donde esta la carpeta .git)
set /P dir="Enter git directory: "
cd /d "%dir%"

rem github repo url
set /P remote="Enter url of github repository: "

rem add github repo as remote
git remote add github %remote%

pause