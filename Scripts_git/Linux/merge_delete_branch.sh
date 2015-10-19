#!/bin/bash

echo "Enter git directory: "
read dir

cd "$dir"

echo "Branch's name: "
read branch

git checkout master
git merge $branch

git pull origin master
git add .
git commit -am "Merge $branch into master"
git push github master

git fetch -p origin
git push origin :$branch
git push origin master

git push github :$branch