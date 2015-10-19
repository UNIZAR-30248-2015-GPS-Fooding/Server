#!/bin/bash

echo "Enter git directory: "
read dir

cd "$dir"

echo "Branch to start: "
read obranch

echo "New branch's name: "
read nbranch%

rem Push to Openshift
rem ==========================================
rem Checkout a obranch
git checkout $obranch
git pull

rem Create the new branch
git checkout -b $nbranch
git push origin $nbranch
rem ==========================================

rem Push to Github
rem ==========================================
git push github $nbranch