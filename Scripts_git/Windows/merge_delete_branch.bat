@echo off

rem Directorio del proyecto de git (donde esta la carpeta .git)
set /P dir="Enter git directory: "
cd /d "%dir%"

rem Branch to commit
set /P branch="Branch's name: "

rem Delete branch
git checkout master
git merge %branch%

rem merge on github
git pull origin master
git add .
git commit -am "Merge %branch% into master"
git push github master

rem delete from openshift
git fetch -p origin
git push origin :%branch%
git push origin master

rem delete branch from github
git push github :%branch%

pause