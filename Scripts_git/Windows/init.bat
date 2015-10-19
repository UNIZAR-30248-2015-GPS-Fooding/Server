@echo off

rem git directory
set /P dir="Enter git directory: "
cd /d "%dir%"

rem github repo url
set /P remote="Enter url of github repository: "

rem add github repo as remote
git remote add github %remote%

pause